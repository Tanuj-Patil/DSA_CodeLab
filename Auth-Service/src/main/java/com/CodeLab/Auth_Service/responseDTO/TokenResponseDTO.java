package com.CodeLab.Auth_Service.responseDTO;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenResponseDTO {
    String token;
}
