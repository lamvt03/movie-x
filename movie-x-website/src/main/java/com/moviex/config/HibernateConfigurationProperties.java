package com.moviex.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "hibernate")
@ApplicationScoped
@Getter
public class HibernateConfigurationProperties {
    
    String driverClass;
    String url;
    String username;
    String password;
    String dialect;
    String ddlAuto;
}
