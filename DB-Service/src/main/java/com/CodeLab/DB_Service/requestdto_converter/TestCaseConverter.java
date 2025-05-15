package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.model.TestCase;
import com.CodeLab.DB_Service.requestDTO.TestCaseRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class TestCaseConverter {
    public static List<TestCase> testCaseConverter(List<TestCaseRequestDTO> testCaseRequestDTOList, Problem problem){
        List<TestCase> testCaseList = new ArrayList<>();

        for (TestCaseRequestDTO testCaseRequestDTO : testCaseRequestDTOList){
            TestCase testCase = new TestCase();

            testCase.setTestCaseInput(testCaseRequestDTO.getTestCaseInput());
            testCase.setTestCaseOutput(testCaseRequestDTO.getTestCaseOutput());
            testCase.setVisible(testCaseRequestDTO.isVisible());
            testCase.setProblem(problem);

            testCaseList.add(testCase);
        }

        return testCaseList;
    }
}
