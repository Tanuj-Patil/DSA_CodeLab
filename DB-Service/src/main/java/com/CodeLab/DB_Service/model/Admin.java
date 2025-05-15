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
@Table(name = "Admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_id",updatable = false)
    private UUID adminId;

    @Column(name = "admin_name",nullable = false)
    private String name;

    @Column(name = "admin_email",nullable = false,unique = true,updatable = false)
    private String email;

    @Column(name = "admin_password",nullable = false)
    private String password;
}
