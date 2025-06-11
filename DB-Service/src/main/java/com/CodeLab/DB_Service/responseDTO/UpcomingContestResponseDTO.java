package com.CodeLab.DB_Service.responseDTO;

import com.CodeLab.DB_Service.model.Contest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UpcomingContestResponseDTO {
    private Contest contest;
    private boolean userAlreadyRegistered;
}
