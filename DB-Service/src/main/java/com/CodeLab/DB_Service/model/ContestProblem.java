package com.CodeLab.DB_Service.model;

import com.CodeLab.DB_Service.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Contest_Problems")
public class ContestProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contest_problem_id",updatable = false)
    private UUID contestProblemId;

    @ManyToOne
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "contest_id",nullable = false)
    private Contest contest;

    @Column(name = "contest_question_no")
    private int contestQuestionNo;
}
