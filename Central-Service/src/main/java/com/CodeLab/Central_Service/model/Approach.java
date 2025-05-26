package com.CodeLab.Central_Service.model;

import com.CodeLab.Central_Service.enums.ApproachType;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Approach {

    private UUID approachId;


    private ApproachType approachType;


    private String approachDescription;

    private Solution solution;
}
