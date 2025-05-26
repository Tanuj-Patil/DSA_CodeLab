package com.CodeLab.Central_Service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Notification exchange and queue setup
    private final String notificationExchangeName = "codelab-notification-exchange";
    private final String notificationQueueName = "codelab-notification-queue";
    private final String notificationRoutingKey = "codelab-notification-route-123";

    // Code execution exchange and queue setup
    private final String codeExecutionExchangeName = "codelab-code-execution-exchange";
    private final String codeExecutionQueueName = "codelab-code-execution-queue";
    private final String codeExecutionRoutingKey = "codelab-code-execution-route";

    // ==============================
    // RabbitMQ Core Configurations
    // ==============================

    @Bean
    public CachingConnectionFactory getConnection() {
        CachingConnectionFactory connection = new CachingConnectionFactory("localhost");
        connection.setUsername("guest");
        connection.setPassword("guest");
        return connection;
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(CachingConnectionFactory connection) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    // ==============================
    // Notification Queue + Exchange
    // ==============================

    @Bean(name = "notificationQueue")
    public Queue getNotificationQueue() {
        return QueueBuilder.durable(notificationQueueName).build();
    }

    @Bean(name = "notificationExchange")
    public DirectExchange getNotificationExchange() {
        return new DirectExchange(notificationExchangeName);
    }

    @Bean
    public Binding bindNotificationQueueWithExchange(
            @Qualifier("notificationExchange") DirectExchange exchange,
            @Qualifier("notificationQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(notificationRoutingKey);
    }

    // ==============================
    // Code Execution Queue + Exchange
    // ==============================

    @Bean(name = "codeExecutionQueue")
    public Queue codeExecutionQueue() {
        return QueueBuilder.durable(codeExecutionQueueName).build();
    }

    @Bean(name = "codeExecutionExchange")
    public DirectExchange codeExecutionExchange() {
        return new DirectExchange(codeExecutionExchangeName);
    }

    @Bean
    public Binding bindCodeExecutionQueueWithExchange(
            @Qualifier("codeExecutionExchange") DirectExchange exchange,
            @Qualifier("codeExecutionQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(codeExecutionRoutingKey);
    }

    // ==============================
    // Utility Beans
    // ==============================

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
