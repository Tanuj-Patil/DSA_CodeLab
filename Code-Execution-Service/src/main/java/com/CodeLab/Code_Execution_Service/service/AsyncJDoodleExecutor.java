package com.CodeLab.Code_Execution_Service.service;

import com.CodeLab.Code_Execution_Service.requestDTO.RunCodeRequestDTO;
import com.CodeLab.Code_Execution_Service.responseDTO.RunCodeResponseDTO;
import com.CodeLab.Code_Execution_Service.service.ExecuteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncJDoodleExecutor {

    @Autowired
    private ExecuteCodeService executeCodeService;

    @Async("jdoodleExecutor")
    public CompletableFuture<RunCodeResponseDTO> runAsync(RunCodeRequestDTO requestDTO) {
        return executeCodeService.executeAsync(requestDTO);
    }
}
