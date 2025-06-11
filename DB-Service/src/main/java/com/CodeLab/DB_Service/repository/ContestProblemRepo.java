package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.ContestProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContestProblemRepo extends JpaRepository<ContestProblem, UUID> {
    @Query(value = "SELECT * FROM contest_problems WHERE contest_id = :contestId", nativeQuery = true)
    List<ContestProblem> findByContestIdNative(@Param("contestId") UUID contestId);
}
