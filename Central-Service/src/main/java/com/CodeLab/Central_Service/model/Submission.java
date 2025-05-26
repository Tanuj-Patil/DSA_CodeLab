package com.CodeLab.Central_Service.model;

import com.CodeLab.Central_Service.enums.Language;
import com.CodeLab.Central_Service.enums.SubmissionStatus;

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


    private String runtime;


    private String memory;


    private LocalDateTime submittedAt;


    private User user;


    private Problem problem;



}
