package org.makarenko.bulletinboard.repository;

import org.makarenko.bulletinboard.entity.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

  Page<Bulletin> findAllByOrderByPublishDateDesc(Pageable page);

  Integer countByUserEmail(String email);

}
