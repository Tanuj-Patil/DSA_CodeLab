package com.CodeLab.Central_Service.requestDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SolutionRequestDTO {
    private List<ApproachRequestDTO> approachRequestDTOList;
}
