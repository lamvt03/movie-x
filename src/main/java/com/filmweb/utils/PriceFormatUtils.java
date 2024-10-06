package com.filmweb.utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class PriceFormatUtils {
  
  private static final Locale locale;
  private static final Currency currency;

  static {
    locale = new Locale("vi", "VN");
    currency = Currency.getInstance("VND");
  }
  
  public static String formatPrice(long price) {
    var formatter = NumberFormat.getCurrencyInstance(locale);
    formatter.setCurrency(currency);
    
    return formatter.format(price);
  }
}
