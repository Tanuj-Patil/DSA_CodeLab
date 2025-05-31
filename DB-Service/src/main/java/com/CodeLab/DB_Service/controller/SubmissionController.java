package com.CodeLab.DB_Service.controller;

import com.CodeLab.DB_Service.enums.SubmissionStatus;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.model.Submission;
import com.CodeLab.DB_Service.model.User;
import com.CodeLab.DB_Service.requestDTO.SubmissionRequestDTO;
import com.CodeLab.DB_Service.requestDTO.UpdateSubmissionRequestDTO;
import com.CodeLab.DB_Service.responseDTO.GeneralResponseDTO;
import com.CodeLab.DB_Service.service.ProblemService;
import com.CodeLab.DB_Service.service.SubmissionService;
import com.CodeLab.DB_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/db/submission")
public class SubmissionController {
    @Autowired
    SubmissionService submissionService;

    @Autowired
    ProblemService problemService;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public Submission addSubmission(@RequestBody SubmissionRequestDTO requestDTO){
        Problem problem = problemService.getProblem(requestDTO.getProblemId());
        User user = userService.getUserById(requestDTO.getUserId());
        Submission submission = submissionService.addSubmission(requestDTO,problem,user);
        return submission;
    }

    @PutMapping("/update-status")
    public GeneralResponseDTO updateSubmissionStatus(@RequestBody UpdateSubmissionRequestDTO requestDTO){
        Submission submission = submissionService.updateSubmissionStatus(requestDTO);
        GeneralResponseDTO responseDTO = new GeneralResponseDTO();
        responseDTO.setMessage("The submission status of Submission with id"+requestDTO.getSubmissionId()+" has been update to "+requestDTO.getSubmissionStatus());
        return responseDTO;
    }


}
