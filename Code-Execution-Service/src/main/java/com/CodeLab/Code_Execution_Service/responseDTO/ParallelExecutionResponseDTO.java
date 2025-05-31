package com.CodeLab.Code_Execution_Service.responseDTO;


import lombok.Data;

import java.util.List;

@Data
public class ParallelExecutionResponseDTO {
    private List<RunCodeResponseDTO> results;
    private double totalTimeInSeconds;
}
