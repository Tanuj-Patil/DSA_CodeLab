package com.CodeLab.Code_Execution_Service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class JDoodleResponse {
    private String output;
    private String statusCode;
    private String memory;
    private String cpuTime;
    private String error;
}
