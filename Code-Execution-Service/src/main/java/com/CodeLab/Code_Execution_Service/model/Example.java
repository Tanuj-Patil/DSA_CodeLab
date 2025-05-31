package com.CodeLab.Code_Execution_Service.model;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Example {


    private UUID exampleId;


    private String exampleInput;


    private String exampleOutput;


    private String exampleExplanation;

    private Problem problem;
}
