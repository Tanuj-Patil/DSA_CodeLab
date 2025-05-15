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
@Table(name = "TestCases")
public class TestCase{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "testcase_id",updatable = false)
    private UUID testCaseId;

    @Column(name = "testcase_input",nullable = false,columnDefinition = "TEXT")
    private String testCaseInput;

    @Column(name = "testcase_output",nullable = false,columnDefinition = "TEXT")
    private String testCaseOutput;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;

    @Column(name = "testcase_visibility",nullable = false)
    private boolean isVisible;
}
