package com.CodeLab.DB_Service.requestDTO;

import com.CodeLab.DB_Service.enums.Language;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PartialContestSubmissionRequestDTO {
    private Language language;

    private UUID userId;

    private UUID problemId;

    private UUID contestId;
}
