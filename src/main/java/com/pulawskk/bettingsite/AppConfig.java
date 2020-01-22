package com.pulawskk.bettingsite;

import com.pulawskk.bettingsite.controllers.EventController;
import com.pulawskk.bettingsite.services.OutcomingDataService;
import com.rabbitmq.client.ConnectionFactory;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableJms
@EnableScheduling
public class AppConfig {

    @Bean
    public ConnectionFactory createNewConnectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String URI = "amqp://mxddlpbm:3nP42tVOl_XbgGvhODI8nIu4GdAPXB2g@golden-kangaroo.rmq.cloudamqp.com/mxddlpbm";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(URI);
        return connectionFactory;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
