package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.Problem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProblemAddedResponseDTO {
    private  String message;
    private Problem problem;
}
