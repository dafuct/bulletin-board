package org.makarenko.bulletinboard.controller;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import org.makarenko.bulletinboard.dto.BulletinDto;
import org.makarenko.bulletinboard.dto.response.BulletinResponse;
import org.makarenko.bulletinboard.dto.response.ErrorResponse;
import org.makarenko.bulletinboard.entity.Bulletin;
import org.makarenko.bulletinboard.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BulletinController {

  private final BulletinService bulletinService;

  @Autowired
  public BulletinController(BulletinService bulletinService) {
    this.bulletinService = bulletinService;
  }

  @GetMapping("/bulletins")
  public Page<BulletinResponse> bulletins(@RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    PageRequest pageRequest = PageRequest.of(page - 1, size);
    return bulletinService.getBulletins(pageRequest);
  }

  @PostMapping(value = "/bulletins")
  public ResponseEntity<?> addBulletin(
      ServletRequest request, @Valid @RequestBody BulletinDto bulletinDto) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    String filePath = request.getServletContext().getRealPath("/");
    Bulletin bulletin = bulletinService.createBulletin(bulletinDto, filePath, email);
    return new ResponseEntity<>(bulletin, HttpStatus.CREATED);
  }

  @GetMapping("/bulletins/count")
  public Integer commentsCount() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return bulletinService.countByUserEmail(email);
  }

  @DeleteMapping("/bulletins/{id}")
  public ResponseEntity<?> deleteAdvertisement(@PathVariable Long id) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    if (bulletinService.checkIfUserIsBulletinAuthor(id, email)) {
      bulletinService.removeBulletin(id);
      return ResponseEntity.noContent().build();
    }
    return new ResponseEntity<>(new ErrorResponse("User is not author of bulletin"),
        HttpStatus.FORBIDDEN);
  }
}
