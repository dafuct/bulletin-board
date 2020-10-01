package org.makarenko.bulletinboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:security.properties", encoding = "UTF-8")
@SpringBootApplication
public class BulletinBoardBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BulletinBoardBackendApplication.class, args);
  }

}
