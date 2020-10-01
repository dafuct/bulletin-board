package org.makarenko.bulletinboard.service;

import org.makarenko.bulletinboard.dto.ImageDto;

public interface ImageService {

  String saveImage(String imageBase64, String imageExtension, String path);

  ImageDto getImage(String fullPath);
}


