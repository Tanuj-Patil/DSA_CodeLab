package com.CodeLab.Central_Service.contoller;

import com.CodeLab.Central_Service.model.Submission;
import com.CodeLab.Central_Service.responseDTO.GeneralResponseDTO;
import com.CodeLab.Central_Service.responseDTO.TokenValidationResponseDTO;
import com.CodeLab.Central_Service.service.AuthenticationService;
import com.CodeLab.Central_Service.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/central/submission")
public class SubmissionController {
    @Autowired
    SubmissionService submissionService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/get-by-id/{submissionId}")
    public ResponseEntity<?> getSubmissionById(@PathVariable UUID submissionId,@RequestHeader(value = "Authorization", required = false) String header){

        TokenValidationResponseDTO responseDTO = authenticationService.validateUserToken(header);
        System.out.println(responseDTO.getMessage());

        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }

        Submission submission = submissionService.getSubmissionById(submissionId);
        if(submission == null){
            GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
            generalResponseDTO.setMessage("Submission with id-"+submissionId+" not Found!!!");
            return new ResponseEntity<>(generalResponseDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(submission, HttpStatus.FOUND);
    }

    @GetMapping("/get-by-user-id")
    public ResponseEntity<?> getSubmissionsByUserId(@RequestHeader(value = "Authorization", required = false) String header){
        TokenValidationResponseDTO responseDTO = authenticationService.validateUserToken(header);
        System.out.println(responseDTO.getMessage());

        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }

        UUID userId = responseDTO.getUserId();

        List<Submission> submissions = submissionService.getSubmissionByUserId(userId);
        return new ResponseEntity<>(submissions, HttpStatus.FOUND);
    }
    @GetMapping("/get-by-user-id-and-problem-id")
    public ResponseEntity<?> getSubmissionsByUserId(@RequestParam UUID problemId,@RequestHeader(value = "Authorization", required = false) String header){
        TokenValidationResponseDTO responseDTO = authenticationService.validateUserToken(header);
        System.out.println(responseDTO.getMessage());

        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }

        UUID userId = responseDTO.getUserId();

        List<Submission> submissions = submissionService.getSubmissionByUserIdAndProblem(userId,problemId);
        return new ResponseEntity<>(submissions, HttpStatus.FOUND);
    }
}
