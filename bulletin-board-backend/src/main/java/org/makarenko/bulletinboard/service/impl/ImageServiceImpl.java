package org.makarenko.bulletinboard.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.tomcat.util.codec.binary.Base64;
import org.makarenko.bulletinboard.dto.ImageDto;
import org.makarenko.bulletinboard.exception.ImageException;
import org.makarenko.bulletinboard.service.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

  @Override
  public String saveImage(String imageBase64, String imageExtension, String path) {
    Objects.requireNonNull(imageBase64);
    Objects.requireNonNull(imageExtension);
    Objects.requireNonNull(path);
    byte[] imageBytes = Base64.decodeBase64(imageBase64);
    String imagesDirectoryPath = path + "images";
    File imagesDirectory = new File(imagesDirectoryPath);
    if (!imagesDirectory.exists()) {
      boolean mkdir = imagesDirectory.mkdir();
    }
    try (Stream<Path> files = Files.list(Paths.get(imagesDirectoryPath))) {
      long imagesCount = files.count();
      String savedImageFullPath = imagesDirectoryPath + "\\ad" + imagesCount + imageExtension;
      new FileOutputStream(savedImageFullPath).write(imageBytes);
      return savedImageFullPath;
    } catch (Exception e) {
      throw new ImageException("Error saving bulletin image");
    }
  }

  @Override
  public ImageDto getImage(String fullPath) {
    Objects.requireNonNull(fullPath);
    File imageFile = new File(fullPath);
    try {
      String base64Value = Base64.encodeBase64String(Files.readAllBytes(imageFile.toPath()));
      String fileExtension = fullPath.substring(fullPath.lastIndexOf('.') + 1);
      return new ImageDto(base64Value, fileExtension);
    } catch (IOException e) {
      throw new ImageException(e);
    }
  }
}
