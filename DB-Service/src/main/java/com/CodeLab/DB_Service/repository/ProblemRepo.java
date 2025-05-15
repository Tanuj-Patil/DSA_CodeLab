package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemRepo extends JpaRepository<Problem, UUID> {
    @Query(value = "Select * from Problems where topic_list Like %:topicName% ",nativeQuery = true)
    public List<Problem> findByTopicName(@Param("topicName")String topicName);

    @Query(value = "Select COUNT(*) from Problems where topic_list LIKE %:topicName%",nativeQuery = true)
    public long countByTopicName(@Param("topicName")String topicName);

    @Query(value = "Select * from Problems where company_list Like %:companyName% ",nativeQuery = true)
    public List<Problem> findByCompanyName(@Param("companyName")String companyName);

    @Query(value = "Select COUNT(*) from Problems where company_list Like %:companyName% ",nativeQuery = true)
    public long countByCompanyName(@Param("companyName")String companyName);


//    @Query(value = "Select * from Problems where LOWER(CAST(problem_no AS TEXT)) like LOWER(%:keyword%) OR LOWER(problem_title) like LOWER(%:keyword%)" ,nativeQuery = true)
//    public List<Problem> problemSearch(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM Problems WHERE LOWER(CAST(problem_no AS TEXT)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(problem_title) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<Problem> problemSearch(@Param("keyword") String keyword);
}
