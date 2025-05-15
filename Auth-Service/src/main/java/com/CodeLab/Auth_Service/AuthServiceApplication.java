package com.CodeLab.Auth_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@configuration
//@ComponentScan
//@EnableAutoConfiguration

public class AuthServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthServiceApplication.class, args);
		System.out.println("Auth-Service is Running:)");

	}

}
