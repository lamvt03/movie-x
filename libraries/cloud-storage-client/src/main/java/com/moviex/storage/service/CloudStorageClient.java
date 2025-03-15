package com.moviex.storage.service;

public interface CloudStorageClient {

  String upload(String pathWithoutExtension, byte[] data);
}
