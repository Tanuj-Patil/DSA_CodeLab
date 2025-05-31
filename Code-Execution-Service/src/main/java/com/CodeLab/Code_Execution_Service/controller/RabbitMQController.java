package com.CodeLab.Code_Execution_Service.controller;

import com.CodeLab.Code_Execution_Service.enums.SubmissionStatus;
import com.CodeLab.Code_Execution_Service.integration.DBService;
import com.CodeLab.Code_Execution_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Code_Execution_Service.requestDTO.UpdateSubmissionRequestDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Code_Execution_Service.service.DetectError;
import com.CodeLab.Code_Execution_Service.service.ExecuteCodeService;
import com.CodeLab.Code_Execution_Service.service.GeminiService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RabbitMQController {

    @Autowired
    ExecuteCodeService executeCodeService;

    @Autowired
    DetectError detectError;

    @Autowired
    private DBService dbService;

    @Autowired
    GeminiService geminiService;

    @RabbitListener(queues = "codelab-code-execution-queue")
    public void consumeMessage(@Payload List<RunCodeRequestDTO> requestList) {
        try {
            long totalStart = System.nanoTime(); // Start total timer

            // Step 1: Test Case Execution
            long execStart = System.nanoTime();
            List<CompletableFuture<RunCodeResponseDTO>> futures = requestList.stream()
                    .map(executeCodeService::executeAsync)
                    .collect(Collectors.toList());

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            List<RunCodeResponseDTO> results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            long execEnd = System.nanoTime();
            double execTimeInSec = (execEnd - execStart) / 1_000_000_000.0;

            // Step 2: Result Processing
            for (int i = 0; i < results.size(); i++) {
                RunCodeResponseDTO result = results.get(i);
                RunCodeRequestDTO request = requestList.get(i);

                if (result.isError()) {
                    dbService.callUpdateSubmission(new UpdateSubmissionRequestDTO(
                            request.getSubmissionId(), SubmissionStatus.COMPILE_ERROR, null, null, result.getOutput(), null
                    ));
                    System.out.println("Compile Error");
                    System.out.println("Output: " + result.getOutput());
                    return;
                }

                if (!detectError.isOutputMatched(result, request)) {
                    String wrong = "Input:\n" + request.getInput() +
                            "\nYour Output:\n" + result.getOutput() +
                            "\nExpected Output:\n" + request.getExpectedOutput();

                    dbService.callUpdateSubmission(new UpdateSubmissionRequestDTO(
                            request.getSubmissionId(), SubmissionStatus.WRONG_ANSWER, null, null, null, wrong
                    ));
                    System.out.println("Wrong Answer");
                    System.out.println(wrong);
                    return;
                }

                System.out.println("Test Case " + (i + 1));
                System.out.println("Output: " + result.getOutput());
                System.out.println("CPU Time: " + result.getCpuTime());
                System.out.println("-------------------------");
            }

            System.out.printf("Executed %d test cases in %.3f seconds%n", results.size(), execTimeInSec);

            // Step 3: Analyze TC and SC
            long analysisStart = System.nanoTime();
            Map<String, String> map = geminiService.analyzeCodeComplexity(requestList.get(0).getCode());
            long analysisEnd = System.nanoTime();
            double analysisTimeInSec = (analysisEnd - analysisStart) / 1_000_000_000.0;

            System.out.println("TC: " + map.get("TC"));
            System.out.println("SC: " + map.get("SC"));
            System.out.printf("Time taken for complexity analysis: %.3f seconds%n", analysisTimeInSec);

            // Step 4: Final Update
            dbService.callUpdateSubmission(new UpdateSubmissionRequestDTO(
                    requestList.get(0).getSubmissionId(), SubmissionStatus.ACCEPTED, map.get("TC"), map.get("SC"), null, null
            ));

            long totalEnd = System.nanoTime();
            double totalTimeInSec = (totalEnd - totalStart) / 1_000_000_000.0;
            System.out.printf("Total time taken (execution + analysis): %.3f seconds%n", totalTimeInSec);

        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
        }
    }

}

