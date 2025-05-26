package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.OTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OTPRepo extends JpaRepository<OTP, UUID> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM otps WHERE expiry_time < :now", nativeQuery = true)
    void deleteByExpiryTimeBefore(@Param("now") LocalDateTime now);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM otps WHERE verified = :verified", nativeQuery = true)
    void deleteVerifiedOTPs(@Param("verified") boolean verified);

    Optional<OTP> findByEmailAndOtpAndIsVerifiedFalse(String email, String otp);

    void deleteByEmail(String email);
}
