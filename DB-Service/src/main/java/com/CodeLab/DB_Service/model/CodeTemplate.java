package com.CodeLab.DB_Service.model;
import com.CodeLab.DB_Service.enums.Language;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "CodeTemplate")
public class CodeTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code_template_id",updatable = false)
    private UUID codeTemplateId;

    @Column(name = "visible_template_code",nullable = false,columnDefinition = "TEXT")
    private String visibleTemplateCode;

    @Column(name = "invisible_template_code",nullable = false,columnDefinition = "TEXT")
    private String invisibleTemplateCode;

    @Column(name = "language",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Language language;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;
}
