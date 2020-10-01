package org.makarenko.bulletinboard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinDto {

  @NotBlank
  @Size(min = 1, max = 500)
  private String description;

  @NotBlank
  @Size(min = 1, max = 50)
  private String title;

  private String imageBase64;
  private String imageExtension;
}
