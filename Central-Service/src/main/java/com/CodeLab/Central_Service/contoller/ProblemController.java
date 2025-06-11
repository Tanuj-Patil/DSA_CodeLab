package com.CodeLab.Central_Service.contoller;

import com.CodeLab.Central_Service.enums.Difficulty;
import com.CodeLab.Central_Service.model.Problem;
import com.CodeLab.Central_Service.requestDTO.ProblemRequestDTO;
import com.CodeLab.Central_Service.responseDTO.GeneralResponseDTO;
import com.CodeLab.Central_Service.responseDTO.ProblemAddedResponseDTO;
import com.CodeLab.Central_Service.responseDTO.TokenValidationResponseDTO;
import com.CodeLab.Central_Service.service.AuthenticationService;
import com.CodeLab.Central_Service.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/central/problem")
public class ProblemController {
    @Autowired
    ProblemService problemService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<?> addProblem(@RequestBody ProblemRequestDTO problemRequestDTO,@RequestHeader(value = "Authorization", required = false) String header){
        TokenValidationResponseDTO tokenValidationResponseDTO = authenticationService.validateAdminToken(header);
        System.out.println(tokenValidationResponseDTO.getMessage());

        if (!tokenValidationResponseDTO.isValid()) {
            return new ResponseEntity<>(tokenValidationResponseDTO, HttpStatus.UNAUTHORIZED);
        }
        ProblemAddedResponseDTO responseDTO = problemService.addProblem(problemRequestDTO);

        if(responseDTO.getProblem() != null){
            responseDTO.setMessage("Problem Added Successfully:)");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }
        else{
            responseDTO.setMessage("Problem Not Added!!!");
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProblems(){
        List<Problem> problemList = problemService.getProblems();
        return new ResponseEntity<>(problemList,HttpStatus.OK);
    }

    @GetMapping("/get/page")
    public ResponseEntity<?> getProblemsByPage(@RequestParam int pageNo){
        List<Problem> problemList = problemService.getProblemsByPage(pageNo);
        return new ResponseEntity<>(problemList,HttpStatus.OK);
    }

    @GetMapping("/get/{problemId}")
    public ResponseEntity<?> getProblem(@PathVariable UUID problemId){
        Problem problem = problemService.getProblemById(problemId);
        if(problem == null){
            GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();
            generalResponseDTO.setMessage("Problem with Id-"+problemId+" not Found!!!");
            return new ResponseEntity<>(generalResponseDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(problem,HttpStatus.OK);
    }

    @GetMapping("/get-by-topic")
    public ResponseEntity<?> getProblemsTopicWise(@RequestParam String topicName){
        List<Problem> problemList = problemService.getProblemsTopicWise(topicName);
        return new ResponseEntity<>(problemList,HttpStatus.OK);
    }

    @GetMapping("/get-by-company")
    public ResponseEntity<?> getProblemsCompanyWise(@RequestParam String companyName){
        List<Problem> problemList = problemService.getProblemsCompanyWise(companyName);
        return new ResponseEntity<>(problemList,HttpStatus.OK);
    }

    @GetMapping("/get-count-by-topic")
    public ResponseEntity<?> getProblemsCountTopicWise(@RequestParam String topicName){
        Long ctn = problemService.getProblemsCountTopicWise(topicName);
        return new ResponseEntity<>(ctn,HttpStatus.OK);
    }

    @GetMapping("/get-count-by-company")
    public ResponseEntity<?> getProblemsCountCompanyWise(@RequestParam String companyName){
        Long ctn = problemService.getProblemsCountCompanyWise(companyName);
        return new ResponseEntity<>(ctn,HttpStatus.OK);
    }

    @GetMapping("/get-by-difficulty")
    public ResponseEntity<?> getByDifficulty(@RequestParam Difficulty difficulty){
        List<Problem> problemList = problemService.getProblemByDifficulty(difficulty);

        if(problemList == null || problemList.isEmpty()){
            return new ResponseEntity<>(problemList,HttpStatus.OK);
        }
        return new ResponseEntity<>(problemList,HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<?> searchProblem(@RequestParam String keyword){
        List<Problem> problemList =  problemService.searchProblem(keyword);
        if(problemList == null || problemList.size() == 0){
            return new ResponseEntity<>(problemList,HttpStatus.OK);
        }
        return new ResponseEntity<>(problemList,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{problemId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID problemId, @RequestHeader(value = "Authorization", required = false) String header){
        TokenValidationResponseDTO responseDTO = authenticationService.validateAdminToken(header);
        System.out.println(responseDTO.getMessage());

        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
        GeneralResponseDTO generalResponseDTO = problemService.deleteById(problemId);
        return new ResponseEntity<>(generalResponseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAll(@RequestHeader(value = "Authorization", required = false) String header){
        TokenValidationResponseDTO responseDTO = authenticationService.validateAdminToken(header);
        System.out.println(responseDTO.getMessage());

        if (!responseDTO.isValid()) {
            return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
        }

        GeneralResponseDTO generalResponseDTO = problemService.deleteAll();
        return new ResponseEntity<>(generalResponseDTO,HttpStatus.OK);
    }

}
