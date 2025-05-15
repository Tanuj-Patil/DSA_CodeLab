package com.CodeLab.DB_Service.model;

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
@Table(name = "Examples")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "example_id",updatable = false)
    private UUID exampleId;

    @Column(name = "example_input",nullable = false)
    private String exampleInput;

    @Column(name = "example_output",nullable = false)
    private String exampleOutput;

    @Column(name = "example_explanation",nullable = false,columnDefinition = "TEXT")
    private String exampleExplanation;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
