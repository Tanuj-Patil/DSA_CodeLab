package com.CodeLab.Auth_Service.controller;

import com.CodeLab.Auth_Service.responseDTO.TokenResponseDTO;
import com.CodeLab.Auth_Service.responseDTO.TokenValidationResponseDTO;
import com.CodeLab.Auth_Service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/token")
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping("/generate")
    public ResponseEntity<?> generateToken(@RequestParam String email, @RequestParam String password){

        String jwtToken = authService.generateToken(email,password);

        TokenResponseDTO responseDTO = new TokenResponseDTO(jwtToken);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(){
        TokenValidationResponseDTO responseDTO = new TokenValidationResponseDTO();
        responseDTO.setValid(true);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


}
