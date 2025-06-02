package com.CodeLab.DB_Service.service;

import com.CodeLab.DB_Service.model.Admin;
import com.CodeLab.DB_Service.repository.AdminRepo;
import com.CodeLab.DB_Service.requestDTO.AdminRequestDTO;
import com.CodeLab.DB_Service.requestdto_converter.AdminConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;

    public Admin getAdminByEmail(String email){
        return adminRepo.getByEmail(email).orElse(null);
    }

    public Admin getUserById(UUID id){
        return adminRepo.findById(id).orElse(null);
    }

    public Admin registerAdmin(AdminRequestDTO adminRequestDTO){
        Admin admin = AdminConverter.adminConverter(adminRequestDTO);
        adminRepo.save(admin);
        return admin;
    }

    public boolean changePassword(UUID adminId, String newPassword){
        Admin admin = adminRepo.findById(adminId).orElse(null);

        if(admin == null){
            return false;
        }

        admin.setPassword(newPassword);
        adminRepo.save(admin);
        return true;
    }

}
