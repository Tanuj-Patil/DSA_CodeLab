package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ContestRepo extends JpaRepository<Contest,UUID> {
    @Query(value = "SELECT * FROM contests WHERE start_time > NOW() ORDER BY start_time ASC", nativeQuery = true)
    List<Contest> findAllUpcomingContests();

    @Query(
            value = "SELECT * FROM contests WHERE start_time > NOW() ORDER BY start_time ASC",
            countQuery = "SELECT COUNT(*) FROM contests WHERE start_time > NOW()",
            nativeQuery = true
    )
    Page<Contest> findAllUpcomingContests(Pageable pageable);

    @Query(value = "SELECT * FROM contests WHERE start_time <= :now AND end_time > :now", nativeQuery = true)
    List<Contest> findAllLiveContests(@Param("now") LocalDateTime now);

    @Query(
            value = "SELECT * FROM contests WHERE start_time <= :now AND end_time > :now",
            countQuery = "SELECT COUNT(*) FROM contests WHERE start_time <= :now AND end_time > :now",
            nativeQuery = true
    )
    Page<Contest> findAllLiveContestsPaginated(@Param("now") LocalDateTime now, Pageable pageable);

}
