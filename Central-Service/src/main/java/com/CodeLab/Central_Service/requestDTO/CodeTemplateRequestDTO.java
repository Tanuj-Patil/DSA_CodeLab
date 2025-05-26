package com.CodeLab.Central_Service.requestDTO;

import com.CodeLab.Central_Service.enums.Language;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CodeTemplateRequestDTO {

    private String visibleTemplateCode;

    private String invisibleTemplateCode;

    private Language language;
}
