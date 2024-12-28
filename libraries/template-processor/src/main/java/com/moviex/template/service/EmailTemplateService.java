package com.moviex.template.service;

import com.moviex.template.exception.TemplateProcessException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.StringWriter;
import java.util.Map;

@ApplicationScoped
@Named("emailTemplateService")
public class EmailTemplateService implements TemplateService {
  
  private static final String HTML_SUFFIX = ".html";
  
  @Inject
  @Named("emailTemplateConfiguration")
  private Configuration configuration;
  
  @Override
  public String processTemplate(String templateId, Map<String, Object> context) throws TemplateProcessException {
    try {
      Template template = configuration.getTemplate(buildTemplateFileName(templateId));
      try (StringWriter writer = new StringWriter()) {
        template.process(context, writer);
        return writer.toString();
      }
    } catch (Exception e) {
      throw new TemplateProcessException("Failed to process template id: " + templateId, e);
    }
  }
  
  private String buildTemplateFileName(String templateId) {
    return templateId + HTML_SUFFIX;
  }
}
