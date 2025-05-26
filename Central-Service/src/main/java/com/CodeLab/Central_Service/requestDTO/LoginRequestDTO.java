package com.CodeLab.Central_Service.requestDTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoginRequestDTO {
    private String email;
    private String password;
}
