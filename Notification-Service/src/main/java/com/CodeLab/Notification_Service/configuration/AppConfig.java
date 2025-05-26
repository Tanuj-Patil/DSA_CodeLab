package com.CodeLab.Notification_Service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Value("${spring.mail.username}")
    String ourEmail;

    @Value("${spring.mail.password}")
    String ourPassword;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configure mail server properties
        mailSender.setHost("smtp.gmail.com"); // For Gmail SMTP (change to your email provider's SMTP)
        mailSender.setPort(587); // Use 465 for SSL, 587 for TLS

        mailSender.setUsername(ourEmail); // Your email
        mailSender.setPassword(ourPassword); // Your email password or app password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;

    }

    @Bean
    public TemplateEngine getTemplateEngine(){
        return new TemplateEngine();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
