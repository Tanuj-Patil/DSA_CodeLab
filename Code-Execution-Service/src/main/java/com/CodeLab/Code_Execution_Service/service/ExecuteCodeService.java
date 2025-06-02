package com.CodeLab.Code_Execution_Service.service;

import com.CodeLab.Code_Execution_Service.model.JDoodleRequest;
import com.CodeLab.Code_Execution_Service.model.JDoodleResponse;
import com.CodeLab.Code_Execution_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.SubmitCodeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ExecuteCodeService {

    @Autowired
    JDoodleService jDoodleService;

    @Autowired
    GeminiService geminiService;

    public RunCodeResponseDTO runCode(RunCodeRequestDTO requestDTO) {
        JDoodleRequest request = new JDoodleRequest();
        request.setScript(requestDTO.getCode());
        request.setLanguage(requestDTO.getLanguage());
        request.setVersionIndex(requestDTO.getVersionIndex());
        request.setStdin(requestDTO.getInput());

        JDoodleResponse response = jDoodleService.executeCode(request);

        RunCodeResponseDTO responseDTO = new RunCodeResponseDTO();
        responseDTO.setOutput(response.getOutput());
        responseDTO.setStatusCode(response.getStatusCode());
        responseDTO.setMemory(response.getMemory());
        responseDTO.setCpuTime(response.getCpuTime());

        boolean isError = this.detectCompileError(responseDTO.getOutput(), request.getLanguage());
        responseDTO.setError(isError);

        String runtimeError = detectRuntimeError(response.getOutput(), request.getLanguage());
        responseDTO.setRuntimeError(runtimeError);

        return responseDTO;
    }

    public boolean detectCompileError(String output, String language) {
        if (output == null || output.isBlank() || language == null) return false;

        String lowerOutput = output.toLowerCase();

        switch (language) {
            case "java":
                return lowerOutput.contains("exception") || lowerOutput.contains("error");
            case "cpp":
            case "c":
                return lowerOutput.contains("error") ||
                        lowerOutput.contains("undefined") ||
                        lowerOutput.contains("segmentation fault");
            case "nodejs":
                return lowerOutput.contains("referenceerror") ||
                        lowerOutput.contains("typeerror") ||
                        lowerOutput.contains("syntaxerror");
            case "python3":
                return lowerOutput.contains("traceback") ||
                        lowerOutput.contains("error") ||
                        lowerOutput.contains("exception");
            default:
                return lowerOutput.contains("error");
        }
    }

    public String detectRuntimeError(String output, String language) {
        if (output == null || output.isBlank() || language == null) return null;

        String lowerOutput = output.toLowerCase();

        switch (language) {
            case "java":
                if (lowerOutput.contains("exception") || lowerOutput.contains("runtime"))
                    return extractRelevantError(output);
                break;
            case "cpp":
            case "c":
                if (lowerOutput.contains("segmentation fault") || lowerOutput.contains("floating point exception"))
                    return extractRelevantError(output);
                break;
            case "python3":
                if (lowerOutput.contains("traceback") || lowerOutput.contains("error"))
                    return extractRelevantError(output);
                break;
            case "nodejs":
                if (lowerOutput.contains("typeerror") || lowerOutput.contains("referenceerror") || lowerOutput.contains("rangeerror"))
                    return extractRelevantError(output);
                break;
            default:
                if (lowerOutput.contains("exception") || lowerOutput.contains("runtime"))
                    return extractRelevantError(output);
        }
        return null;
    }

    private String extractRelevantError(String output) {
        String[] lines = output.split("\n");
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            String lower = line.toLowerCase();
            if (lower.contains("error") || lower.contains("exception") || lower.contains("traceback")) {
                builder.append(line).append("\n");
            }
        }
        return builder.toString().strip();
    }

    public SubmitCodeResponseDTO getTimeAndSpaceComplexities(String code) {
        SubmitCodeResponseDTO response = new SubmitCodeResponseDTO();
        Map<String, String> map = geminiService.analyzeCodeComplexity(code);
        response.setTC(map.getOrDefault("TC", "Unavailable"));
        response.setSC(map.getOrDefault("SC", "Unavailable"));
        return response;
    }

    @Async("jdoodleExecutor")
    public CompletableFuture<RunCodeResponseDTO> executeAsync(RunCodeRequestDTO requestDTO) {
        JDoodleRequest request = new JDoodleRequest();
        request.setScript(requestDTO.getCode());
        request.setLanguage(requestDTO.getLanguage());
        request.setVersionIndex(requestDTO.getVersionIndex());
        request.setStdin(requestDTO.getInput());

        JDoodleResponse response = jDoodleService.executeCode(request);

        RunCodeResponseDTO responseDTO = new RunCodeResponseDTO();
        responseDTO.setOutput(response.getOutput());
        responseDTO.setStatusCode(response.getStatusCode());
        responseDTO.setMemory(response.getMemory());
        responseDTO.setCpuTime(response.getCpuTime());

        boolean isError = detectCompileError(response.getOutput(), requestDTO.getLanguage());
        responseDTO.setError(isError);

        String runtimeError = detectRuntimeError(response.getOutput(), requestDTO.getLanguage());
        responseDTO.setRuntimeError(runtimeError);

        return CompletableFuture.completedFuture(responseDTO);
    }
}
