package com.CodeLab.Auth_Service.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Pair{
    String credentials;
    UUID userId;

    public Pair(String c,UUID id){
        this.credentials = c;
        this.userId = id;
    }

}
