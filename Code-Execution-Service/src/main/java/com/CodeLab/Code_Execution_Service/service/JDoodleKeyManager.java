package com.CodeLab.Code_Execution_Service.service;

import com.CodeLab.Code_Execution_Service.configuration.JDoodleConfig;
import com.CodeLab.Code_Execution_Service.model.JDoodleKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JDoodleKeyManager {

    private final JDoodleConfig config;
    private int currentIndex = 0;

    @Autowired
    public JDoodleKeyManager(JDoodleConfig config) {
        this.config = config;
    }

    public synchronized JDoodleKey getAvailableKey() {
        List<JDoodleKey> keys = config.getKeys();

        for (int i = 0; i < keys.size(); i++) {
            JDoodleKey key = keys.get(currentIndex);
            currentIndex = (currentIndex + 1) % keys.size();

            if (!key.isExhausted()) {
                return key;
            }
        }
        throw new RuntimeException("All JDoodle keys are exhausted.");
    }

    public void markKeyAsExhausted(JDoodleKey key) {
        key.setExhausted(true);
    }

    public void resetExhaustedFlags() {
        config.getKeys().forEach(k -> k.setExhausted(false));
    }

    public int getKeysCount() {
        return config.getKeys().size();
    }
}
