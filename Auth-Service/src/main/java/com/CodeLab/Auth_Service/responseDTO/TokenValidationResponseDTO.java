package com.CodeLab.Auth_Service.responseDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TokenValidationResponseDTO {
    UUID userId;
    boolean isValid;
    String message;
}
