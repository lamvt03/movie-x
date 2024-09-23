package com.filmweb.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SlugUtilsTest {
  
  @Test
  public void should_generate_slug_case1_correctly() {
    String name = "Ông trùm báo thù";
    
    String actual = SlugUtils.generateSlug(name, null);
    String expected = "ong-trum-bao-thu";
    
    assertEquals(actual, expected);
  }
  
  @Test
  public void should_generate_slug_case2_correctly() {
    String name = "Ông trùm báo thù - Thuyết minh";
    
    String actual = SlugUtils.generateSlug(name, null);
    String expected = "ong-trum-bao-thu-thuyet-minh";
    
    assertEquals(actual, expected);
  }
  
  @Test
  public void should_generate_slug_case3_correctly() {
    String name = "Ông trùm báo thù";
    
    String actual = SlugUtils.generateSlug(name, "random-suffix");
    String expected = "ong-trum-bao-thu-random-suffix";
    
    assertEquals(actual, expected);
  }
}
