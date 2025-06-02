package com.CodeLab.DB_Service.model;

import com.CodeLab.DB_Service.enums.Language;
import com.CodeLab.DB_Service.enums.SubmissionStatus;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "submission_id",updatable = false)
    private UUID submissionId;

    @Column(name = "submission_status",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SubmissionStatus submissionStatus;

    @Column(name = "language_used",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Language language;

    @Column(name = "time_complexity")
    private String timeComplexity;

    @Column(name = "space_complexity")
    private String spaceComplexity;

    @Column(name = "error",columnDefinition = "TEXT")
    private String error;

    @Column(name = "code",columnDefinition = "TEXT")
    private String code;

    @Column(name = "submitted_at",nullable = false)
    @CreationTimestamp
    private LocalDateTime submittedAt;

//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;

    @Column(name = "last_testcase_input",columnDefinition = "TEXT")
    private  String lastInput;

    @Column(name = "last_testcase_output",columnDefinition = "TEXT")
    private String lastOutput;

    @Column(name = "last_testcase_expected_output",columnDefinition = "TEXT")
    private String lastExpectedOutput;

}
