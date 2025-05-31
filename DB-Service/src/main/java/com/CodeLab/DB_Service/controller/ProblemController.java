package com.CodeLab.DB_Service.controller;

import com.CodeLab.DB_Service.exceptions.NotFoundException;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.requestDTO.ProblemRequestDTO;
import com.CodeLab.DB_Service.requestDTO.TestCaseRequestDTO;
import com.CodeLab.DB_Service.responseDTO.GeneralResponseDTO;
import com.CodeLab.DB_Service.responseDTO.ProblemAddedResponseDTO;
import com.CodeLab.DB_Service.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProblemAddedResponseDTO addProblem(@RequestBody ProblemRequestDTO problemRequestDTO){
        Problem problem  = problemService.addProblem(problemRequestDTO);
        ProblemAddedResponseDTO responseDTO = new ProblemAddedResponseDTO();
        responseDTO.setProblem(problem);
        if(problem != null){
            responseDTO.setMessage("Problem Added Successfully:)");
        }
        else{
            responseDTO.setMessage("Problem Not Added!!!");
        }
        return responseDTO;
    }

    @GetMapping("/get")
    public List<Problem> getProblems(){

        return problemService.getProblems();
    }

    @GetMapping("/get/page")
    public List<Problem> getProblemsByPage(@RequestParam int pageNo){

        return problemService.getProblems(pageNo).getContent();
    }

    @GetMapping("/get/{problemId}")
    public Problem getProblemById(@PathVariable UUID problemId){
        return problemService.getProblem(problemId);
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
    public GeneralResponseDTO deleteById(@PathVariable UUID problemId){
        GeneralResponseDTO responseDTO = new GeneralResponseDTO();
        try {
            problemService.deleteProblem(problemId);
            responseDTO.setMessage("Problem with id-"+problemId+" has been deleted succussfully:)");
            return responseDTO;
        }
        catch (NotFoundException e){
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }
    }

    @DeleteMapping("/delete")
    public GeneralResponseDTO deleteAll(){
        GeneralResponseDTO responseDTO = new GeneralResponseDTO();
        try {
            problemService.deleteAllProblems();

            responseDTO.setMessage("All problems have been deleted successfully:)");
            return responseDTO;
        }
        catch (NotFoundException e){
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }
    }

    @PostMapping("/all-tescases/{problemId}")
    public GeneralResponseDTO addTestCaseList(@RequestBody List<TestCaseRequestDTO> testCaseRequestDTOList,@PathVariable UUID problemId){
        GeneralResponseDTO generalResponseDTO = new GeneralResponseDTO();

        try {
            problemService.addTestCases(testCaseRequestDTOList,problemId);
            generalResponseDTO.setMessage("All TestCases are Added Successfully");

        } catch (RuntimeException e) {
            generalResponseDTO.setMessage(e.getMessage());
        }
        return generalResponseDTO;

    }
}
