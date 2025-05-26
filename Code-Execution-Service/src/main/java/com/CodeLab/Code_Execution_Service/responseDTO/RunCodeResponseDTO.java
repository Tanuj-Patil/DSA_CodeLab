package com.CodeLab.Code_Execution_Service.responseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RunCodeResponseDTO {
    private String output;
    private String statusCode;
    private String memory;
    private String cpuTime;
    private boolean error;
}
