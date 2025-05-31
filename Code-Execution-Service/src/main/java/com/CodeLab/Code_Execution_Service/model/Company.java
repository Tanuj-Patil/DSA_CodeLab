package com.CodeLab.Code_Execution_Service.model;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Company {


    private UUID companyId;


    private String companyName;

}
