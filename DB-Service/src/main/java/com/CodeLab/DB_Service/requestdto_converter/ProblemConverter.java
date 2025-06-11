package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.*;
import com.CodeLab.DB_Service.requestDTO.*;

import java.util.ArrayList;
import java.util.List;

public class ProblemConverter {

    public static Problem problemConverter(ProblemRequestDTO problemRequestDTO,boolean isVisible) {

        Problem problem = new Problem();

        problem.setProblemTitle(problemRequestDTO.getProblemTitle());
        problem.setProblemDifficulty(problemRequestDTO.getProblemDifficulty());
        problem.setProblemDescription(problemRequestDTO.getProblemDescription());
        problem.setProblemConstraints(problemRequestDTO.getProblemConstraints());
        problem.setNote(problemRequestDTO.getNote());
        problem.setTopicList(problemRequestDTO.getTopicList());
        problem.setCompanyList(problemRequestDTO.getCompanyList());
        problem.setVisible(isVisible);

        List<Example> exampleList = ExampleConverter.exampleConverter(problemRequestDTO.getExampleRequestDTOList(), problem);
        problem.setExampleList(exampleList);

        List<TestCase> testCaseList = TestCaseConverter.testCaseConverter(problemRequestDTO.getTestCaseRequestDTOList(), problem);
        problem.setTestCasesList(testCaseList);

        Solution solution = SolutionConverter.solutionConverter(problemRequestDTO.getSolutionRequestDTO(), problem);
        problem.setSolution(solution);

        List<CodeTemplate> codeTemplateList = CodeTemplateConverter.codeTemplateConverter(problemRequestDTO.getCodeTemplateRequestDTOList(), problem);
        problem.setCodeTemplateList(codeTemplateList);

        return problem;
    }
}
