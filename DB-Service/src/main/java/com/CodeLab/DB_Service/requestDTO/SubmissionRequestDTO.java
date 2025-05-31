package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.Language;
import com.CodeLab.DB_Service.enums.SubmissionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SubmissionRequestDTO {

    private Language language;

    private UUID userId;

    private UUID problemId;
}
