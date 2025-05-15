package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepo extends JpaRepository<Company, UUID> {
    public Optional<Company> findByCompanyName(String company);
}
