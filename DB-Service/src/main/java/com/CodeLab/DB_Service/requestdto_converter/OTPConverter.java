package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.OTP;
import com.CodeLab.DB_Service.requestDTO.OTPGenerateRequestDTO;

import java.time.LocalDateTime;

public class OTPConverter {
    public static OTP otpConverter(OTPGenerateRequestDTO requestDTO){
        OTP otp = new OTP();
        otp.setOtp(requestDTO.getOtp());
        otp.setEmail(requestDTO.getEmail());
        otp.setIsVerified(false);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // Example: 5 minute expiry

        return otp;
    }
}
