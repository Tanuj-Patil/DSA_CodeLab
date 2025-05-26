package com.CodeLab.DB_Service.service;

import com.CodeLab.DB_Service.model.Submission;
import com.CodeLab.DB_Service.model.User;
import com.CodeLab.DB_Service.repository.SubmissionRepo;
import com.CodeLab.DB_Service.repository.UserRepo;
import com.CodeLab.DB_Service.requestDTO.UserRequestDTO;
import com.CodeLab.DB_Service.requestdto_converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

   @Autowired
   private SubmissionService submissionService;

    public User registerUser(UserRequestDTO userRequestDTO){
        User user = UserConverter.userConverter(userRequestDTO);
        userRepo.save(user);
        return user;
    }

    public User getUserByEmail(String email){
        return userRepo.getByEmail(email).orElse(null);

    }

    public User getUserById(UUID id){
        return userRepo.findById(id).orElse(null);

    }

    public List<Submission> getSubmissions(UUID userId){
        return submissionService.getSubmissions(userId);
    }

    public List<Submission> getSubmissions(UUID userId,UUID problemId){
        return submissionService.getSubmissions(userId,problemId);
    }

    public Submission getSubmission(UUID submissionId){
        return submissionService.getSubmission(submissionId);
    }

    public boolean changePassword(UUID userId,String newPassword){
        User user = userRepo.findById(userId).orElse(null);

        if(user == null){
            return false;
        }

        user.setPassword(newPassword);

        userRepo.save(user);

        return true;

    }


}
