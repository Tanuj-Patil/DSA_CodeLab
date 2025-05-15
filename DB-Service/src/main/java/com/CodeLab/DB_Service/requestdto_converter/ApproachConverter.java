package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Approach;
import com.CodeLab.DB_Service.model.Solution;
import com.CodeLab.DB_Service.requestDTO.ApproachRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class ApproachConverter {
    public static List<Approach> approachConverter(List<ApproachRequestDTO> approachRequestDTOList, Solution solution){
        List<Approach> approachList = new ArrayList<>();

        for (ApproachRequestDTO approachRequestDTO : approachRequestDTOList){
            Approach approach = new Approach();

            approach.setApproachDescription(approachRequestDTO.getApproachDescription());
            approach.setApproachType(approachRequestDTO.getApproachType());
            approach.setSolution(solution);

            approachList.add(approach);

        }

        return approachList;
    }
}
