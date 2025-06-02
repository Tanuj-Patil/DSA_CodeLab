package com.CodeLab.Notification_Service.requestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OTPGenerateRequestDTO {
    private String otp;
    private String email;

}
