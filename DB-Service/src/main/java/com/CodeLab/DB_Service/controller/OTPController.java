package com.CodeLab.DB_Service.controller;

import com.CodeLab.DB_Service.requestDTO.AdminRequestDTO;
import com.CodeLab.DB_Service.requestDTO.OTPGenerateRequestDTO;
import com.CodeLab.DB_Service.requestDTO.UserRequestDTO;
import com.CodeLab.DB_Service.responseDTO.OTPVerificationResponseDTO;
import com.CodeLab.DB_Service.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/db/otp")
public class OTPController {

    @Autowired
    OTPService otpService;


    @PostMapping("/generate")
    public void generateOTP(@RequestBody OTPGenerateRequestDTO requestDTO){
        otpService.generateOTP(requestDTO);
    }

    @PutMapping("/verify-user")
    public ResponseEntity<?> verifyUserOtp(@RequestBody UserRequestDTO requestDTO, @RequestParam String otp){
        OTPVerificationResponseDTO responseDTO = new OTPVerificationResponseDTO();

        if(otpService.verifyUserOTP(requestDTO,otp)){
            responseDTO.setValid(true);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @PutMapping("/verify-admin")
    public ResponseEntity<?> verifyAdminOtp(@RequestBody AdminRequestDTO requestDTO, @RequestParam String otp){
        OTPVerificationResponseDTO responseDTO = new OTPVerificationResponseDTO();

        if(otpService.verifyAdminOTP(requestDTO,otp)){
            responseDTO.setValid(true);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

}
