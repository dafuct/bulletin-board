package org.makarenko.bulletinboard.controller;

import org.makarenko.bulletinboard.dto.response.ErrorResponse;
import org.makarenko.bulletinboard.exception.ImageException;
import org.makarenko.bulletinboard.exception.UserDataException;
import org.makarenko.bulletinboard.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ErrorHandlingController {

  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler({UserDataException.class})
  public ResponseEntity<ErrorResponse> handleUserDataException(UserDataException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler({ImageException.class})
  public ResponseEntity<ErrorResponse> handleImageException(ImageException e) {
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(e.getLocalizedMessage() + ", bulletin not saved"));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleNotValidException(
      MethodArgumentNotValidException e) {
    FieldError fieldError = e.getBindingResult().getFieldError();
    String res;
    if (fieldError != null) {
      res = "Error in object " + fieldError.getObjectName() +
          ", field " + fieldError.getField() + ": " + fieldError.getDefaultMessage();
    } else {
      res = "Object is not valid";
    }
    return ResponseEntity.badRequest().body(new ErrorResponse(res));
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<ErrorResponse> handleTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e.getLocalizedMessage()));
  }
}
