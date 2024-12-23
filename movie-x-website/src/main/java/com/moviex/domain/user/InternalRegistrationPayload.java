package com.moviex.domain.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InternalRegistrationPayload {
  
  String email;
  String phone;
  String password;
  String fullName;
}
