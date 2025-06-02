package com.CodeLab.Central_Service.model;

import com.CodeLab.Central_Service.enums.Language;
import com.CodeLab.Central_Service.enums.SubmissionStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Submission {

    private UUID submissionId;

    private SubmissionStatus submissionStatus;

    private Language language;

    private String timeComplexity;

    private String spaceComplexity;

    private String error;

    private LocalDateTime submittedAt;

    private User user;

    private Problem problem;

    private  String lastInput;

    private String lastOutput;

    private String lastExpectedOutput;

    private String code;

}

