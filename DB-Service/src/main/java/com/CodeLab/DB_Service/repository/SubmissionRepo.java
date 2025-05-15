package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepo extends JpaRepository<Submission, UUID> {
    @Query(value = "Select * from submissions where user_Id = :userId",nativeQuery = true)
    List<Submission> findAllByUser(@Param("userId") UUID userId);

    @Query(value = "Select * from submissions where user_Id = :userId AND problem_id = :problemId",nativeQuery = true)
    List<Submission> findAllByUserAndProblem(@Param("userId") UUID userId,@Param("problemId")UUID problemId);
}
