package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.TestCase;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RunTimeErrorResponseDTO {
    String runtimeError;
    TestCase lastExecutedTestcase;
}
