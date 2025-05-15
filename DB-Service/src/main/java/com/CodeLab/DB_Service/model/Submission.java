package com.CodeLab.DB_Service.model;

import com.CodeLab.DB_Service.enums.Language;
import com.CodeLab.DB_Service.enums.SubmissionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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

    @Column(name = "time_complexity",nullable = false)
    private String timeComplexity;

    @Column(name = "space_complexity",nullable = false)
    private String spaceComplexity;

    @Column(name = "runtime_taken",nullable = false)
    private String runtime;

    @Column(name = "memory_taken",nullable = false)
    private String memory;


    @Column(name = "submitted_at",nullable = false)
    @CreationTimestamp
    private LocalDateTime submittedAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne()
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;



}
