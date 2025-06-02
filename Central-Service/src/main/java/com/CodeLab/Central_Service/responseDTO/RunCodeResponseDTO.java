package com.CodeLab.Central_Service.responseDTO;

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
    private String runtimeError;

    public boolean hasRuntimeError() {
        return runtimeError != null && !runtimeError.isBlank();
    }
}
