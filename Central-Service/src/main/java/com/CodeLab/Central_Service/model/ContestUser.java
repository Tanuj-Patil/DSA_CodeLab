package com.CodeLab.Central_Service.model;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ContestUser {

    private UUID contestUserId;


    private Contest contest;

    private User user;
}
