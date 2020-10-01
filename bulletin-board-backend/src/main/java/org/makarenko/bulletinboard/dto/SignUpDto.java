package org.makarenko.bulletinboard.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 8, max = 100)
  private String password;

  @NotBlank
  @Size(min = 1, max = 50)
  private String firstName;

  @NotBlank
  @Size(min = 1, max = 100)
  private String lastName;
}
