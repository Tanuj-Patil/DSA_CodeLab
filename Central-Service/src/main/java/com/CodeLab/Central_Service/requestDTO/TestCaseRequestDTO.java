package com.CodeLab.Central_Service.requestDTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TestCaseRequestDTO {

    private String testCaseInput;

    private String testCaseOutput;

    private boolean visible;

}
