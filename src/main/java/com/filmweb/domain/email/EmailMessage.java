package com.filmweb.domain.email;

import java.util.Map;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class EmailMessage {
  
  String to;
  String subject;
  String templateId;
  
  @Singular(value = "tag")
  Map<String, Object> tags;
}
