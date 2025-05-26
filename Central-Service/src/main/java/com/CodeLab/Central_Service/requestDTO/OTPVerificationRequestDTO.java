package com.CodeLab.Central_Service.requestDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OTPVerificationRequestDTO {
    private UUID userId;
    private String otp;
}
