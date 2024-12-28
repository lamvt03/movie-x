package com.moviex.template.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class TemplateEngineConfig {
  
  @Produces
  @Named("emailTemplateConfiguration")
  public Configuration sendEmailExecutor() {
    var configuration = new Configuration(Configuration.VERSION_2_3_34);
    
    configuration.setClassLoaderForTemplateLoading(
        Thread.currentThread().getContextClassLoader(), "templates/email");
    configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    
    return configuration;
  }
}
