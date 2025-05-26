package com.CodeLab.Central_Service.integration;

import com.CodeLab.Central_Service.requestDTO.OTPGenerateRequestDTO;
import com.CodeLab.Central_Service.responseDTO.GeneralResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NotificationService extends RestAPI {
    @Autowired
    ModelMapper modelMapper;

    @Value("${notification.service.base}")
    String baseURL;

    public GeneralResponseDTO callSendMail(OTPGenerateRequestDTO requestDTO){
        String endPoint = "/email/registration-otp";

        Object response = this.makePostCall(baseURL,endPoint,requestDTO,new HashMap<>());

        return modelMapper.map(response,GeneralResponseDTO.class);
    }
}
