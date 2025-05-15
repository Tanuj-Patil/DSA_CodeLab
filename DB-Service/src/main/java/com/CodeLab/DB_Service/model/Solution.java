package com.CodeLab.DB_Service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "solution_id",updatable = false)
    private UUID solutionId;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;

    @JsonManagedReference
    @OneToMany(mappedBy = "solution",cascade = CascadeType.ALL)
    private List<Approach> approachList;
}
