package com.CodeLab.Auth_Service.requestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserResponse {
    String email;
    String password;
}
