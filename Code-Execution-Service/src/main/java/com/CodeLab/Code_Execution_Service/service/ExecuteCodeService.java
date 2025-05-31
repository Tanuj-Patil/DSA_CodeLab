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
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;



import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ExecuteCodeService {
    @Autowired
    JDoodleService jDoodleService;

    @Autowired
    GeminiService geminiService;



    public RunCodeResponseDTO runCode(RunCodeRequestDTO requestDTO){
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
        boolean isError = this.detectError(responseDTO.getOutput(),request.getLanguage());
        responseDTO.setError(isError);

//        log.info(responseDTO.getCpuTime());

        return responseDTO;
    }
    public boolean detectError(String output, String language) {
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

    public SubmitCodeResponseDTO getTimeAndSpaceComplexities(String code){
        SubmitCodeResponseDTO response = new SubmitCodeResponseDTO();


        Map<String,String> map = geminiService.analyzeCodeComplexity(code);
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
        boolean isError = detectError(response.getOutput(), requestDTO.getLanguage());
        responseDTO.setError(isError);

        return CompletableFuture.completedFuture(responseDTO);
    }

}
