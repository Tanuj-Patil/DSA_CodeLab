package com.CodeLab.DB_Service.requestdto_converter;


import com.CodeLab.DB_Service.enums.SubmissionStatus;
import com.CodeLab.DB_Service.model.Contest;
import com.CodeLab.DB_Service.model.PartialContestSubmission;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.model.User;
import com.CodeLab.DB_Service.requestDTO.PartialContestSubmissionRequestDTO;

import java.time.LocalDateTime;

public class PartialContestSubmissionConverter {

    public static PartialContestSubmission contestPartialSubmissionConverter(PartialContestSubmissionRequestDTO requestDTO, Problem problem, User user, Contest contest){
        PartialContestSubmission submission = new PartialContestSubmission();

        submission.setSubmissionStatus(SubmissionStatus.PENDING);
        submission.setLanguage(requestDTO.getLanguage());
        submission.setProblem(problem);
        submission.setUser(user);
        submission.setContest(contest);
        submission.setCode(null);
        submission.setError(null);
        submission.setTotalTestCases(0);
        submission.setTotalPassedTestCases(0);
        submission.setPercentage(0.0);
        submission.setUserSubmittedAt(LocalDateTime.now());

        return submission;
    }
}
