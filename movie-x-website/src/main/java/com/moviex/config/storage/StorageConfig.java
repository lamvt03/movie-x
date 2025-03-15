package com.moviex.config.storage;

import com.moviex.storage.service.CloudStorageClient;
import com.moviex.storage.service.CloudinaryStorageClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
public class StorageConfig {
  
  @Produces
  @Default
  public CloudStorageClient cloudinaryStorageClient(@ConfigProperties CloudinaryConfigurationProperties configurationProperties) {
    return new CloudinaryStorageClient(configurationProperties);
  }
}
