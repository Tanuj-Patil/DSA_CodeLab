package com.CodeLab.Central_Service.requestDTO;

import com.CodeLab.Central_Service.enums.Language;
import lombok.*;

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
