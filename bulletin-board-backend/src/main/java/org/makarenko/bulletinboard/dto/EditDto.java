package org.makarenko.bulletinboard.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditDto {

  @Email
  private String email;

  @Size(min = 8, max = 100)
  private String password;

  @Size(min = 1, max = 50)
  private String firstName;

  @Size(min = 1, max = 100)
  private String lastName;
}
