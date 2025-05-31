package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.enums.SubmissionStatus;
import com.CodeLab.DB_Service.model.*;
import com.CodeLab.DB_Service.repository.ProblemRepo;
import com.CodeLab.DB_Service.repository.UserRepo;
import com.CodeLab.DB_Service.requestDTO.SolutionRequestDTO;
import com.CodeLab.DB_Service.requestDTO.SubmissionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubmissionConverter {


    public static Submission submissionConverter(SubmissionRequestDTO requestDTO,Problem problem,User user){
        Submission submission = new Submission();

        submission.setSubmissionStatus(SubmissionStatus.PENDING);
        submission.setLanguage(requestDTO.getLanguage());
        submission.setProblem(problem);
        submission.setUser(user);
        submission.setMemory("");
        submission.setRuntime("");
        submission.setSpaceComplexity("");
        submission.setTimeComplexity("");

        return submission;
    }
}
