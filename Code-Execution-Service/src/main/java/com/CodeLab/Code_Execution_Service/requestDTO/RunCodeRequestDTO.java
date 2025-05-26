package com.CodeLab.Code_Execution_Service.requestDTO;

import lombok.*;

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
}
