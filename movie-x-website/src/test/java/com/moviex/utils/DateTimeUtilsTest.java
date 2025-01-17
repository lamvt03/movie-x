package com.moviex.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class DateTimeUtilsTest {
  
  @Test
  public void should_format_date_time_correctly() {
    var dateTime = LocalDateTime.of(2025, 1, 1, 10, 20, 30);
    
    String actual = DateTimeUtils.toFormattedDateTime(dateTime);
    String expected = "01-01-2025 10:20:30";
    
    assertEquals(actual, expected);
  }
}
