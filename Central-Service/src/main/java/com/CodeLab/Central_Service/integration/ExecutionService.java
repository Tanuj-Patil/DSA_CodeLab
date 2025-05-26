package com.CodeLab.Central_Service.integration;

import com.CodeLab.Central_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Central_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Central_Service.responseDTO.SubmitCodeResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class ExecutionService extends RestAPI{

    @Value("${code-execution.service.base}")
    String baseURL;

    @Autowired
    ModelMapper modelMapper;

    public RunCodeResponseDTO callRunCode(RunCodeRequestDTO runCodeRequestDTO){
        String endpoint = "/run";

        Object response = this.makePostCall(baseURL,endpoint,runCodeRequestDTO,new HashMap<>());

        return modelMapper.map(response,RunCodeResponseDTO.class);
    }

    public SubmitCodeResponseDTO callSubmitCode(RunCodeRequestDTO requestDTO){
        String endpoint = "/submit";


        Object response = this.makePostCall(baseURL,endpoint,requestDTO,new HashMap<>());

        return modelMapper.map(response,SubmitCodeResponseDTO.class);
    }
}
