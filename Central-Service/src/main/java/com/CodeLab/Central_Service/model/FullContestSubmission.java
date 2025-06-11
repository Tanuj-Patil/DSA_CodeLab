package com.CodeLab.Central_Service.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class FullContestSubmission {
    private UUID submissionId;

    private Contest contest;

    private User user;

    private LocalDateTime userStartedAt;

    private LocalDateTime userSubmittedAt;

    private Long totalTimeTaken; //in seconds

    private Double percentage;
}
