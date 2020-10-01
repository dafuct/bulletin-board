package org.makarenko.bulletinboard.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.makarenko.bulletinboard.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${app.secret.key}")
  private String SECRET_KEY;

  @Value("${app.expiration.time}")
  private int EXPIRATION_TIME;

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    long time = new Date().getTime();
    Date date = new Date(time + EXPIRATION_TIME * 1000);
    Claims claims = Jwts.claims()
        .setSubject(user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(date);
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      System.out.println("Invalid JWT Signature");
    } catch (MalformedJwtException e) {
      System.out.println("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      System.out.println("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      System.out.println("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      System.out.println("JWT claims strong is empty");
    }
    return false;
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
