package org.makarenko.bulletinboard.util.security;

import org.makarenko.bulletinboard.service.impl.CustomUserServiceDetail;
import org.makarenko.bulletinboard.util.jwt.JwtAuthenticationEntryPoint;
import org.makarenko.bulletinboard.util.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomUserServiceDetail userService;
  private final JwtAuthenticationFilter authFilter;
  private final JwtAuthenticationEntryPoint authEntryPoint;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public SecurityConfig(CustomUserServiceDetail userService,
      JwtAuthenticationFilter authFilter,
      JwtAuthenticationEntryPoint authEntryPoint,
      BCryptPasswordEncoder encoder) {
    this.userService = userService;
    this.authFilter = authFilter;
    this.authEntryPoint = authEntryPoint;
    this.encoder = encoder;
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE");
      }
    };
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
        .passwordEncoder(encoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/bulletins").permitAll()
        .antMatchers("/user/status").permitAll()
        .antMatchers("/signup", "/signin").anonymous()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(authEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
