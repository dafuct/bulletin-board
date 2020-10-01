package org.makarenko.bulletinboard.exception;

public class UserByEmailAlreadyExistsException extends RuntimeException {

  public UserByEmailAlreadyExistsException(String message) {
    super(message);
  }
}
