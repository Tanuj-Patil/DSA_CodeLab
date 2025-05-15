package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.Gender;
import com.CodeLab.DB_Service.enums.UserCategory;
import com.CodeLab.DB_Service.model.Location;
import com.CodeLab.DB_Service.model.Submission;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserRequestDTO {

    private String name;

    private String email;

    private String password;

    private Gender gender;

    private LocationRequestDTO locationRequestDTO;

    private UserCategory userCategory;

}
