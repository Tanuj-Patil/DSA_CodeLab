package com.CodeLab.Central_Service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    // Code execution exchange setup (shared)
    private final String codeExecutionExchangeName = "codelab-code-execution-exchange";

    // Old queue (optional, keep if still used)
    private final String codeExecutionQueueName = "codelab-code-execution-queue";
    private final String codeExecutionRoutingKey = "codelab-code-execution-route";

    // New queues and routing keys
    private final String normalCodeExecutionQueueName = "normal-submission-queue";
    private final String normalRoutingKey = "normal-code-execution-route";

    private final String contestCodeExecutionQueueName = "contest-submission-queue";
    private final String contestRoutingKey = "contest-code-execution-route";

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
    // Shared Code Execution Exchange
    // ==============================

    @Bean(name = "codeExecutionExchange")
    public DirectExchange codeExecutionExchange() {
        return new DirectExchange(codeExecutionExchangeName);
    }

    // ==============================
    // Old Code Execution Queue (Optional)
    // ==============================

    @Bean(name = "codeExecutionQueue")
    public Queue codeExecutionQueue() {
        return QueueBuilder.durable(codeExecutionQueueName).build();
    }

    @Bean
    public Binding bindCodeExecutionQueueWithExchange(
            @Qualifier("codeExecutionExchange") DirectExchange exchange,
            @Qualifier("codeExecutionQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(codeExecutionRoutingKey);
    }

    // ==============================
    // Normal Submission Queue
    // ==============================

    @Bean(name = "normalCodeExecutionQueue")
    public Queue normalCodeExecutionQueue() {
        return QueueBuilder.durable(normalCodeExecutionQueueName).build();
    }

    @Bean
    public Binding bindNormalCodeExecutionQueue(
            @Qualifier("codeExecutionExchange") DirectExchange exchange,
            @Qualifier("normalCodeExecutionQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(normalRoutingKey);
    }

    // ==============================
    // Contest Submission Queue
    // ==============================

    @Bean(name = "contestCodeExecutionQueue")
    public Queue contestCodeExecutionQueue() {
        return QueueBuilder.durable(contestCodeExecutionQueueName).build();
    }

    @Bean
    public Binding bindContestCodeExecutionQueue(
            @Qualifier("codeExecutionExchange") DirectExchange exchange,
            @Qualifier("contestCodeExecutionQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(contestRoutingKey);
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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())  // Handles LocalDateTime, etc.
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
