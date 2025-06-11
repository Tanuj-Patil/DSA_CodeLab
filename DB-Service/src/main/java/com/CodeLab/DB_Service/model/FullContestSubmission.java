package com.CodeLab.DB_Service.model;

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
@Table(name = "full_contest_submissions")
public class FullContestSubmission {
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

    @Column(name = "user_started_at")
    @CreationTimestamp
    private LocalDateTime userStartedAt;

    @Column(name = "user_submitted_at")
    private LocalDateTime userSubmittedAt;

    @Column(name = "total_time_taken")
    private Long totalTimeTaken; //in seconds

    @Column(name = "percentage")
    private Double percentage;
}
