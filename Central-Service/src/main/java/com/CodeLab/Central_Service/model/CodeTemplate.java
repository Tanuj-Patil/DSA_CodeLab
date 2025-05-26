package com.CodeLab.Central_Service.model;
import com.CodeLab.Central_Service.enums.Language;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class CodeTemplate {

    private UUID codeTemplateId;


    private String visibleTemplateCode;

    private String invisibleTemplateCode;


    private Language language;

    private Problem problem;
}
