package org.makarenko.bulletinboard.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
