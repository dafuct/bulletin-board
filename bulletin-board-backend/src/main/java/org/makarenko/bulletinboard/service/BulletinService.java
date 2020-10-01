package org.makarenko.bulletinboard.service;

import org.makarenko.bulletinboard.dto.BulletinDto;
import org.makarenko.bulletinboard.dto.response.BulletinResponse;
import org.makarenko.bulletinboard.entity.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BulletinService {

  Bulletin createBulletin(BulletinDto bulletinDto, String imagePath, String email);

  Page<BulletinResponse> getBulletins(PageRequest pageRequest);

  Integer countByUserEmail(String email);

  boolean checkIfUserIsBulletinAuthor(Long id, String email);

  void removeBulletin(Long id);

}
