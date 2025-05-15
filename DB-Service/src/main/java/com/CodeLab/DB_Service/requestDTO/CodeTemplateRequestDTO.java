package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.Language;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CodeTemplateRequestDTO {

    private String templateCode;

    private Language language;
}
