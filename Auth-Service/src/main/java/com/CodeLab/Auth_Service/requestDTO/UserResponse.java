package com.CodeLab.Auth_Service.requestDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserResponse {
    UUID userId;
    String email;
    String password;
}
