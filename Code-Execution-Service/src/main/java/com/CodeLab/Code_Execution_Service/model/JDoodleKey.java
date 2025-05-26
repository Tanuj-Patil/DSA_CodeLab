package com.CodeLab.Code_Execution_Service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class JDoodleKey {
    private String clientId;
    private String clientSecret;
    private boolean exhausted = false;
}
