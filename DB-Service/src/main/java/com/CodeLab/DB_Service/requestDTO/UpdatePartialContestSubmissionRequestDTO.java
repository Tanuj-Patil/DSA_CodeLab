package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.SubmissionStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UpdatePartialContestSubmissionRequestDTO {

    private UUID submissionId;
    private SubmissionStatus submissionStatus;
    private String error;
    private String code;
    private int totalTestCases;
    private int totalPassedTestCases;
    private double percentage;
}
