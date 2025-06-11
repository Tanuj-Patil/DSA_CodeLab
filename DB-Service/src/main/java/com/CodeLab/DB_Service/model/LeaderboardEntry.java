package com.CodeLab.DB_Service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "leaderboard_entry")
public class LeaderboardEntry {

    @Id
    @GeneratedValue
    @Column(name = "leaderboard_entry_id")
    private UUID leaderboardEntryId;

    @ManyToOne
    @JoinColumn(name = "contest_id",updatable = false)
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rank;

    private double percentage;

    private long totalTimeTaken;

    private LocalDateTime createdAt = LocalDateTime.now();
}
