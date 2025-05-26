package com.CodeLab.DB_Service.responseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IsUserAlreadyPresentResponseDTO {
    private boolean isPresent;
}
