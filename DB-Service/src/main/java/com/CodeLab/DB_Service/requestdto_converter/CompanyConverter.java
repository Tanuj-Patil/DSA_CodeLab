package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Company;
import com.CodeLab.DB_Service.requestDTO.CompanyRequestDTO;

import java.util.ArrayList;

public class CompanyConverter {
    public static Company companyConverter(CompanyRequestDTO requestDTO) {
        Company company = new Company();
        company.setCompanyName(requestDTO.getCompanyName());

        return company;
    }
}
