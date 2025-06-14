package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.Difficulty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProblemRequestDTO {

    private String problemTitle;//

    private Difficulty problemDifficulty;//

    private String problemDescription;//

    private List<ExampleRequestDTO> exampleRequestDTOList;//

    private String problemConstraints;//


    private String topicList;//


    private String companyList;

    private List<TestCaseRequestDTO> testCaseRequestDTOList;

    private SolutionRequestDTO solutionRequestDTO;

    private List<CodeTemplateRequestDTO> codeTemplateRequestDTOList;

    private String note;
}
