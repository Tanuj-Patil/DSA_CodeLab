package com.CodeLab.Central_Service.responseDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CentralServiceRunCodeResponse {
    String input;
    String output;
    String expected;
    boolean outputMatched;


}
