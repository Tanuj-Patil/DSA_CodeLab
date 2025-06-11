package com.CodeLab.DB_Service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "contest_user")
public class ContestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",updatable = false)
    private UUID contestUserId;

    @ManyToOne
    @JoinColumn(name = "contest_id",nullable = false)
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
