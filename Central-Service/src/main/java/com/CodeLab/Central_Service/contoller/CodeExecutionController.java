package com.CodeLab.Central_Service.contoller;

import com.CodeLab.Central_Service.requestDTO.CodeRequestDTO;
import com.CodeLab.Central_Service.responseDTO.*;
import com.CodeLab.Central_Service.service.AuthenticationService;
import com.CodeLab.Central_Service.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/central/execute")
public class CodeExecutionController {
    @Autowired
    CodeExecutionService codeExecutionService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/run")
    public ResponseEntity<?> runCode(@RequestBody CodeRequestDTO requestDTO, @RequestHeader(value = "Authorization", required = false) String header) {
        TokenValidationResponseDTO responseDTO = authenticationService.validateToken(header);
        
        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
        try {
            List<CentralServiceRunCodeResponse> responseDTOS = codeExecutionService.runCode(requestDTO);
            return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage().replace("com.CodeLab.Central_Service.exception.ErrorException: ", "");
            ;
            return new ResponseEntity<>(errorMessage, HttpStatus.OK);
        }
    }


    @PostMapping("/submit")
    public ResponseEntity<?> submitCode(@RequestBody CodeRequestDTO requestDTO, @RequestHeader(value = "Authorization", required = false) String header) {
        TokenValidationResponseDTO responseDTO = authenticationService.validateToken(header);
        System.out.println(responseDTO.getMessage());

        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
        SubmitCodeResponseDTO response = codeExecutionService.submitCode(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
