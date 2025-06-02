package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Admin;
import com.CodeLab.DB_Service.requestDTO.AdminRequestDTO;

public class AdminConverter {

    public static Admin adminConverter(AdminRequestDTO adminRequestDTO){
        Admin admin = new Admin();
        admin.setName(adminRequestDTO.getName());
        admin.setEmail(adminRequestDTO.getEmail());
        admin.setPassword(adminRequestDTO.getPassword());

        return admin;
    }
}
