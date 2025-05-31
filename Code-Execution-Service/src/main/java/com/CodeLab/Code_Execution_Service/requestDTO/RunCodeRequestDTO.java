package com.CodeLab.Code_Execution_Service.requestDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RunCodeRequestDTO {
    private String code;
    private String language;
    private String versionIndex;
    private String input;
    private String expectedOutput;
    private UUID submissionId;
}
