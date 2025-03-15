package com.moviex.storage.service;

import com.cloudinary.Cloudinary;
import com.moviex.storage.config.CloudStorageClientProperties;
import java.io.IOException;
import java.util.Map;

public class CloudinaryStorageClient implements CloudStorageClient {
  
  private static final String URL_PATTERN = "cloudinary://%s:%s@%s";
  private final Cloudinary cloudinaryClient;
  
  public CloudinaryStorageClient(CloudStorageClientProperties properties) {
    var cloudinaryUrl = String.format(URL_PATTERN, properties.getClientId(), properties.getClientSecret(), properties.getBucketName());
    this.cloudinaryClient = new Cloudinary(cloudinaryUrl);
  }
  
  @Override
  public String upload(String pathWithoutExtension, byte[] data) {
    try {
      var response = cloudinaryClient.uploader().upload(data, Map.of(
          "public_id", pathWithoutExtension,
          "overwrite", true
      ));
      return response.get("secure_url").toString();
    } catch (IOException e) {
      System.out.println("Error while uploading file: " + e.getMessage());
      return null;
    }
  }
}
