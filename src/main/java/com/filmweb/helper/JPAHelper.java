package com.filmweb.helper;

import com.filmweb.config.HibernateConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JPAHelper {

    @Inject
    private HibernateConfig hibernateConfig;

    private EntityManagerFactory factory;

    private synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null || !factory.isOpen()) {
            String PERSISTENCE_UNIT_NAME = "movie-x-persistence";
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, hibernateConfig.getProperties());
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

}
