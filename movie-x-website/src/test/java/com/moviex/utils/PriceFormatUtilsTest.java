package com.moviex.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PriceFormatUtilsTest {
  
  @Test
  public void should_format_price_correctly() {
    long price = 15000;
    
    String actual = PriceFormatUtils.toFomattedString(price);
    String expected = "15.000 ₫";
    
    assertEquals(actual, expected);
  }
  
  @Test
  public void should_get_price_number_case_00001_correctly() {
    var price = "15.000₫";
    
    var actual = PriceFormatUtils.toNumber(price);
    long expected = 15000;
    
    assertEquals(actual, expected);
  }
  
  @Test
  public void should_get_price_number_case_00002_correctly() {
    var price = "15.000";
    
    var actual = PriceFormatUtils.toNumber(price);
    long expected = 15000;
    
    assertEquals(actual, expected);
  }
}
