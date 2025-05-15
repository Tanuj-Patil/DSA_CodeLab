package com.CodeLab.DB_Service.controller;

import com.CodeLab.DB_Service.Exceptions.NotFoundException;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.requestDTO.ProblemRequestDTO;
import com.CodeLab.DB_Service.responseDTO.GeneralResponseDTO;
import com.CodeLab.DB_Service.responseDTO.ProblemAddedResponseDTO;
import com.CodeLab.DB_Service.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/db/problem")
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @PostMapping("/add")
    public ResponseEntity<?> addProblem(@RequestBody ProblemRequestDTO problemRequestDTO){
        Problem problem  = problemService.addProblem(problemRequestDTO);
        ProblemAddedResponseDTO responseDTO = new ProblemAddedResponseDTO();
        responseDTO.setProblem(problem);
        if(problem != null){
            responseDTO.setMessage("Problem Added Successfully:)");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }
        else{
            responseDTO.setMessage("Problem Not Added!!!");
            return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get")
    public List<Problem> getProblems(){
        return problemService.getProblems();
    }

    @GetMapping("/get/page")
    public Page<Problem> getProblems(@RequestParam int pageNo){
        return problemService.getProblems(pageNo);
    }

    @GetMapping("/get/{problemId}")
    public ResponseEntity<?> getProblem(@PathVariable UUID problemId){
        Problem problem = problemService.getProblem(problemId);

        if(problem != null){
            return new ResponseEntity<>(problem,HttpStatus.FOUND);
        }
        else{
            GeneralResponseDTO responseDTO = new GeneralResponseDTO();
            responseDTO.setMessage("Problem with id-"+problemId+" not found!!!");
            return new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getbytopic")
    public List<Problem> getProblemsTopicWise(@RequestParam String topicName){
        return problemService.getProblemsTopicWise(topicName);
    }

    @GetMapping("/getbycompany")
    public List<Problem> getProblemsCompanyWise(@RequestParam String companyName){
        return problemService.getProblemsCompanyWise(companyName);
    }

    @GetMapping("/getcountbytopic")
    public long getProblemsCountTopicWise(@RequestParam String topicName){
        return problemService.getProblemsCountTopicWise(topicName);
    }

    @GetMapping("/getcountbycompany")
    public long getProblemsCountCompanyWise(@RequestParam String companyName){
        return problemService.getProblemsCountCompanyWise(companyName);
    }

    @GetMapping("/search")
    public List<Problem> searchProblem(@RequestParam String keyword){
        return problemService.searchProblem(keyword);
    }

    @DeleteMapping("/delete/{problemId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID problemId){
        try {
            problemService.deleteProblem(problemId);
            GeneralResponseDTO responseDTO = new GeneralResponseDTO();
            responseDTO.setMessage("Problem with id-"+problemId+" has been deleted succussfully:)");
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
        catch (NotFoundException e){
            GeneralResponseDTO responseDTO = new GeneralResponseDTO();
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAll(){
        try {
            problemService.deleteAllProblems();
            GeneralResponseDTO responseDTO = new GeneralResponseDTO();
            responseDTO.setMessage("All problems have been deleted successfully:)");
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
        catch (NotFoundException e){
            GeneralResponseDTO responseDTO = new GeneralResponseDTO();
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
    }
}
