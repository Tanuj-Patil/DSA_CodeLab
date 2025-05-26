package com.CodeLab.Central_Service.model;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class TestCase{


    private UUID testCaseId;


    private String testCaseInput;


    private String testCaseOutput;


    private Problem problem;


    private boolean isVisible;
}
