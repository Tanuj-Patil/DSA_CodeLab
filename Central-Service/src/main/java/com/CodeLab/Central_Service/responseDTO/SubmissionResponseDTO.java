package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.Submission;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SubmissionResponseDTO {
    UUID submissionId;
    String message;
}
