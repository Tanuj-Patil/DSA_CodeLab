package com.CodeLab.Central_Service.model;

import com.CodeLab.Central_Service.responseDTO.CentralServiceRunCodeResponse;
import com.CodeLab.Central_Service.responseDTO.RunTimeErrorResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CodeExecutionResult {
    private boolean success;
    private List<CentralServiceRunCodeResponse> responses;
    private RunTimeErrorResponseDTO runtimeError;
    private String errorMessage;
}
