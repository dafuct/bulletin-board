package org.makarenko.bulletinboard.service.impl;

import org.makarenko.bulletinboard.entity.User;
import org.makarenko.bulletinboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceDetail implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public CustomUserServiceDetail(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username.toLowerCase());
    if (user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Username does not exist");
    }
  }
}
