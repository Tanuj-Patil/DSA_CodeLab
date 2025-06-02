package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Admin;
import com.CodeLab.DB_Service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepo extends JpaRepository<Admin, UUID> {
    Optional<Admin> getByEmail(String email);
}
