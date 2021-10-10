package com.example.config;

import com.example.model.Basket;
import com.example.model.item.Item;
import com.example.model.group.Group;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.util.DatabaseConfigurationUtil;

import java.util.Properties;

@Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory(){
        return configure().buildSessionFactory();
    }


    public org.hibernate.cfg.Configuration configure() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addProperties(hibernateProperties());
        addAnnotatedClasses(configuration);

        return configuration;
    }

    public Properties hibernateProperties() {
        return DatabaseConfigurationUtil
                .readProperties("hibernate.properties");
    }

    private void addAnnotatedClasses(org.hibernate.cfg.Configuration configuration) {
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Group.class);
        configuration.addAnnotatedClass(Basket.class);
    }
}
