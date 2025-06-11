package com.CodeLab.Central_Service.model;

import com.CodeLab.Central_Service.enums.Language;
import com.CodeLab.Central_Service.enums.SubmissionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PartialContestSubmission {
    private UUID submissionId;

    private Contest contest;

    private User user;

    private Problem problem;

    private LocalDateTime userSubmittedAt;

    private int totalTestCases;

    private int totalPassedTestCases;

    private Double percentage;

    private String error;

    private String code;

    private SubmissionStatus submissionStatus;

    private Language language;


}
