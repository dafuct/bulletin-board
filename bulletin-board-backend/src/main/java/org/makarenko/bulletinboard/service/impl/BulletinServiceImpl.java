package org.makarenko.bulletinboard.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.makarenko.bulletinboard.dto.BulletinDto;
import org.makarenko.bulletinboard.dto.response.BulletinResponse;
import org.makarenko.bulletinboard.entity.Bulletin;
import org.makarenko.bulletinboard.entity.User;
import org.makarenko.bulletinboard.exception.UserNotFoundException;
import org.makarenko.bulletinboard.repository.BulletinRepository;
import org.makarenko.bulletinboard.service.BulletinService;
import org.makarenko.bulletinboard.service.ImageService;
import org.makarenko.bulletinboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BulletinServiceImpl implements BulletinService {

  private final BulletinRepository bulletinRepository;
  private final UserService userService;
  private final ImageService imageService;

  @Autowired
  public BulletinServiceImpl(BulletinRepository bulletinRepository, UserService userService,
      ImageService imageService) {
    this.bulletinRepository = bulletinRepository;
    this.userService = userService;
    this.imageService = imageService;
  }

  @Override
  public Bulletin createBulletin(BulletinDto bulletinDto, String imagePath, String email) {
    Objects.requireNonNull(bulletinDto);
    Objects.requireNonNull(imagePath);
    Objects.requireNonNull(email);
    User user = userService.findUserByEmail(email);
    if (user == null) {
      throw new UserNotFoundException("User does not exist");
    }
    String savedImagePath = null;
    if (bulletinDto.getImageBase64() != null) {
      savedImagePath = imageService.saveImage(bulletinDto.getImageBase64(),
          bulletinDto.getImageExtension(), imagePath);
    }
    Bulletin bulletin = Bulletin.builder()
        .imagePath(savedImagePath)
        .title(bulletinDto.getTitle())
        .description(bulletinDto.getDescription())
        .publishDate(LocalDateTime.now())
        .user(user)
        .build();
    return bulletinRepository.save(bulletin);
  }

  @Override
  public Page<BulletinResponse> getBulletins(PageRequest pageRequest) {
    Objects.requireNonNull(pageRequest);
    Page<Bulletin> bulletins = bulletinRepository.findAllByOrderByPublishDateDesc(pageRequest);
    return bulletins.map(bulletin -> {
      BulletinResponse bulletinResponse = new BulletinResponse(bulletin);
      if (bulletin.getImagePath() != null) {
        bulletinResponse.setImage(imageService.getImage(bulletin.getImagePath()));
      }
      return bulletinResponse;
    });
  }

  @Override
  public Integer countByUserEmail(String email) {
    Objects.requireNonNull(email);
    return bulletinRepository.countByUserEmail(email);
  }

  @Override
  public boolean checkIfUserIsBulletinAuthor(Long id, String email) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(email);
    Optional<Bulletin> bulletinOptional = bulletinRepository.findById(id);
    if (bulletinOptional.isPresent()) {
      Bulletin bulletin = bulletinOptional.get();
      return bulletin.getUser().getEmail().equals(email);
    }
    return false;
  }

  @Override
  public void removeBulletin(Long id) {
    Objects.requireNonNull(id);
    bulletinRepository.deleteById(id);
  }
}
