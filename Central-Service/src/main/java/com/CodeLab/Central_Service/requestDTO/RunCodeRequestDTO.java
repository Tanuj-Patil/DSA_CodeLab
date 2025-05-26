package com.CodeLab.Central_Service.requestDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RunCodeRequestDTO {
    private String code;
    private String language;
    private String versionIndex;
    private String input;

}
