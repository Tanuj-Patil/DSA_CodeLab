package com.CodeLab.Central_Service.integration;

import com.CodeLab.Central_Service.requestDTO.OTPGenerateRequestDTO;
import com.CodeLab.Central_Service.requestDTO.RunCodeRequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQIntegration {
    @Autowired
    RabbitTemplate rabbitTemplate;

    String notificationExchangeName = "codelab-notification-exchange";
    String notificationRoutingKey = "codelab-notification-route-123";

    public void insertMessageToQueue(OTPGenerateRequestDTO requestDTO){
        rabbitTemplate.convertAndSend(notificationExchangeName, notificationRoutingKey, requestDTO);
    }

    String executionExchangeName = "codelab-code-execution-exchange";
    String executionRoutingKey = "codelab-code-execution-route";

    public void sendCodeExecutionRequest(RunCodeRequestDTO requestDTO) {
        rabbitTemplate.convertAndSend(executionExchangeName, executionRoutingKey, requestDTO);
    }

}
