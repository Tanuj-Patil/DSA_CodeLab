package com.CodeLab.DB_Service.model;

import com.CodeLab.DB_Service.enums.ApproachType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Approaches")
public class Approach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "approach_id",updatable = false)
    private UUID approachId;

    @Column(name = "approach_type",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ApproachType approachType;

    @Column(name = "approach_description",nullable = false,columnDefinition = "TEXT")
    private String approachDescription;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "solution_id",nullable = false)
    private Solution solution;
}
