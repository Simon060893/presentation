package com.payment_cracker.api.dao.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;

import java.util.Locale;


public class SessionManager {
    private static final SessionFactory factory;
    private static final ServiceRegistry serviceRegistry;
    private static Configuration configuration;
    private Session session;
    private Transaction transaction;

    public SessionManager() {
        session = factory.openSession();
        Locale.setDefault(Locale.ENGLISH);
    }

    static {
        Locale.setDefault(Locale.ENGLISH);
        configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    public void startSession() {
        if (session == null) {
            session = startFactory().openSession();
        } else {
            if (!session.isOpen()) {
                session = startFactory().openSession();
            }
        }
    }

    public SessionFactory startFactory() {
        if (factory.isClosed()) {
            return configuration.buildSessionFactory
                    (new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
        }
        return this.factory;
    }

    public Session getSession() {
        return session;
    }

    public void closeSession() {
        if (session != null) {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public void closeFactory() {
        factory.close();
    }

    public void startTransaction() {
        if (transaction != null) {
            if (transaction.isActive()) {
            } else if (!transaction.isActive()) {
                transaction = session.beginTransaction();
            }
        } else {
            transaction = session.beginTransaction();
        }
    }

    public void commitTransaction() {
        if (transaction != null) {
            if (transaction.isActive()) {
                transaction.commit();
            } else if (!transaction.isActive()) {
            }
        } else {
        }
    }

    public void rollbackTransaction() {
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

}
