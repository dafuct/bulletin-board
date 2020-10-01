package org.makarenko.bulletinboard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.makarenko.bulletinboard.dto.ImageDto;
import org.makarenko.bulletinboard.entity.Bulletin;
import org.makarenko.bulletinboard.entity.User;

@Data
@AllArgsConstructor
public class BulletinResponse {

  private Long id;

  private ImageDto image;

  private String title;

  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime publishDate;

  private User user;

  public BulletinResponse(Bulletin bulletin) {
    this.id = bulletin.getId();
    this.title = bulletin.getTitle();
    this.description = bulletin.getDescription();
    this.publishDate = bulletin.getPublishDate();
    this.user = bulletin.getUser();
  }
}
