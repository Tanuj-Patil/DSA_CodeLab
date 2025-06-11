package com.CodeLab.DB_Service.scheduler;

import com.CodeLab.DB_Service.model.Contest;
import com.CodeLab.DB_Service.repository.ContestRepo;
import com.CodeLab.DB_Service.service.ContestService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaderboardScheduler {

    @Autowired
    private ContestRepo contestRepo;

    @Autowired
    private ContestService contestService;

    /**
     * This method runs every 10 seconds (after the previous execution completes).
     * It generates leaderboards for contests that have ended but don't yet have one.
     */
    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void generateLeaderboardsForEndedContests() {
        System.out.println("Checking for ended contests without leaderboard at " + LocalDateTime.now());

        List<Contest> endedContests = contestRepo.findAllEndedContestsWithoutLeaderboard(LocalDateTime.now());

        if (endedContests.isEmpty()) {
            System.out.println("No ended contests without leaderboard found.");
            return;
        }

        for (Contest contest : endedContests) {
            try {
                contestService.generateLeaderboard(contest.getContestId());
                System.out.println("✅ Generated leaderboard for contest: " + contest.getContestName());
            } catch (Exception e) {
                System.err.println("❌ Failed to generate leaderboard for contest: " + contest.getContestName());
                e.printStackTrace();
            }
        }
    }
}
