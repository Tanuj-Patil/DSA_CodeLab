package com.CodeLab.DB_Service.model;

import com.CodeLab.DB_Service.enums.Language;
import com.CodeLab.DB_Service.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "partial_contest_submissions")
public class PartialContestSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "submission_id",updatable = false)
    private UUID submissionId;

    @ManyToOne
    @JoinColumn(name = "contest_id",nullable = false)
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;

    @Column(name = "user_submitted_at")
    private LocalDateTime userSubmittedAt;


    @Column(name = "total_testcases")
    private int totalTestCases;

    @Column(name = "total_passed_testcases")
    private int totalPassedTestCases;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "error",columnDefinition = "TEXT")
    private String error;

    @Column(name = "code",columnDefinition = "TEXT")
    private String code;

    @Column(name = "submission_status",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SubmissionStatus submissionStatus;

    @Column(name = "language_used",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Language language;

}
