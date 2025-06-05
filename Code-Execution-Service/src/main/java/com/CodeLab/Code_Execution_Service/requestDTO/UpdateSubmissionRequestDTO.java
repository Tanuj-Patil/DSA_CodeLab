package com.CodeLab.Code_Execution_Service.requestDTO;

import com.CodeLab.Code_Execution_Service.enums.SubmissionStatus;
import com.CodeLab.Code_Execution_Service.model.TestCase;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UpdateSubmissionRequestDTO {
    private UUID submissionId;
    private SubmissionStatus submissionStatus;
    private String TC;
    private String SC;
    private String error;
    private String code;
    private  String lastInput;
    private String lastOutput;
    private String LastExpectedOutput;
    private int totalTestCases;
    private int totalPassedTestCases;
}
