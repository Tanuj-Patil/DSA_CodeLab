package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.PartialContestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PartialContestSubmissionRepo extends JpaRepository<PartialContestSubmission, UUID> {
    @Query(
            value = "SELECT * FROM partial_contest_submissions " +
                    "WHERE contest_id = :contestId " +
                    "AND user_id = :userId " +
                    "AND problem_id = :problemId",
            nativeQuery = true
    )
    List<PartialContestSubmission> findByContestIdAndUserIdAndProblemId(
            @Param("contestId") UUID contestId,
            @Param("userId") UUID userId,
            @Param("problemId") UUID problemId
    );
}
