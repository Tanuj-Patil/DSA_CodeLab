package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Approach;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.model.Solution;
import com.CodeLab.DB_Service.requestDTO.SolutionRequestDTO;

import java.util.List;

public class SolutionConverter {
    public static Solution solutionConverter(SolutionRequestDTO solutionRequestDTO, Problem problem){
        Solution solution = new Solution();

        List<Approach> approachList = ApproachConverter.approachConverter(solutionRequestDTO.getApproachRequestDTOList(),solution);

        solution.setApproachList(approachList);
        solution.setProblem(problem);

        return solution;
    }
}
