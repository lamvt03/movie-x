package com.moviex.exception;

public class MovieXException extends RuntimeException {
  
  public MovieXException(String message) {
    super(message);
  }
  
  public MovieXException(Throwable cause) {
    super(cause);
  }
  
  public MovieXException(String message, Throwable cause) {
    super(message, cause);
  }
}
