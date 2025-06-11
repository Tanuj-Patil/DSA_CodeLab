package com.CodeLab.Code_Execution_Service.integration;

import com.CodeLab.Code_Execution_Service.enums.SubmissionStatus;
import com.CodeLab.Code_Execution_Service.requestDTO.UpdatePartialContestSubmissionRequestDTO;
import com.CodeLab.Code_Execution_Service.requestDTO.UpdateSubmissionRequestDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.GeneralResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class DBService extends RestAPI{
    @Value("${db.service.base}")
    String baseURL;

    @Autowired
    ModelMapper modelMapper;

    public GeneralResponseDTO callUpdateSubmission(UpdateSubmissionRequestDTO requestDTO){
        String endpoint = "/submission/update-status";
        Object object = this.makePutCall(baseURL,endpoint,requestDTO,new HashMap<>());
        return modelMapper.map(object,GeneralResponseDTO.class);
    }
    public GeneralResponseDTO callUpdatePartialContestSubmission(UpdatePartialContestSubmissionRequestDTO requestDTO){
        String endpoint = "/contest/update-status";
        Object object = this.makePutCall(baseURL,endpoint,requestDTO,new HashMap<>());
        return modelMapper.map(object,GeneralResponseDTO.class);
    }

}
