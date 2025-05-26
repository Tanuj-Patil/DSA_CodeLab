package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.User;
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
