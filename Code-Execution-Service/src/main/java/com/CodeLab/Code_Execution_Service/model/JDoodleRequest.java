package com.CodeLab.Code_Execution_Service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class JDoodleRequest {
    private String clientId;
    private String clientSecret;

    private String script;
    private String language;
    private String versionIndex;
    private String stdin; // Optional
}
