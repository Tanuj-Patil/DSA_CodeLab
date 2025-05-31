package com.CodeLab.Code_Execution_Service.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Solution {

    private UUID solutionId;

    private Problem problem;

    private List<Approach> approachList  = new ArrayList<>();
}
