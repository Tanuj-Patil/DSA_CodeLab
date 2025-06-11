package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaderboardEntryRepo extends JpaRepository<LeaderboardEntry, UUID> {

    @Query(value = """
        SELECT * 
        FROM leaderboard_entry 
        WHERE contest_id = :contestId
        ORDER BY rank ASC
        """, nativeQuery = true)
    List<LeaderboardEntry> findByContestIdNative(@Param("contestId") UUID contestId);

    // Optional: Non-native alternative if you want to use JPQL
    // List<LeaderboardEntry> findByContest_ContestIdOrderByRankAsc(UUID contestId);
}

