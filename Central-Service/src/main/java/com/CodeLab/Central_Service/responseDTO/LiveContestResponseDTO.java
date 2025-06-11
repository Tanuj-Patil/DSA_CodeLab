package com.CodeLab.Central_Service.responseDTO;

import com.CodeLab.Central_Service.model.Contest;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class LiveContestResponseDTO {
    private Contest contest;
    private boolean userRegistered;
    private boolean userRejoin;
    private boolean contestSubmitted;
}
