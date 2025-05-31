package com.CodeLab.Central_Service.requestDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RunCodeRequestDTO {
    private String code;
    private String language;
    private String versionIndex;
    private String input;
    private String expectedOutput;
    private UUID submissionId;
    public RunCodeRequestDTO(RunCodeRequestDTO other) {
        this.code = other.code;
        this.language = other.language;
        this.versionIndex = other.versionIndex;
        this.submissionId = other.submissionId;
        // Intentionally omit input and expectedOutput to set them per test case
    }
}

