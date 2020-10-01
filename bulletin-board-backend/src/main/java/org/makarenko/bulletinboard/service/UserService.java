package org.makarenko.bulletinboard.service;

import org.makarenko.bulletinboard.dto.response.AuthenticationResponse;
import org.makarenko.bulletinboard.dto.EditDto;
import org.makarenko.bulletinboard.dto.SignInDto;
import org.makarenko.bulletinboard.dto.SignUpDto;
import org.makarenko.bulletinboard.entity.User;

public interface UserService {

  boolean checkCredentials(SignInDto signInDto);

  AuthenticationResponse login(SignInDto signInDto);

  User createUser(SignUpDto signUpDTO);

  User updateUser(String email, EditDto editDto);

  User findUserByEmail(String email);

  boolean checkIfEmailExists(String email);

}
