package com.moviex.template.service;

import com.moviex.template.exception.TemplateProcessException;
import java.util.Map;

public interface TemplateService {
  
  String processTemplate(String template, Map<String, Object> context) throws TemplateProcessException;
}
