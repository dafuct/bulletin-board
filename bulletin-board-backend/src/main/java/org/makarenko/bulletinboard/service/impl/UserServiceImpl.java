package org.makarenko.bulletinboard.service.impl;

import java.util.Objects;
import org.makarenko.bulletinboard.dto.response.AuthenticationResponse;
import org.makarenko.bulletinboard.dto.EditDto;
import org.makarenko.bulletinboard.dto.SignInDto;
import org.makarenko.bulletinboard.dto.SignUpDto;
import org.makarenko.bulletinboard.entity.User;
import org.makarenko.bulletinboard.exception.UserByEmailAlreadyExistsException;
import org.makarenko.bulletinboard.exception.UserDataException;
import org.makarenko.bulletinboard.repository.UserRepository;
import org.makarenko.bulletinboard.service.UserService;
import org.makarenko.bulletinboard.util.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder encoder;
  private final JwtTokenProvider tokenProvider;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder,
      JwtTokenProvider tokenProvider) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.tokenProvider = tokenProvider;
  }

  @Override
  public boolean checkCredentials(SignInDto signInDto) {
    Objects.requireNonNull(signInDto);
    User userByEmail = userRepository.findByEmail(signInDto.getEmail());
    if (userByEmail != null) {
      return encoder.matches(signInDto.getPassword(), userByEmail.getPassword());
    }
    return false;
  }

  @Override
  public AuthenticationResponse login(SignInDto signInDto) {
    Objects.requireNonNull(signInDto);
    User userByEmail = userRepository.findByEmail(signInDto.getEmail());
    if (userByEmail != null) {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userByEmail, null, userByEmail.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      String jwt = tokenProvider
          .generateToken(SecurityContextHolder.getContext().getAuthentication());
      return new AuthenticationResponse(jwt);
    }
    throw new UserDataException("AuthenticationResponse is null");
  }

  @Override
  public User createUser(SignUpDto signUpDTO) {
    Objects.requireNonNull(signUpDTO);
    if (checkIfEmailExists(signUpDTO.getEmail())) {
      throw new UserByEmailAlreadyExistsException(
          "User with email" + signUpDTO.getEmail() + " exists");
    }

    User user = User.builder()
        .firstName(signUpDTO.getFirstName())
        .lastName(signUpDTO.getLastName())
        .email(signUpDTO.getEmail().toLowerCase())
        .password(encoder.encode(signUpDTO.getPassword()))
        .build();
    return userRepository.save(user);
  }

  @Override
  public User updateUser(String email, EditDto editDto) {
    Objects.requireNonNull(email);
    Objects.requireNonNull(editDto);

    User user = userRepository.findByEmail(email);
    if (user != null) {
      if (Objects.nonNull(editDto.getEmail())) {
        if (!email.equals(editDto.getEmail()) && checkIfEmailExists(editDto.getEmail())) {
          throw new UserDataException("User with email " + email + " already exists");
        }
        user.setEmail(editDto.getEmail().toLowerCase());
      }

      if (Objects.nonNull(editDto.getPassword())) {
        user.setPassword(encoder.encode(editDto.getPassword()));
      }
      if (Objects.nonNull(editDto.getFirstName())) {
        user.setFirstName(editDto.getFirstName());
      }
      if (Objects.nonNull(editDto.getLastName())) {
        user.setLastName(editDto.getLastName());
      }
      return userRepository.save(user);
    } else {
      throw new UserDataException("Update user is null");
    }
  }

  @Override
  public boolean checkIfEmailExists(String email) {
    Objects.requireNonNull(email);
    return userRepository.existsByEmail(email.toLowerCase());
  }

  @Override
  public User findUserByEmail(String email) {
    Objects.requireNonNull(email);
    if (userRepository.findByEmail(email.toLowerCase()) != null) {
      return userRepository.findByEmail(email.toLowerCase());
    }
    throw new UserDataException("User is null");
  }
}
