package com.CodeLab.Notification_Service.controller;

import com.CodeLab.Notification_Service.requestDTO.OTPGenerateRequestDTO;
import com.CodeLab.Notification_Service.responseDTO.GeneralResponseDTO;
import com.CodeLab.Notification_Service.service.SendMailService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SendMailService sendMailService;

    @RabbitListener(queues = "codelab-notification-queue")
    public void consumeMessage(@Payload OTPGenerateRequestDTO requestDTO) throws Exception{

        GeneralResponseDTO responseDTO = new GeneralResponseDTO();
        try {
            sendMailService.sendMail(requestDTO);


        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            System.out.println(requestDTO);
        }
    }
}
