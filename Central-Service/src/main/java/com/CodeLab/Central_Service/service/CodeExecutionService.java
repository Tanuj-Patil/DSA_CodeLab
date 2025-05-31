package com.CodeLab.Central_Service.service;

import com.CodeLab.Central_Service.exception.ErrorException;
import com.CodeLab.Central_Service.integration.DBService;
import com.CodeLab.Central_Service.integration.ExecutionService;
import com.CodeLab.Central_Service.integration.RabbitMQIntegration;
import com.CodeLab.Central_Service.model.Problem;
import com.CodeLab.Central_Service.model.Submission;
import com.CodeLab.Central_Service.model.TestCase;
import com.CodeLab.Central_Service.requestDTO.CodeRequestDTO;
import com.CodeLab.Central_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Central_Service.requestDTO.SubmissionRequestDTO;
import com.CodeLab.Central_Service.responseDTO.CentralServiceRunCodeResponse;
import com.CodeLab.Central_Service.responseDTO.RunCodeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class CodeExecutionService {

    @Autowired
    ExecutionService executionService;

    @Autowired
    DBService dbService;


    @Autowired
    RabbitMQIntegration rabbitMQIntegration;

    //Synchronous
//    public List<CentralServiceRunCodeResponse> runCode(CodeRequestDTO requestDTO) throws ErrorException {
//        UUID problemId = requestDTO.getProblemId();
//
//        String language = requestDTO.getLanguage().toString();
//        String baseCode = (language.equals("PYTHON") || language.equals("JAVA_SCRIPT"))
//                ? requestDTO.getVisibleCode() + "\n" + requestDTO.getInvisibleCode()
//                : requestDTO.getInvisibleCode() + "\n" + requestDTO.getVisibleCode();
//
//        Problem problem = dbService.callGetProblem(problemId);
//        if (problem == null) {
//            throw new ErrorException("Problem Not Found!!!");
//        }
//
//        List<TestCase> testCases = problem.getAllVisibleTestCases();
//        if (testCases == null || testCases.isEmpty()) {
//            throw new ErrorException("No visible test cases found for the problem.");
//        }
//
//        RunCodeRequestDTO runCodeRequestDTO = new RunCodeRequestDTO();
//        runCodeRequestDTO.setCode(baseCode);
//        runCodeRequestDTO.setLanguage(normalizeLanguage(language));
//        runCodeRequestDTO.setVersionIndex(getVersionIndex(language));
//
//        List<CentralServiceRunCodeResponse> responseList = new ArrayList<>();
//
//        for (TestCase t : testCases) {
//            runCodeRequestDTO.setInput(t.getTestCaseInput());
//
//            RunCodeResponseDTO responseDTO = executionService.callRunCode(runCodeRequestDTO);
//
//            if (responseDTO.isError()) {
//                throw new ErrorException(responseDTO.getOutput());
//            }
//
//            CentralServiceRunCodeResponse response = new CentralServiceRunCodeResponse();
//            response.setInput(t.getTestCaseInput());
//            response.setOutput(responseDTO.getOutput());
//            response.setExpected(t.getTestCaseOutput());
//            response.setOutputMatched(responseDTO.getOutput().equals(t.getTestCaseOutput()));
//
//            responseList.add(response);
//        }
//
//        return responseList;
//    }

    //Asynchronous
    public List<CentralServiceRunCodeResponse> runCode(CodeRequestDTO requestDTO) {
        UUID problemId = requestDTO.getProblemId();
        String language = requestDTO.getLanguage().toString();

        // Determine code order
        Set<String> visibleFirstLanguages = Set.of("PYTHON", "JAVA_SCRIPT"); // Extendable
        String baseCode = visibleFirstLanguages.contains(language)
                ? requestDTO.getVisibleCode() + "\n" + requestDTO.getInvisibleCode()
                : requestDTO.getInvisibleCode() + "\n" + requestDTO.getVisibleCode();

        // Fetch problem and test cases
        Problem problem = dbService.callGetProblem(problemId);
        if (problem == null) {
            throw new ErrorException("Problem Not Found!!!");
        }

        List<TestCase> testCases = problem.getAllVisibleTestCases();
        if (testCases == null || testCases.isEmpty()) {
            throw new ErrorException("No visible test cases found for the problem.");
        }

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
                    runCodeRequestDTO.setExpectedOutput(testCase.getTestCaseOutput());

                    RunCodeResponseDTO responseDTO = executionService.callRunCode(runCodeRequestDTO);
                    if (responseDTO.isError()) {
                        throw new ErrorException(responseDTO.getOutput());
                    }

                    CentralServiceRunCodeResponse response = new CentralServiceRunCodeResponse();
                    response.setInput(testCase.getTestCaseInput());
                    response.setOutput(responseDTO.getOutput());
                    response.setExpected(testCase.getTestCaseOutput());
                    response.setOutputMatched(responseDTO.getOutput().equals(testCase.getTestCaseOutput()));

                    return response;
                }, executor))
                .collect(Collectors.toList());

        List<CentralServiceRunCodeResponse> results = futures.stream()
                .map(future -> {
                    try {
                        return future.join();
                    } catch (CompletionException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof ErrorException) {
                            CentralServiceRunCodeResponse errorResponse = new CentralServiceRunCodeResponse();
//                            errorResponse.setInput("Error during execution");
//                            errorResponse.setOutput(cause.getMessage());
//                            errorResponse.setExpected("");
//                            errorResponse.setOutputMatched(false);
                            throw new ErrorException(cause.getMessage());
//                            return errorResponse;
                        }
                        throw e;
                    }
                })
                .collect(Collectors.toList());

        executor.shutdown();
        return results;
    }




    public Submission submitCode(CodeRequestDTO requestDTO, UUID userId) {
        UUID problemId = requestDTO.getProblemId();

        // Fetch problem and validate
        Problem problem = dbService.callGetProblem(problemId);
        if (problem == null) {
            throw new ErrorException("Problem Not Found!");
        }


//        List<TestCase> testCases = problem.getTestCasesList();
        List<TestCase> testCases = problem.getAllVisibleTestCases();


        if (testCases == null || testCases.isEmpty()) {
            throw new ErrorException("No test cases available for submission.");
        }

        // Create submission
        Submission submission = dbService.callAddSubmission(
                new SubmissionRequestDTO(requestDTO.getLanguage(),userId,problemId)
        );

        // Precompute shared fields
        RunCodeRequestDTO baseRequest = convertToRunCodeRequest(requestDTO);
        baseRequest.setSubmissionId(submission.getSubmissionId());

        // Prepare execution requests
        List<RunCodeRequestDTO> executionRequests = testCases.stream()
                .map(testCase -> {
                    RunCodeRequestDTO request = new RunCodeRequestDTO(baseRequest);
                    request.setInput(testCase.getTestCaseInput());
                    request.setExpectedOutput(testCase.getTestCaseOutput());
                    return request;
                })
                .collect(Collectors.toList());

        // Send to execution queue
        rabbitMQIntegration.sendCodeExecutionRequest(executionRequests);
        return submission;
    }

    public RunCodeRequestDTO convertToRunCodeRequest(CodeRequestDTO requestDTO) {
        String finalCode = requestDTO.getInvisibleCode() + requestDTO.getVisibleCode();
        String language = requestDTO.getLanguage().toString();

        RunCodeRequestDTO runCodeRequestDTO = new RunCodeRequestDTO();
        runCodeRequestDTO.setCode(finalCode);
        runCodeRequestDTO.setLanguage(normalizeLanguage(language));
        runCodeRequestDTO.setVersionIndex(getVersionIndex(language));
        return runCodeRequestDTO;
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
