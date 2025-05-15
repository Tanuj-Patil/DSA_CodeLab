package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.ApproachType;
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
