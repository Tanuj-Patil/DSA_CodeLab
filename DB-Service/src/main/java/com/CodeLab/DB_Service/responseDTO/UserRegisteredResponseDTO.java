package com.CodeLab.DB_Service.responseDTO;

import com.CodeLab.DB_Service.model.User;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserRegisteredResponseDTO {
    UUID userId;
}
