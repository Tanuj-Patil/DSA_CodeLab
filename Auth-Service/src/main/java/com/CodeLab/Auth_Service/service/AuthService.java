package com.CodeLab.Auth_Service.service;

import com.CodeLab.Auth_Service.integration.DBService;
import com.CodeLab.Auth_Service.requestDTO.UserResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {
    @Autowired
    DBService dbService;

    @Value("${auth.secret.key}")
    String secretKey;

    Long expirationTime = 604800000L;  //7 days

    public String generateToken(String email,String password){
        String credentials = email + ":" + password;

        String jwtToken = Jwts.builder().setSubject(credentials)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        log.info(jwtToken);
        return jwtToken;
    }

    public String decryptToken(String token){
        String credentials = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        log.info(credentials);
        return credentials;
    }

    public String validateToken(String token){
        String credentials = decryptToken(token);

        String email = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        UserResponse response = dbService.callGetUserByEmail(email);

        if(response == null){
            return null;
        }
        return (response.getPassword().equals(password)) ? credentials : null;

    }



}
