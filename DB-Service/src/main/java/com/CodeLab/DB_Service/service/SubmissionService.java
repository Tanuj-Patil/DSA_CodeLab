package com.CodeLab.DB_Service.service;

import com.CodeLab.DB_Service.model.Submission;
import com.CodeLab.DB_Service.repository.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubmissionService {
    @Autowired
    SubmissionRepo submissionRepo;

    public List<Submission> getSubmissions(UUID userId){
        return submissionRepo.findAllByUser(userId);
    }

    public List<Submission> getSubmissions(UUID userId,UUID problemId){
        return submissionRepo.findAllByUserAndProblem(userId,problemId);
    }

    public Submission getSubmission(UUID submissionId){
        return submissionRepo.findById(submissionId).orElse(null);
    }
}
