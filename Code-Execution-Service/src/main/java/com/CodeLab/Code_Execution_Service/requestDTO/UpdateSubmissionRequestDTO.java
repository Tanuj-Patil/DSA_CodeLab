package com.CodeLab.Code_Execution_Service.requestDTO;

import com.CodeLab.Code_Execution_Service.enums.SubmissionStatus;
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
    private String wrongAnswer;
}
