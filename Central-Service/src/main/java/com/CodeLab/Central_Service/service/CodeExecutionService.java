package com.CodeLab.Central_Service.service;

import com.CodeLab.Central_Service.exception.ErrorException;
import com.CodeLab.Central_Service.exception.WrongAnswerException;
import com.CodeLab.Central_Service.integration.DBService;
import com.CodeLab.Central_Service.integration.ExecutionService;
import com.CodeLab.Central_Service.model.Problem;
import com.CodeLab.Central_Service.model.TestCase;
import com.CodeLab.Central_Service.requestDTO.CodeRequestDTO;
import com.CodeLab.Central_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Central_Service.responseDTO.CentralServiceRunCodeResponse;
import com.CodeLab.Central_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Central_Service.responseDTO.SubmitCodeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

@Service
public class CodeExecutionService {

    @Autowired
    ExecutionService executionService;

    @Autowired
    DBService dbService;

    public List<CentralServiceRunCodeResponse> runCode(CodeRequestDTO requestDTO) throws RuntimeException{
        UUID problemId = requestDTO.getProblemId();
        final String baseCode;

        if(requestDTO.getLanguage().toString().equals("PYTHON")){
            baseCode =   requestDTO.getVisibleCode() + "\n"+requestDTO.getInvisibleCode();
        }
        else if(requestDTO.getLanguage().toString().equals("JAVA")){
            baseCode = requestDTO.getInvisibleCode() + "\n"+ requestDTO.getVisibleCode();
        }
        else if(requestDTO.getLanguage().toString().equals("CPP")){
            baseCode = requestDTO.getInvisibleCode() + "\n"+ requestDTO.getVisibleCode();
        }
        else if(requestDTO.getLanguage().toString().equals("C")){
            baseCode = requestDTO.getInvisibleCode() + "\n"+ requestDTO.getVisibleCode();
        }else{
            baseCode =   requestDTO.getVisibleCode() + "\n"+requestDTO.getInvisibleCode();
        }


        Problem problem = dbService.callGetProblem(problemId);
        List<TestCase> testCases = problem.getAllVisibleTestCases();

        String language = requestDTO.getLanguage().toString();
        String versionIndex = getVersionIndex(language);
        String finalLang = normalizeLanguage(language);

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(testCases.size(), 5));

        List<CompletableFuture<CentralServiceRunCodeResponse>> futures = testCases.stream()
                .map(testCase -> CompletableFuture.supplyAsync(() -> {
                    RunCodeRequestDTO runCodeRequestDTO = new RunCodeRequestDTO();
                    runCodeRequestDTO.setCode(baseCode);
                    runCodeRequestDTO.setLanguage(finalLang);
                    runCodeRequestDTO.setVersionIndex(versionIndex);
                    runCodeRequestDTO.setInput(testCase.getTestCaseInput());

                    RunCodeResponseDTO responseDTO = executionService.callRunCode(runCodeRequestDTO);

                    if (responseDTO.isError()) {
                        throw new ErrorException(responseDTO.getOutput());
                    }

                    CentralServiceRunCodeResponse response = new CentralServiceRunCodeResponse();
                    response.setInput(testCase.getTestCaseInput());
                    response.setOutput(responseDTO.getOutput());
                    response.setExpected(testCase.getTestCaseOutput());
                    response.setOutputMatched(response.getOutput().equals(response.getExpected()));

                    return response;
                }, executor)).collect(Collectors.toList());

        List<CentralServiceRunCodeResponse> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        executor.shutdown();
        return results;
    }

    public SubmitCodeResponseDTO submitCode(CodeRequestDTO requestDTO) {
        UUID problemId = requestDTO.getProblemId();
        Problem problem = dbService.callGetProblem(problemId);
        List<TestCase> testCases = problem.getTestCasesList();

        RunCodeRequestDTO runCodeRequestDTO = this.converter(requestDTO);

        for (TestCase t : testCases) {
            runCodeRequestDTO.setInput(t.getTestCaseInput());
            RunCodeResponseDTO responseDTO = executionService.callRunCode(runCodeRequestDTO);

            if (responseDTO.isError()) {
                throw new ErrorException(responseDTO.getOutput());
            }

            if (!responseDTO.getOutput().equals(t.getTestCaseOutput())) {
                throw new WrongAnswerException(responseDTO.getOutput() + " != " + t.getTestCaseOutput());
            }
        }

        return executionService.callSubmitCode(runCodeRequestDTO);
    }

    public RunCodeRequestDTO converter(CodeRequestDTO requestDTO) {
        RunCodeRequestDTO runCodeRequestDTO = new RunCodeRequestDTO();
        String finalCode = requestDTO.getInvisibleCode() + requestDTO.getVisibleCode();
        String language = requestDTO.getLanguage().toString();
        runCodeRequestDTO.setCode(finalCode);
        runCodeRequestDTO.setLanguage(normalizeLanguage(language));
        runCodeRequestDTO.setVersionIndex(getVersionIndex(language));
        return runCodeRequestDTO;
    }

    private String getVersionIndex(String language) {
        switch (language.toUpperCase()) {
            case "JAVA": return "4";
            case "CPP": return "5";
            case "JAVA_SCRIPT": return "3";
            case "C": return "5";
            default: return "4"; // Python or fallback
        }
    }

    private String normalizeLanguage(String language) {
        switch (language.toUpperCase()) {
            case "JAVA": return "java";
            case "CPP": return "cpp";
            case "JAVA_SCRIPT": return "nodejs";
            case "C": return "c";
            default: return "python3";
        }
    }
}
