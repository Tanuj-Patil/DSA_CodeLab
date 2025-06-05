package com.CodeLab.Central_Service.integration;

import com.CodeLab.Central_Service.enums.Difficulty;
import com.CodeLab.Central_Service.model.Problem;
import com.CodeLab.Central_Service.model.Submission;
import com.CodeLab.Central_Service.requestDTO.*;

import com.CodeLab.Central_Service.responseDTO.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class DBService extends RestAPI{

    @Value("${db.service.base}")
    String baseURL;

    @Autowired
    ModelMapper modelMapper;

    //User Endpoint calls

    public IsEmailAlreadyPresentResponseDTO callIsUserEmailAlreadyExists(String email){
        String endpoint = "/user/is-user-already-present/"+email;

        Object response = this.makeGetCall(baseURL,endpoint,new HashMap<>());

        return modelMapper.map(response, IsEmailAlreadyPresentResponseDTO.class);
    }

    public UserRegisteredResponseDTO callRegisterUser(UserRequestDTO userRequestDTO){
        String endpoint = "/user/register";

        Object response = this.makePostCall(baseURL,endpoint,userRequestDTO,new HashMap<>());

        return modelMapper.map(response,UserRegisteredResponseDTO.class);
    }

    public void callGenerateOtp(OTPGenerateRequestDTO requestDTO){
        String endpoint = "/otp/generate";

        this.makePostCall(baseURL,endpoint,requestDTO,new HashMap<>());
    }

    public OTPVerificationResponseDTO callVerifyUserOtp(UserRequestDTO requestDTO, String otp){
        String endpoint = "/otp/verify";
        HashMap<String,String> map = new HashMap<>();
        map.put("otp",otp);

        Object response = this.makePutCall(baseURL,endpoint,requestDTO,map);

        return modelMapper.map(response, OTPVerificationResponseDTO.class);

    }

    public OTPVerificationResponseDTO callVerifyAdminOtp(AdminRequestDTO requestDTO, String otp){
        String endpoint = "/otp/verify";
        HashMap<String,String> map = new HashMap<>();
        map.put("otp",otp);

        Object response = this.makePutCall(baseURL,endpoint,requestDTO,map);

        return modelMapper.map(response, OTPVerificationResponseDTO.class);

    }


    //Admin Endpoints

    public IsEmailAlreadyPresentResponseDTO callIsAdminEmailAlreadyExists(String email){
        String endpoint = "/admin/is-admin-already-present/"+email;

        Object response = this.makeGetCall(baseURL,endpoint,new HashMap<>());

        return modelMapper.map(response, IsEmailAlreadyPresentResponseDTO.class);
    }

    public AdminRegisteredResponseDTO callRegisterAdmin(AdminRequestDTO adminRequestDTO){
        String endpoint = "/admin/register";

        Object response = this.makePostCall(baseURL,endpoint,adminRequestDTO,new HashMap<>());

        return modelMapper.map(response,AdminRegisteredResponseDTO.class);
    }


    //Problem endpoint calls

    public ProblemAddedResponseDTO callAddProblem(ProblemRequestDTO requestDTO){
        String endpoint = "/problem/add";

        Object response = this.makePostCall(baseURL,endpoint,requestDTO,new HashMap<>());
        if(response == null){
            System.out.println("The response is null");
            return null;
        }

        return modelMapper.map(response,ProblemAddedResponseDTO.class);
    }

    public List<Problem> callGetProblems(){
        String endpoint = "/problem/get";
        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,new HashMap<>());

        List<Problem> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Problem.class));
        }
        return list;
    }

    public List<Problem> callGetProblemsByPage(int pageNo){
        String endpoint = "/problem/get/page";
        HashMap<String,String> map = new HashMap<>();
        map.put("pageNo",pageNo+"");

        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,map);

        List<Problem> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Problem.class));
        }
        return list;
    }


    public Problem callGetProblem(UUID problemId){
        String endpoint = "/problem/get/"+problemId;

        Object response = this.makeGetCall(baseURL,endpoint,new HashMap<>());
        if (response == null){
            return null;
        }

        return modelMapper.map(response,Problem.class);
    }


    public List<Problem> callGetProblemsTopicWise(String topicName){
        String endpoint = "/problem/get-by-topic";
        HashMap<String,String> map = new HashMap<>();
        map.put("topicName",topicName);

        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,map);

        List<Problem> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Problem.class));
        }
        return list;
    }


    public List<Problem> callGetProblemsCompanyWise(String companyName){
        String endpoint = "/problem/get-by-company";
        HashMap<String,String> map = new HashMap<>();
        map.put("companyName",companyName);

        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,map);

        List<Problem> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Problem.class));
        }
        return list;
    }


    public long callGetProblemsCountTopicWise(String topicName){
        String endpoint = "/problem/get-count-by-topic";
        HashMap<String,String> map = new HashMap<>();
        map.put("topicName",topicName);

        Object response = this.makeGetCall(baseURL,endpoint,map);

        return modelMapper.map(response,Long.class);

    }


    public long callGetProblemsCountCompanyWise(String companyName){
        String endpoint = "/problem/get-count-by-company";
        HashMap<String,String> map = new HashMap<>();
        map.put("companyName",companyName);

        Object response = this.makeGetCall(baseURL,endpoint,map);

        return modelMapper.map(response,Long.class);
    }


    public List<Problem> callSearchProblem(String keyword){
        String endpoint = "/problem/search";
        HashMap<String,String> map = new HashMap<>();
        map.put("keyword",keyword);

        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,map);

        List<Problem> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Problem.class));
        }
        return list;
    }

    public List<Problem> callGetProblemByDifficulty(Difficulty difficulty){
        String endpoint = "/problem/get-by-difficulty";
        HashMap<String,String> map = new HashMap<>();
        map.put("difficulty",difficulty.toString());

        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,map);

        List<Problem> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Problem.class));
        }
        return list;
    }

    public GeneralResponseDTO callDeleteById(UUID problemId){
        String endpoint = "/problem/delete/"+problemId;

        Object response = this.makeDeleteCall(baseURL,endpoint,new HashMap<>());

        return modelMapper.map(response,GeneralResponseDTO.class);

    }


    public GeneralResponseDTO callDeleteAll(){
        String endpoint = "/problem/delete";

        Object response = this.makeDeleteCall(baseURL,endpoint,new HashMap<>());

        return modelMapper.map(response,GeneralResponseDTO.class);
    }

    public Submission callAddSubmission(SubmissionRequestDTO requestDTO){
        String endpoint = "/submission/add";

        Object response = this.makePostCall(baseURL,endpoint,requestDTO,new HashMap<>());

       return   modelMapper.map(response,Submission.class);

    }

    public Submission callGetSubmissionById(UUID submissionId){
        String endpoint = "/submission/get-by-id/"+submissionId;

        Object object = this.makeGetCall(baseURL,endpoint,new HashMap<>());
        if (object == null){
            return null;
        }
        return modelMapper.map(object,Submission.class);
    }

    public List<Submission> callGetSubmissionByUserId(UUID userId){
        String endpoint = "/submission/get-by-user-id/"+userId;


        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,new HashMap<>());

        List<Submission> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Submission.class));
        }
        return list;
    }

    public List<Submission> callGetSubmissionByUserIdAndProblemId(UUID userId,UUID problemId){
        String endpoint = "/submission/get-by-user-id-and-problem-id/"+userId;
        HashMap<String,String> map = new HashMap<>();
        map.put("problemId",problemId+"");

        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,map);

        List<Submission> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Submission.class));
        }
        return list;
    }

    public Submission callGetLatestSubmissionOfUser(UUID userId){
        String endpoint = "/submission/get-latest-of-user/"+userId;

        Object object = this.makeGetCall(baseURL,endpoint,new HashMap<>());
        if (object == null){
            return null;
        }
        return modelMapper.map(object,Submission.class);
    }

    public List<Submission> callGetSubmissionsByProblemId(UUID problemId){
        String endpoint = "/submission/get-by-problem-id/"+problemId;
        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,new HashMap<>());

        List<Submission> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Submission.class));
        }
        return list;
    }

    public List<Submission> callGetAllSubmissions(){
        String endpoint = "/submission/get-all";
        List<Object> response = this.makeGetCallAsList(baseURL,endpoint,new HashMap<>());

        List<Submission> list = new ArrayList<>();

        for(Object obj : response){
            list.add(modelMapper.map(obj,Submission.class));
        }
        return list;
    }



}
