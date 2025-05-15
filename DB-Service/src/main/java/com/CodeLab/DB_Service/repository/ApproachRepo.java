package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Approach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApproachRepo extends JpaRepository<Approach, UUID> {
}
