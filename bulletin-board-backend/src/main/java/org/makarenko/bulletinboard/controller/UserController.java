package org.makarenko.bulletinboard.controller;

import javax.validation.Valid;
import org.makarenko.bulletinboard.dto.DataStatus;
import org.makarenko.bulletinboard.dto.EditDto;
import org.makarenko.bulletinboard.dto.response.ErrorResponse;
import org.makarenko.bulletinboard.dto.SignInDto;
import org.makarenko.bulletinboard.dto.SignUpDto;
import org.makarenko.bulletinboard.entity.User;
import org.makarenko.bulletinboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody SignUpDto user) {
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@Valid @RequestBody SignInDto signInDto) {
    if (userService.checkIfEmailExists(signInDto.getEmail())) {
      if (userService.checkCredentials(signInDto)) {
        return new ResponseEntity<>(userService.login(signInDto), HttpStatus.OK);
      }
      return ResponseEntity.badRequest().body(new ErrorResponse("Wrong credentials"));
    }
    return ResponseEntity.badRequest().body(new ErrorResponse("User does not exist"));
  }

  @GetMapping("/user")
  public ResponseEntity<?> getUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findUserByEmail(email);
    if (user == null) {
      return new ResponseEntity<>(new ErrorResponse("User with such username not found"),
          HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PutMapping("/user")
  public ResponseEntity<?> editUser(@Valid @RequestBody EditDto editDto) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(userService.updateUser(email, editDto));
  }

  @GetMapping("/user/status")
  public ResponseEntity<?> emailStatus(@RequestParam String email) {
    return new ResponseEntity<>(new DataStatus(
        userService.checkIfEmailExists(email)), HttpStatus.OK);
  }
}
