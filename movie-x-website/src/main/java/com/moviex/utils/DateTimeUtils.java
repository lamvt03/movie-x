package com.moviex.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
  
  private static final DateTimeFormatter DATE_TIME_FORMATTER =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  
  public static String toFormattedDateTime(LocalDateTime localDateTime) {
    return localDateTime.format(DATE_TIME_FORMATTER);
  }
}
