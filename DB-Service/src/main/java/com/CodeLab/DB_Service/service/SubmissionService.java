package com.CodeLab.DB_Service.service;

import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.model.Submission;
import com.CodeLab.DB_Service.model.User;
import com.CodeLab.DB_Service.repository.SubmissionRepo;
import com.CodeLab.DB_Service.requestDTO.SubmissionRequestDTO;
import com.CodeLab.DB_Service.requestDTO.UpdateSubmissionRequestDTO;
import com.CodeLab.DB_Service.requestdto_converter.SubmissionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubmissionService {
    @Autowired
    SubmissionRepo submissionRepo;

    public Submission addSubmission(SubmissionRequestDTO requestDTO, Problem problem, User user){
        Submission submission = SubmissionConverter.submissionConverter(requestDTO,problem,user);
        submissionRepo.save(submission);
        return submission;
    }

    public Submission getSubmission(UUID submissionId){

        return submissionRepo.findById(submissionId).orElse(null);
    }

    public Submission updateSubmissionStatus(UpdateSubmissionRequestDTO requestDTO){
        Submission submission = this.getSubmission(requestDTO.getSubmissionId());
        submission.setSubmissionStatus(requestDTO.getSubmissionStatus());
        submission.setTimeComplexity(requestDTO.getTC());
        submission.setSpaceComplexity(requestDTO.getSC());
        submission.setError(requestDTO.getError());
        submission.setCode(requestDTO.getCode());
        submission.setLastInput(requestDTO.getLastInput());
        submission.setLastOutput(requestDTO.getLastOutput());
        submission.setLastExpectedOutput(requestDTO.getLastExpectedOutput());
        submission.setTotalTestCases(requestDTO.getTotalTestCases());
        submission.setTotalPassedTestCases(requestDTO.getTotalPassedTestCases());

        submissionRepo.save(submission);
        return submission;
    }

    public Submission getSubmissionById(UUID submissionId){
        return submissionRepo.findById(submissionId).orElse(null);
    }

    public List<Submission> getSubmissionsByUserId(UUID userId){

        return submissionRepo.findAllByUserId(userId);
    }



    public List<Submission> getSubmissionsByUserIdAndProblem(UUID userId,UUID problemId){
        return submissionRepo.findAllByUserAndProblem(userId,problemId);
    }

    public Submission getLatestSubmissionByUserId(UUID userId){
        return submissionRepo.findLatestSubmissionByUserId(userId).orElse(null);
    }

    public List<Submission> getSubmissionsByProblemId(UUID problemId){
        return submissionRepo.findAllByProblemId(problemId);
    }

    public List<Submission> getAllSubmissions(){
        return submissionRepo.findAll();
    }




}
