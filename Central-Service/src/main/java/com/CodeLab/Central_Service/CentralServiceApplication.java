package com.CodeLab.Central_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CentralServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CentralServiceApplication.class, args);
		System.out.println("Central-Service is Running:)");
	}

}
