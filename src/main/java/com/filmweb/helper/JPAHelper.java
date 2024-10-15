package com.filmweb.helper;

import com.filmweb.config.HibernateConfigurationProperties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.hibernate.cfg.Environment;

@ApplicationScoped
public class JPAHelper {

    @Inject
    @ConfigProperties(prefix = "hibernate")
    private HibernateConfigurationProperties hibernateConfigurationProperties;

    private EntityManagerFactory factory;

    private synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null || !factory.isOpen()) {
            String PERSISTENCE_UNIT_NAME = "movie-x-persistence";
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, buildHibernateProperties());
        }
        return factory;
    }

    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public void shutDown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
        factory = null;
    }

    private Map<String, Object> buildHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        //datasource
        properties.put(Environment.DRIVER, hibernateConfigurationProperties.getDriverClass());
        properties.put(Environment.URL, hibernateConfigurationProperties.getUrl());
        properties.put(Environment.USER, hibernateConfigurationProperties.getUsername());
        properties.put(Environment.PASS, hibernateConfigurationProperties.getPassword());
        
        //action
        properties.put(Environment.DIALECT, hibernateConfigurationProperties.getDialect());
        properties.put(Environment.HBM2DDL_AUTO, hibernateConfigurationProperties.getDdlAuto());
        
        return properties;
    }
}
