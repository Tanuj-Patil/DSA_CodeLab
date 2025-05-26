package com.CodeLab.Code_Execution_Service.controller;

import com.CodeLab.Code_Execution_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.SubmitCodeResponseDTO;
import com.CodeLab.Code_Execution_Service.service.ExecuteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execute")
public class ExecuteCodeController {

    @Autowired
    ExecuteCodeService codeService;


    @PostMapping("/run")
    public ResponseEntity<?> runCode(@RequestBody RunCodeRequestDTO requestDTO){
        RunCodeResponseDTO responseDTO = codeService.runCode(requestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitCode(@RequestBody RunCodeRequestDTO requestDTO){
            SubmitCodeResponseDTO responseDTO = codeService.getTimeAndSpaceComplexities(requestDTO.getCode());
            return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }
}
