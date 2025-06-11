package com.CodeLab.Central_Service.model;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ContestProblem {
    private UUID contestProblemId;

    private Problem problem;

    private Contest contest;

    private int contestQuestionNo;
}
