package com.CodeLab.Central_Service.service;

import com.CodeLab.Central_Service.integration.AuthService;
import com.CodeLab.Central_Service.requestDTO.LoginRequestDTO;
import com.CodeLab.Central_Service.responseDTO.LoginResponseDTO;
import com.CodeLab.Central_Service.responseDTO.TokenValidationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    public LoginResponseDTO generateToken(LoginRequestDTO requestDTO){
        requestDTO.setEmail(requestDTO.getEmail().trim());
//        String encodedPass = passwordEncoder.encode();
        requestDTO.setPassword(requestDTO.getPassword().trim());

        return authService.callGenerateToken(requestDTO);

    }

    public TokenValidationResponseDTO validateToken(String token){
        return authService.callValidateToken(token);
    }
}
