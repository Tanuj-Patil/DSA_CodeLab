package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubmissionRepo extends JpaRepository<Submission, UUID> {

    @Query(value = "SELECT * FROM submissions WHERE user_id = :userId ORDER BY submitted_at ASC", nativeQuery = true)
    List<Submission> findAllByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM submissions WHERE problem_id = :problemId ORDER BY submitted_at ASC", nativeQuery = true)
    List<Submission> findAllByProblemId(@Param("problemId") UUID problemId);

    @Query(value = "SELECT * FROM submissions WHERE user_id = :userId AND problem_id = :problemId ORDER BY submitted_at ASC", nativeQuery = true)
    List<Submission> findAllByUserAndProblem(@Param("userId") UUID userId, @Param("problemId") UUID problemId);

    @Query(value = "SELECT * FROM submissions WHERE user_id = :userId ORDER BY submitted_at DESC LIMIT 1", nativeQuery = true)
    Optional<Submission> findLatestSubmissionByUserId(@Param("userId") UUID userId);
}
