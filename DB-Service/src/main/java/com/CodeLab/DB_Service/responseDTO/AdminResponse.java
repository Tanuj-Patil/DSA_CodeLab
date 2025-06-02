package com.CodeLab.DB_Service.responseDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AdminResponse {
    UUID adminId;
    String email;
    String password;
}
