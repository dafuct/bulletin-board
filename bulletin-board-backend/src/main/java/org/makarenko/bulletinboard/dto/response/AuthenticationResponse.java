package org.makarenko.bulletinboard.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponse {

  private String token;
  private String type = "Bearer";

  public AuthenticationResponse(String token) {
    this.token = token;
  }
}
