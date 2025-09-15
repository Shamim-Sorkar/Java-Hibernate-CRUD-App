package com.shamim.Hibernate_CRUD.util;

import com.shamim.Hibernate_CRUD.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class HibernateUtil {

    private SessionFactory sessionFactory;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.show-sql}")
    private boolean showSql;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @PostConstruct
    public void init() {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.connection.url", dbUrl);
        cfg.setProperty("hibernate.connection.username", dbUsername);
        cfg.setProperty("hibernate.connection.password", dbPassword);
        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cfg.setProperty("hibernate.dialect", hibernateDialect);
        cfg.setProperty("hibernate.show_sql", String.valueOf(showSql));
        cfg.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        cfg.addAnnotatedClass(User.class); // Register entity

        sessionFactory = cfg.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void shutdown() {
        if (sessionFactory != null) sessionFactory.close();
    }
}

