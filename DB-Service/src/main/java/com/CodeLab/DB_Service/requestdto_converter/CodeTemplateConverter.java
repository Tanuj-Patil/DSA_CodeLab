package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.CodeTemplate;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.requestDTO.CodeTemplateRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class CodeTemplateConverter {
    public static List<CodeTemplate> codeTemplateConverter(List<CodeTemplateRequestDTO> codeTemplateRequestDTOList, Problem problem){
        List<CodeTemplate> codeTemplateList = new ArrayList<>();

        for (CodeTemplateRequestDTO codeTemplateRequestDTO : codeTemplateRequestDTOList){
            CodeTemplate codeTemplate = new CodeTemplate();

            codeTemplate.setTemplateCode(codeTemplateRequestDTO.getTemplateCode());
            codeTemplate.setLanguage(codeTemplateRequestDTO.getLanguage());
            codeTemplate.setProblem(problem);

            codeTemplateList.add(codeTemplate);
        }

        return codeTemplateList;
    }
}
