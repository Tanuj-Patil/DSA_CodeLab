package com.CodeLab.Code_Execution_Service.requestDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RunCodeRequestDTO {
    private String invisibleCode;
    private String visibleCode;
    private String language;
    private String versionIndex;
    private String input;
    private String expectedOutput;
    private UUID submissionId;
    public RunCodeRequestDTO(RunCodeRequestDTO other) {
        this.visibleCode = other.getVisibleCode();
        this.invisibleCode = other.getInvisibleCode();
        this.language = other.language;
        this.versionIndex = other.versionIndex;
        this.submissionId = other.submissionId;
        // Intentionally omit input and expectedOutput to set them per test case
    }
}
