package com.moviex.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GoogleUser {
  
  String email;
  String name;
  String picture;
}
