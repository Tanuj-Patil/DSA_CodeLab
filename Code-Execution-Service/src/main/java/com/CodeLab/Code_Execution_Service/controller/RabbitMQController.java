package com.CodeLab.Code_Execution_Service.controller;

import com.CodeLab.Code_Execution_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Code_Execution_Service.service.ExecuteCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ExecuteCodeService executeCodeService;

    @RabbitListener(queues = "codelab-code-execution-queue")
    public void consumeMessage(@Payload RunCodeRequestDTO requestDTO) throws Exception{

        try {
            executeCodeService.runCode(requestDTO);
            System.out.println("Code executed successfully.");
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
        }

    }
}
