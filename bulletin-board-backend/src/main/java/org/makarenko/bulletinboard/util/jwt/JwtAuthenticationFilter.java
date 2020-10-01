package org.makarenko.bulletinboard.util.jwt;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.makarenko.bulletinboard.service.impl.CustomUserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Value("${app.token.prefix}")
  private String TOKEN_PREFIX;

  @Value("${app.header}")
  private String HEADER_STRING;

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserServiceDetail userService;

  @Autowired
  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
      CustomUserServiceDetail userService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = getTokenFromRequest(httpServletRequest);
      if (token != null && jwtTokenProvider.validateToken(token)) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                Collections.emptyList());
        authenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    } catch (Exception e) {
      logger.error("Could not set user authentication in security context", e);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader(HEADER_STRING);
    if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
      return authHeader.replace(TOKEN_PREFIX, "");
    }
    return null;
  }
}
