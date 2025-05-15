package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Example;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.requestDTO.ExampleRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class ExampleConverter {
    public static List<Example> exampleConverter(List<ExampleRequestDTO> exampleRequestDTOList, Problem problem){
        List<Example> exampleList = new ArrayList<>();

        for(ExampleRequestDTO exampleRequestDTO : exampleRequestDTOList){
            Example example = new Example();

            example.setExampleInput(exampleRequestDTO.getExampleInput());
            example.setExampleOutput(exampleRequestDTO.getExampleOutput());
            example.setExampleExplanation(exampleRequestDTO.getExampleExplanation());
            example.setProblem(problem);

            exampleList.add(example);
        }

        return exampleList;

    }
}
