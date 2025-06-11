package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.Contest;
import com.CodeLab.Central_Service.model.ContestProblem;
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
