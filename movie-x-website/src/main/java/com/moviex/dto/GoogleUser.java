package com.moviex.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GoogleUser {
  
  String id;
  String email;
  Boolean verified_email;
  String name;
  String given_name;
  String family_name;
  String link;
  String picture;
}
