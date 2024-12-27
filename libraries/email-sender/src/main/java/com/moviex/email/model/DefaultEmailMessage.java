package com.moviex.email.model;

import java.util.Map;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class DefaultEmailMessage implements EmailMessage {
  
  String from;
  String to;
  String subject;
  String templateId;
  
  @Singular(value = "tag")
  Map<String, Object> tags;
}
