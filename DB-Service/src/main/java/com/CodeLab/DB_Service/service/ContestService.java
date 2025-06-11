package com.CodeLab.DB_Service.service;

import com.CodeLab.DB_Service.exceptions.NotFoundException;
import com.CodeLab.DB_Service.model.*;
import com.CodeLab.DB_Service.repository.*;
import com.CodeLab.DB_Service.requestDTO.ContestRequestDTO;
import com.CodeLab.DB_Service.requestDTO.PartialContestSubmissionRequestDTO;
import com.CodeLab.DB_Service.requestDTO.ProblemRequestDTO;
import com.CodeLab.DB_Service.requestDTO.UpdatePartialContestSubmissionRequestDTO;
import com.CodeLab.DB_Service.requestdto_converter.ContestConverter;
import com.CodeLab.DB_Service.requestdto_converter.PartialContestSubmissionConverter;
import com.CodeLab.DB_Service.responseDTO.ContestAddedResponseDTO;
import com.CodeLab.DB_Service.responseDTO.ContestStartResponseDTO;
import com.CodeLab.DB_Service.responseDTO.LiveContestResponseDTO;
import com.CodeLab.DB_Service.responseDTO.UpcomingContestResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContestService {

    @Autowired
    ContestRepo contestRepo;

    @Autowired
    ContestProblemRepo contestProblemRepo;

    @Autowired
    ProblemService problemService;

    @Autowired
    ContestUserRepo contestUserRepo;

    @Autowired
    UserService userService;

    @Autowired
    FullContestSubmissionRepo submissionRepo;

    @Autowired
    PartialContestSubmissionRepo partialContestSubmissionRepo;

    public ContestAddedResponseDTO addContest(ContestRequestDTO requestDTO){
        Contest contest = ContestConverter.contestConverter(requestDTO);

        contest = contestRepo.save(contest);

        List<ProblemRequestDTO> newProblemList = requestDTO.getNewProblemList();
        List<UUID> existingProblemList = requestDTO.getExistingProblemList();

        List<Problem> problemList = new ArrayList<>();

        for(UUID id : existingProblemList){
            Problem problem = problemService.getProblem(id);
            problemList.add(problem);
        }

        for(ProblemRequestDTO problemRequestDTO : newProblemList){
            Problem problem = problemService.addProblem(problemRequestDTO,false);
            problemList.add(problem);
        }

        List<ContestProblem> contestProblemList = new ArrayList<>();

        for (Problem problem: problemList){
            ContestProblem contestProblem = ContestConverter.contestProblemConverter(problem,contest);
            contestProblem = contestProblemRepo.save(contestProblem);
            contestProblemList.add(contestProblem);
        }

        ContestAddedResponseDTO responseDTO = new ContestAddedResponseDTO();
        responseDTO.setContest(contest);
        responseDTO.setContestProblemList(contestProblemList);

        return responseDTO;
    }

    public Contest getContestById(UUID contestId){
        return contestRepo.findById(contestId).orElse(null);
    }

    public List<UpcomingContestResponseDTO> getUpcomingContests(UUID userId){
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id-" + userId + " not Found!!!");
        }

        List<Contest> list=  contestRepo.findAllUpcomingContests();

        List<UpcomingContestResponseDTO> responseDTOList = new ArrayList<>();

        for(Contest contest : list){
            ContestUser contestUser = contestUserRepo.findByUserAndContest(userId,contest.getContestId()).orElse(null);
            UpcomingContestResponseDTO responseDTO = new UpcomingContestResponseDTO();
            responseDTO.setContest(contest);
            if(contestUser != null){
                responseDTO.setUserAlreadyRegistered(true);
            }
            responseDTOList.add(responseDTO);
        }
        return responseDTOList;
    }

    public List<UpcomingContestResponseDTO> getUpcomingContests(int pageNo,UUID userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id-" + userId + " not Found!!!");
        }

        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        List<Contest> list=  contestRepo.findAllUpcomingContests(pageable).getContent();

        List<UpcomingContestResponseDTO> responseDTOList = new ArrayList<>();

        for(Contest contest : list){
            ContestUser contestUser = contestUserRepo.findByUserAndContest(userId,contest.getContestId()).orElse(null);
            UpcomingContestResponseDTO responseDTO = new UpcomingContestResponseDTO();
            responseDTO.setContest(contest);
            if(contestUser != null){
                responseDTO.setUserAlreadyRegistered(true);
            }
            responseDTOList.add(responseDTO);
        }
        return responseDTOList;
    }

    public ContestUser registerForContest(UUID userId, UUID contestId) {
        // 1. Check contest existence
        Contest contest = contestRepo.findById(contestId).orElse(null);
        if (contest == null) {
            throw new NotFoundException("Contest with id-" + contestId + " not Found!!!");
        }

        // 2. Check if contest has started
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        if (contest.getStartTime().isBefore(now)) {
            throw new RuntimeException("Registration closed. Contest has already started.");
        }

        // 3. Check user existence
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id-" + userId + " not Found!!!");
        }

        // 4. Check if user is already registered
        Optional<ContestUser> existing = contestUserRepo.findByUserAndContest(userId, contestId);
        if (existing.isPresent()) {
            throw new RuntimeException("User is already registered for this contest.");
        }

        // 5. Register user for contest
        ContestUser contestUser = new ContestUser();
        contestUser.setUser(user);
        contestUser.setContest(contest);
        contestUserRepo.save(contestUser);

        return contestUser;
    }

    public List<LiveContestResponseDTO> getLiveContests(UUID userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id-" + userId + " not Found!!!");
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

        // Fetch all currently live contests
        List<Contest> liveContests = contestRepo.findAllLiveContests(now);

        List<LiveContestResponseDTO> responseList = new ArrayList<>();

        for (Contest contest : liveContests) {
            UUID contestId = contest.getContestId();

            boolean isRegistered = contestUserRepo
                    .findByUserAndContest(userId, contestId)
                    .isPresent();

            Optional<FullContestSubmission> submissionOpt =
                    submissionRepo.findByContestIdAndUserId(contestId, userId);

            boolean isSubmitted = false;
            boolean isRejoin = false;

            if (submissionOpt.isPresent()) {
                FullContestSubmission submission = submissionOpt.get();
                if (submission.getUserSubmittedAt() != null) {
                    isSubmitted = true;
                } else {
                    isRejoin = true; // started but not submitted
                }
            }

            LiveContestResponseDTO dto = new LiveContestResponseDTO();
            dto.setContest(contest);
            dto.setUserRegistered(isRegistered);
            dto.setContestSubmitted(isSubmitted);
            dto.setUserRejoin(isRejoin); // ✅ Set rejoin flag

            responseList.add(dto);
        }

        return responseList;
    }



    public List<LiveContestResponseDTO> getLiveContests(UUID userId, int pageNo) {
        int pageSize = 2;

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id-" + userId + " not Found!!!");
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        List<Contest> contestList = contestRepo.findAllLiveContestsPaginated(now, pageable).getContent();
        List<LiveContestResponseDTO> responseDTOList = new ArrayList<>();

        for (Contest contest : contestList) {
            UUID contestId = contest.getContestId();

            boolean isRegistered = contestUserRepo
                    .findByUserAndContest(userId, contestId)
                    .isPresent();

            Optional<FullContestSubmission> submissionOpt =
                    submissionRepo.findByContestIdAndUserId(contestId, userId);

            boolean isSubmitted = false;
            boolean isRejoin = false;

            if (submissionOpt.isPresent()) {
                FullContestSubmission submission = submissionOpt.get();
                if (submission.getUserSubmittedAt() != null) {
                    isSubmitted = true;
                } else {
                    isRejoin = true; // Started but not submitted
                }
            }

            LiveContestResponseDTO dto = new LiveContestResponseDTO();
            dto.setContest(contest);
            dto.setUserRegistered(isRegistered);
            dto.setContestSubmitted(isSubmitted);
            dto.setUserRejoin(isRejoin); // ✅ Set rejoin flag

            responseDTOList.add(dto);
        }

        return responseDTOList;
    }

//    public static String formatDuration(long totalSeconds) {
//        long hours = totalSeconds / 3600;
//        long minutes = (totalSeconds % 3600) / 60;
//        long seconds = totalSeconds % 60;
//
//        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
//    }

    public ContestStartResponseDTO userStartsContest(UUID userId, UUID contestId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id-" + userId + " not Found!!!");
        }

        Contest contest = contestRepo.findById(contestId).orElse(null);
        if (contest == null) {
            throw new NotFoundException("Contest with id-" + contestId + " not Found!!!");
        }

        if (LocalDateTime.now().isBefore(contest.getStartTime())) {
            throw new RuntimeException("Contest with id-" + contestId + " has not started yet!!!");
        }
        // Also consider if the contest has already ended
        if (LocalDateTime.now().isAfter(contest.getEndTime())) {
            throw new RuntimeException("Contest with id-" + contestId + " has already ended!!!");
        }

        boolean isRegistered = contestUserRepo.findByUserAndContest(userId, contestId).isPresent();
        if (!isRegistered) {
            throw new RuntimeException("User is not registered for this contest.");
        }

        if (LocalDateTime.now().isBefore(contest.getStartTime())) {
            throw new RuntimeException("Contest with id-" + contestId + " has not started yet!!!");
        }
        // Also consider if the contest has already ended
        if (LocalDateTime.now().isAfter(contest.getEndTime())) {
            throw new RuntimeException("Contest with id-" + contestId + " has already ended!!!");
        }

        Optional<FullContestSubmission> existingSubmissionOpt =
                submissionRepo.findByContestIdAndUserId(contestId, userId);

        LocalDateTime now = LocalDateTime.now();
        FullContestSubmission submission;

        if (existingSubmissionOpt.isPresent()) {
            submission = existingSubmissionOpt.get();
            if (submission.getUserSubmittedAt() != null) {
                throw new RuntimeException("User has already submitted the contest.");
            }
            // Allow rejoining
        } else {
            submission = new FullContestSubmission();
            submission.setUser(user);
            submission.setContest(contest);
            submission.setUserStartedAt(now);
            submission.setUserSubmittedAt(null);
            submission.setPercentage(0.0);
            submission.setTotalTimeTaken(0L);
            submissionRepo.save(submission);
        }

        long durationSeconds = contest.getDuration(); // 👈 Duration now stored in seconds

        LocalDateTime userDeadline = submission.getUserStartedAt().plusSeconds(durationSeconds);
        LocalDateTime effectiveDeadline = userDeadline.isBefore(contest.getEndTime()) ? userDeadline : contest.getEndTime();

        long remainingTimeInSeconds = Duration.between(now, effectiveDeadline).getSeconds();
        if (remainingTimeInSeconds < 0) {
            remainingTimeInSeconds = 0; // Just in case user is too late
        }

        List<ContestProblem> contestProblemList = contestProblemRepo.findByContestIdNative(contestId);
        List<Problem> problemList = new ArrayList<>();
        for (ContestProblem contestProblem : contestProblemList) {
            problemList.add(contestProblem.getProblem());
        }

        ContestStartResponseDTO responseDTO = new ContestStartResponseDTO();
        responseDTO.setContest(contest);
        responseDTO.setProblemList(problemList);
        responseDTO.setRemainingTimeInSeconds(remainingTimeInSeconds); // 👈 Set remaining time

        return responseDTO;
    }

    public Problem getContestProblem(UUID problemId){
        return problemService.getProblem(problemId);
    }

    public PartialContestSubmission addPartialContestSubmission(PartialContestSubmissionRequestDTO requestDTO, Problem problem, User user, Contest contest){
        PartialContestSubmission partialContestSubmission = PartialContestSubmissionConverter.contestPartialSubmissionConverter(requestDTO,problem,user,contest);
        return partialContestSubmissionRepo.save(partialContestSubmission);
    }

    public PartialContestSubmission getPartialContestSubmission(UUID submissionId) {
        return partialContestSubmissionRepo.findById(submissionId).orElse(null);
    }
    public PartialContestSubmission updatePartialContestSubmission(UpdatePartialContestSubmissionRequestDTO requestDTO){
        PartialContestSubmission submission = this.getPartialContestSubmission(requestDTO.getSubmissionId());

        if (submission == null){
            throw new NotFoundException("Partial Submission with id-" + requestDTO.getSubmissionId() + " not Found!!!");
        }

        submission.setSubmissionStatus(requestDTO.getSubmissionStatus());
        submission.setCode(requestDTO.getCode());
        submission.setError(requestDTO.getError());
        submission.setPercentage(requestDTO.getPercentage());
        submission.setTotalTestCases(requestDTO.getTotalTestCases());
        submission.setTotalPassedTestCases(requestDTO.getTotalPassedTestCases());

        return partialContestSubmissionRepo.save(submission);
    }


    public FullContestSubmission submitContest(UUID userId, UUID contestId) {
        Optional<FullContestSubmission> optionalSubmission =
                submissionRepo.findByContestIdAndUserId(contestId, userId);

        if (optionalSubmission.isEmpty()) {
            throw new IllegalArgumentException("User has not started the contest");
        }

        FullContestSubmission submission = optionalSubmission.get();

        if (submission.getUserSubmittedAt() != null) {
            throw new IllegalStateException("User has already submitted the contest");
        }

        List<ContestProblem> contestProblemList = contestProblemRepo.findByContestIdNative(contestId);
        List<Problem> allProblems = contestProblemList.stream()
                .map(ContestProblem::getProblem)
                .toList();

        double totalPercentage = 0.0;
        int problemCount = allProblems.size();

        for (Problem problem : allProblems) {
            List<PartialContestSubmission> submissions = partialContestSubmissionRepo
                    .findByContestIdAndUserIdAndProblemId(contestId, userId, problem.getProblemId());

            if (!submissions.isEmpty()) {
                // Take the latest one or average one – here we use the latest submission
                PartialContestSubmission latest = submissions.get(submissions.size() - 1);
                totalPercentage += latest.getPercentage();
            }
        }

        double averagePercentage = problemCount > 0 ? (totalPercentage / problemCount) : 0.0;

        LocalDateTime submittedAt = LocalDateTime.now();
        submission.setUserSubmittedAt(submittedAt);
        submission.setTotalTimeTaken(Duration.between(submission.getUserStartedAt(), submittedAt).getSeconds()); //in seconds
        submission.setPercentage(averagePercentage);

        return submissionRepo.save(submission);
    }
}
