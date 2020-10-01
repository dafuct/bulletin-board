package org.makarenko.bulletinboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {

  private String base64Value;
  private String fileExtension;

}
