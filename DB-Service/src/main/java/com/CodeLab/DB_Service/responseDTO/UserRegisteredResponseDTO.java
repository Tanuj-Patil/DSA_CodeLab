package com.CodeLab.DB_Service.responseDTO;

import com.CodeLab.DB_Service.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserRegisteredResponseDTO {
    String message;
    User user;
}
