package com.CodeLab.Central_Service.model;

import com.CodeLab.Central_Service.enums.Difficulty;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Problem {


    private UUID problemId;


    private int problemNo;


    private String problemTitle;


    private Difficulty problemDifficulty;


    private String topicList;


    private String companyList;


    private String problemDescription;


    private List<Example> exampleList;


    private String problemConstraints;

    private List<TestCase> testCasesList;

    private Solution solution;

    private List<CodeTemplate> codeTemplateList;

    private String note;

    public List<TestCase> getAllVisibleTestCases(){
        List<TestCase> testCases = new ArrayList<>();
        for(TestCase t : this.testCasesList){
            if(t.isVisible()){
                testCases.add(t);
            }
        }
        return testCases;
    }
}
