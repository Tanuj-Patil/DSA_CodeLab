package com.CodeLab.Central_Service.requestDTO;

import com.CodeLab.Central_Service.enums.ApproachType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ApproachRequestDTO {

    private ApproachType approachType;

    private String approachDescription;
}
