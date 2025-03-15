package com.moviex.config.storage;

import com.moviex.storage.config.CloudStorageClientProperties;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "cloudinary")
@ApplicationScoped
@Getter
public class CloudinaryConfigurationProperties implements CloudStorageClientProperties {
  
  String clientId;
  String clientSecret;
  String bucketName;
}
