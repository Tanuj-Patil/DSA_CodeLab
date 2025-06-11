package com.CodeLab.DB_Service.model;

import com.CodeLab.DB_Service.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "problem_id",updatable = false)
    private UUID problemId;

    @Column(name = "problem_No",unique = true, nullable = false,updatable = false)
    private int problemNo;

    @Column(name = "problem_title",nullable = false,unique = true)
    private String problemTitle;

    @Column(name = "problem_difficulty",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Difficulty problemDifficulty;

    @Column(name = "topic_list",nullable = false,columnDefinition = "TEXT")
    private String topicList;

    @Column(name = "company_list",nullable = false,columnDefinition = "TEXT")
    private String companyList;

    @Column(name = "problem_description",nullable = false,unique = true,columnDefinition = "TEXT")
    private String problemDescription;

    @JsonManagedReference
    @OneToMany(mappedBy = "problem",cascade = CascadeType.ALL)
    private List<Example> exampleList = new ArrayList<>();

    @Column(name = "problem_constraints",nullable = false,columnDefinition = "TEXT")
    private String problemConstraints;

    @JsonManagedReference
    @OneToMany(mappedBy = "problem",cascade = CascadeType.ALL)
    private List<TestCase> testCasesList = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "problem",cascade = CascadeType.ALL)
    private Solution solution;

    @JsonManagedReference
    @OneToMany(mappedBy = "problem",cascade = CascadeType.ALL)
    private List<CodeTemplate> codeTemplateList = new ArrayList<>();

    @Column(name = "problem_note")
    private String note;

    @Column(name = "is_visible")
    private boolean isVisible;


}
