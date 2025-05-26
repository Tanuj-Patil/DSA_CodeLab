package com.CodeLab.Central_Service.requestDTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LocationRequestDTO {
    private String city;


    private String state;


    private String country;
}
