package com.filmweb.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Getter
public class HibernateConfig {

    private Map<String, Object> properties;

    @Inject
    @ConfigProperty(name="hibernate.driver-class")
    private String driverClass;

    @Inject
    @ConfigProperty(name="hibernate.url")
    private String url;

    @Inject
    @ConfigProperty(name = "hibernate.username")
    private String username;

    @Inject
    @ConfigProperty(name = "hibernate.password")
    private String password;

    @Inject
    @ConfigProperty(name = "hibernate.dialect")
    private String dialect;

    @Inject
    @ConfigProperty(name = "hibernate.ddl-auto")
    private String ddlAuto;

    @PostConstruct
    public void init() {
        properties = new HashMap<>();

        //datasource
        properties.put(Environment.DRIVER, driverClass);
        properties.put(Environment.URL, url);
        properties.put(Environment.USER, username);
        properties.put(Environment.PASS, password);

        //action
        properties.put(Environment.DIALECT, dialect);
        properties.put(Environment.HBM2DDL_AUTO, ddlAuto);

    }
}
