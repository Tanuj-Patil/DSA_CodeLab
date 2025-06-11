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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepo problemRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private TopicRepo topicRepo;

    public long getProblemCount() {
        return problemRepo.count();
    }

    public Problem addProblem(ProblemRequestDTO problemRequestDTO,boolean isVisible) {
        problemRequestDTO.setTopicList(problemRequestDTO.getTopicList().toUpperCase());
        problemRequestDTO.setCompanyList(problemRequestDTO.getCompanyList().toUpperCase());
        Problem problem = ProblemConverter.problemConverter(problemRequestDTO,isVisible);

        int totalProblems = (int) this.getProblemCount();
        problem.setProblemNo(totalProblems + 1);

        String[] topicList = problemRequestDTO.getTopicList().split(",");
        for (String rawTopic : topicList) {
            final String topicName = rawTopic.trim();
            topicRepo.findByTopicName(topicName).orElseGet(() -> {
                Topic topic = TopicConverter.topicConverter(new TopicRequestDTO(topicName));
                return topicRepo.save(topic);
            });
        }

        String[] companyList = problemRequestDTO.getCompanyList().split(",");
        for (String rawCompany : companyList) {
            final String companyName = rawCompany.trim();
            companyRepo.findByCompanyName(companyName).orElseGet(() -> {
                Company company = CompanyConverter.companyConverter(new CompanyRequestDTO(companyName));
                return companyRepo.save(company);
            });
        }

        return problemRepo.save(problem);
    }


    public List<Problem> getProblems() {
        return problemRepo.getAllVisibleProblems();
    }

    public Page<Problem> getProblems(int pageNo) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by("problemNo").ascending());
        return problemRepo.findAllByIsVisibleTrue(pageable);
    }

    public Problem getProblem(UUID problemId) {
        return problemRepo.findById(problemId).orElse(null);
    }

    public List<Problem> getProblemsTopicWise(String topicName) {
        return problemRepo.findByTopicName(normalize(topicName));
    }

    public List<Problem> getProblemsCompanyWise(String companyName) {
        return problemRepo.findByCompanyName(normalize(companyName));
    }

    public long getProblemsCountTopicWise(String topicName) {
        return problemRepo.countByTopicName(normalize(topicName));
    }

    public long getProblemsCountCompanyWise(String companyName) {
        return problemRepo.countByCompanyName(normalize(companyName));
    }

    public List<Problem> searchProblem(String keyword) {
        return problemRepo.problemSearch(keyword);
    }

    public Page<Problem> searchVisibleProblems(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("problemNo").ascending());
        return problemRepo.searchVisibleProblems(keyword, pageable);
    }

    public void deleteProblem(UUID problemId) {
        if (getProblem(problemId) == null) {
            throw new NotFoundException("Problem with id-" + problemId + " not found!!!");
        }
        problemRepo.deleteById(problemId);
    }

    public void deleteAllProblems() {
        if (this.getProblemCount() == 0) {
            throw new NotFoundException("There is not any problem present in the Database");
        }
        problemRepo.deleteAll();
    }

    public void addTestCases(List<TestCaseRequestDTO> testCaseRequestDTOList, UUID problemId) {
        Problem problem = this.getProblem(problemId);

        if (problem == null) {
            throw new NotFoundException("Problem with id-" + problemId + " not found!!!");
        }

        List<TestCase> testCases = TestCaseConverter.testCaseConverter(testCaseRequestDTOList, problem);

        for (TestCase testCase : testCases) {
            problem.getTestCasesList().add(testCase);
        }
        problemRepo.save(problem);
    }

    public List<Problem> getProblemByDifficulty(Difficulty difficulty) {
        return problemRepo.findProblemByDifficulty(difficulty.toString());
    }

    private String normalize(String s) {
        return s.toUpperCase().trim().replaceAll("_", " ");
    }
}
