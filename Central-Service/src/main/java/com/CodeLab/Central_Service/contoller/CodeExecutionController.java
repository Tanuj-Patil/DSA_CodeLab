package com.CodeLab.Central_Service.contoller;

import com.CodeLab.Central_Service.exception.ErrorException;
import com.CodeLab.Central_Service.model.Submission;
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
        } catch (ErrorException e) {
            String errorMessage = e.getMessage().replace("com.CodeLab.Central_Service.exception.ErrorException: ", "");
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

        Submission submission = codeExecutionService.submitCode(requestDTO,responseDTO.getUserId());
        SubmissionResponseDTO submissionResponseDTO = new SubmissionResponseDTO();
        submissionResponseDTO.setSubmissionId(submission.getSubmissionId());
        submissionResponseDTO.setMessage("All Testcases are inserted into the messaging queue");

        return new ResponseEntity<>(submissionResponseDTO, HttpStatus.OK);
    }
}
