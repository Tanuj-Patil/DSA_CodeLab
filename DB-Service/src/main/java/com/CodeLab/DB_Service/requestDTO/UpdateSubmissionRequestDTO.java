package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.SubmissionStatus;
import com.CodeLab.DB_Service.model.TestCase;
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
}
