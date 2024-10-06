package com.filmweb.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PriceFormatUtilsTest {
  
  @Test
  public void should_format_price_correctly() {
    long price = 15000;
    
    String actual = PriceFormatUtils.formatPrice(price);
    String expected = "15.000 ₫";
    
    assertEquals(actual, expected);
  }
}
