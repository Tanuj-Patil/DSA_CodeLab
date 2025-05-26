package com.CodeLab.Code_Execution_Service.scheduler;

import com.CodeLab.Code_Execution_Service.service.JDoodleKeyManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JDoodleScheduler {

    private final JDoodleKeyManager keyManager;

    public JDoodleScheduler(JDoodleKeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC") // reset at 5:30 AM daily
    public void resetKeys() {
        keyManager.resetExhaustedFlags();
        System.out.println("✅ JDoodle API keys reset at midnight.");
    }
}
