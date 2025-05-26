package com.CodeLab.Central_Service.requestDTO;

import com.CodeLab.Central_Service.enums.Language;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CodeRequestDTO {

    private UUID problemId;
    private Language language;
    private String visibleCode;
    private String invisibleCode;

}
