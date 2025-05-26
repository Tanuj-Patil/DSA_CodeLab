package com.CodeLab.DB_Service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Otps")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "opt_id",updatable = false)
    private UUID otpId;

    @Column(name = "opt",updatable = false,nullable = false)
    private String otp;

    @Column(name = "user_email",updatable = false,nullable = false)
    private String email;

    @Column(name = "expiry_time",updatable = false,nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "verified",nullable = false)
    private Boolean isVerified = false;

}
