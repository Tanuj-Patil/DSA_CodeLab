package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.Contest;
import com.CodeLab.Central_Service.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ContestRegistrationResponseDTO {
    private String message;
    private boolean isRegistered;
    private User user;
    private Contest contest;
}
