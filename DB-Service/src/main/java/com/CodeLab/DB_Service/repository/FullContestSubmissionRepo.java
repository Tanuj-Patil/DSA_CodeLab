package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.FullContestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FullContestSubmissionRepo extends JpaRepository<FullContestSubmission, UUID> {
    @Query(value = "SELECT * FROM full_contest_submissions WHERE contest_id = :contestId AND user_id = :userId", nativeQuery = true)
    Optional<FullContestSubmission> findByContestIdAndUserId(@Param("contestId") UUID contestId, @Param("userId") UUID userId);
}
