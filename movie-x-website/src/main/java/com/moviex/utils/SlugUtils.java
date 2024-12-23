package com.moviex.utils;

import java.text.Normalizer;

public class SlugUtils {
  
  private static final String HYPHEN = "-";

  public static String generateSlug(String name, String additionalString) {
    String slug = generateSlug(name);
    
    if (additionalString != null && !additionalString.isBlank()) {
      slug = slug + HYPHEN + additionalString;
    }
    
    return slug;
  }
  
  public static String generateSlug(String name) {
    String slug = name.trim().toLowerCase();
    
    slug = Normalizer.normalize(slug, Normalizer.Form.NFC);
    
    // Remove accented character
    slug = slug.replaceAll("[áàảãạăắằẳẵặâấầẩẫậ]", "a")
               .replaceAll("[éèẻẽẹêếềểễệ]", "e")
               .replaceAll("[íìỉĩị]", "i")
               .replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o")
               .replaceAll("[úùủũụưứừửữự]", "u")
               .replaceAll("[ýỳỷỹỵ]", "y")
               .replaceAll("[đ]", "d");
    
    // Remove spaces with hyphens
    slug = slug.replaceAll("\\s+", "-");
    
    // Remove consecutive hyphens
    slug = slug.replaceAll("-+", "-");
    
    return slug;
  }
}
