package com.CodeLab.DB_Service.responseDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class LeaderboardEntryResponseDTO {
    private int rank;
    private String name;
    private Double percentage;
    private long timeTaken;
}
