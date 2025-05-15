package com.CodeLab.DB_Service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id",nullable = false)
    private UUID locationId;

    @Column(name = "user_city",nullable = false)
    private String city;

    @Column(name = "user_state",nullable = false)
    private String state;

    @Column(name = "user_country",nullable = false)
    private String country;
}
