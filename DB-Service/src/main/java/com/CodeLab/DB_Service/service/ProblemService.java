package com.CodeLab.DB_Service.service;

import com.CodeLab.DB_Service.enums.Difficulty;
import com.CodeLab.DB_Service.exceptions.NotFoundException;
import com.CodeLab.DB_Service.model.Company;
import com.CodeLab.DB_Service.model.Problem;
import com.CodeLab.DB_Service.model.TestCase;
import com.CodeLab.DB_Service.model.Topic;
import com.CodeLab.DB_Service.repository.CompanyRepo;
import com.CodeLab.DB_Service.repository.ProblemRepo;
import com.CodeLab.DB_Service.repository.TopicRepo;
import com.CodeLab.DB_Service.requestDTO.CompanyRequestDTO;
import com.CodeLab.DB_Service.requestDTO.ProblemRequestDTO;
import com.CodeLab.DB_Service.requestDTO.TestCaseRequestDTO;
import com.CodeLab.DB_Service.requestDTO.TopicRequestDTO;
import com.CodeLab.DB_Service.requestdto_converter.CompanyConverter;
import com.CodeLab.DB_Service.requestdto_converter.ProblemConverter;
import com.CodeLab.DB_Service.requestdto_converter.TestCaseConverter;
import com.CodeLab.DB_Service.requestdto_converter.TopicConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProblemService {

    @Autowired
    ProblemRepo problemRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    TopicRepo topicRepo;

    public long getProblemCount(){
        return problemRepo.count();
    }

    public Problem addProblem(ProblemRequestDTO problemRequestDTO){
        problemRequestDTO.setTopicList(problemRequestDTO.getTopicList().toUpperCase());
        problemRequestDTO.setCompanyList(problemRequestDTO.getCompanyList().toUpperCase());
        Problem problem = ProblemConverter.problemConverter(problemRequestDTO);
        int totalProblems = (int)this.getProblemCount();

        problem.setProblemNo(totalProblems + 1);

        String[] topicList = problemRequestDTO.getTopicList().split(",");
        for(String t : topicList){
            t = t.trim();
            if(topicRepo.findByTopicName(t).orElse(null) == null){
                Topic topic = TopicConverter.topicConverter(new TopicRequestDTO(t));
                topicRepo.save(topic);
            }

        }

        String[] companyList = problemRequestDTO.getCompanyList().split(",");
        for(String c : companyList){
            c = c.trim();
            if(companyRepo.findByCompanyName(c).orElse(null) == null){
                Company company = CompanyConverter.companyConverter(new CompanyRequestDTO(c));
                companyRepo.save(company);
            }

        }


        return problemRepo.save(problem);
    }

    public List<Problem> getProblems(){
        return problemRepo.findAll();
    }

    public Page<Problem> getProblems(int pageNo){
        Sort sort = Sort.by("problemNo").ascending();
        Pageable pageable = PageRequest.of(pageNo,2,sort);
        return problemRepo.findAll(pageable);
    }

    public Problem getProblem(UUID problemId){
        return problemRepo.findById(problemId).orElse(null);
    }

    public List<Problem> getProblemsTopicWise(String topicName){
        topicName = topicName.toUpperCase().trim().replaceAll("_"," ");
        return problemRepo.findByTopicName(topicName);
    }

    public List<Problem> getProblemsCompanyWise(String companyName){
        companyName = companyName.toUpperCase().trim();
        return problemRepo.findByCompanyName(companyName);
    }

    public long getProblemsCountTopicWise(String topicName){
        topicName = topicName.toUpperCase().trim().replaceAll("_"," ");
        return problemRepo.countByTopicName(topicName);
    }

    public long getProblemsCountCompanyWise(String companyName){
        companyName = companyName.toUpperCase().trim();
        return problemRepo.countByCompanyName(companyName);
    }

    public List<Problem> searchProblem(String keyword){
        return problemRepo.problemSearch(keyword);
    }

    public void deleteProblem(UUID problemId){
        if(this.getProblem(problemId) == null) {
            throw new NotFoundException("Problem with id-"+problemId+" not found!!!");
        }
        problemRepo.deleteById(problemId);

    }

    public void deleteAllProblems(){
        if(this.getProblemCount() == 0){
            throw new NotFoundException("There is not any problem present in the Database");
        }
        problemRepo.deleteAll();
    }

    public String replaceDashBySpace(String s){

        StringBuilder ans = new StringBuilder();

        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);

            if(ch == '-'){
                ans.append(' ');
            }
            else{
                ans.append(ch);
            }
        }
        return ans.toString();
    }

    public void addTestCases(List<TestCaseRequestDTO> testCaseRequestDTOList,UUID problemId){
        Problem problem = this.getProblem(problemId);

        if(problem == null){
            throw new NotFoundException("Problem with id-"+problemId+" not found!!!");
        }


        List<TestCase> testCases = TestCaseConverter.testCaseConverter(testCaseRequestDTOList,problem);

        for(TestCase testCase : testCases){
            problem.getTestCasesList().add(testCase);
        }
        problemRepo.save(problem);

    }

    public List<Problem> getProblemByDifficulty(Difficulty difficulty){
        return problemRepo.findProblemByDifficulty(difficulty.toString());
    }
}
