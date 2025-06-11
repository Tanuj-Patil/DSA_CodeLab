package com.CodeLab.DB_Service.responseDTO;

import com.CodeLab.DB_Service.model.Contest;
import com.CodeLab.DB_Service.model.ContestProblem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContestAddedResponseDTO {
    private Contest contest;
    List<ContestProblem> contestProblemList;
}
