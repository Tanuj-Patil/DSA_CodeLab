package com.CodeLab.Central_Service.service;

import com.CodeLab.Central_Service.integration.DBService;
import com.CodeLab.Central_Service.model.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubmissionService {
    @Autowired
    DBService dbService;
    public Submission getSubmissionById(UUID submissionId){
        return dbService.callGetSubmissionById(submissionId);
    }

    public List<Submission> getSubmissionsByUserId(UUID userId){
        return dbService.callGetSubmissionByUserId(userId);
    }

    public List<Submission> getSubmissionsByUserIdAndProblemId(UUID userId, UUID problemId){
        return dbService.callGetSubmissionByUserIdAndProblemId(userId,problemId);
    }

    public Submission getLatestSubmissionByUserId(UUID userId){
        return dbService.callGetLatestSubmissionOfUser(userId);
    }

    public List<Submission> getSubmissionsByProblemId(UUID problemId){
        return dbService.callGetSubmissionsByProblemId(problemId);
    }

    public List<Submission> getAllSubmissions(){
        return dbService.callGetAllSubmissions();
    }
}
