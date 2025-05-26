package com.CodeLab.Code_Execution_Service;

import com.CodeLab.Code_Execution_Service.configuration.JDoodleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(JDoodleConfig.class)
@EnableScheduling
public class CodeExecutionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeExecutionServiceApplication.class, args);
		System.out.println("Code-Execution-Service is Running:)");
	}

}
