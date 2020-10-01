package org.makarenko.bulletinboard.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorResponse {

  private LocalDateTime timestamp;
  private String message;

  public ErrorResponse(String message) {
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }
}
