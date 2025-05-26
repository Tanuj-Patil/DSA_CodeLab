package com.CodeLab.Code_Execution_Service.service;

import com.CodeLab.Code_Execution_Service.model.JDoodleKey;
import com.CodeLab.Code_Execution_Service.model.JDoodleRequest;
import com.CodeLab.Code_Execution_Service.model.JDoodleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JDoodleService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JDoodleKeyManager keyManager;

    @Value("${jdoodle.apiUrl}")
    private String apiUrl;

    public JDoodleResponse executeCode(JDoodleRequest request) {
        int totalKeys = keyManager.getKeysCount(); // You'll add this method
        System.out.println(totalKeys);

        for (int i = 0; i < totalKeys; i++) {
            JDoodleKey key = keyManager.getAvailableKey();
            System.out.println(key.getClientId());

            // Set current key into request
            request.setClientId(key.getClientId());
            request.setClientSecret(key.getClientSecret());

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<JDoodleRequest> entity = new HttpEntity<>(request, headers);

                ResponseEntity<JDoodleResponse> response = restTemplate.postForEntity(apiUrl, entity, JDoodleResponse.class);

                JDoodleResponse body = response.getBody();

                // Check if quota exceeded or error
                if (body != null && body.getStatusCode().equals("200")) {
                    return body;  // Success
                } else if (body != null && body.getOutput() != null && body.getOutput().toLowerCase().contains("quota")) {
                    keyManager.markKeyAsExhausted(key); // Mark exhausted and try next
                } else {
                    // For other errors, optionally mark exhausted or just throw
                    keyManager.markKeyAsExhausted(key);
                }
            } catch (Exception e) {
                keyManager.markKeyAsExhausted(key);
            }
        }
        throw new RuntimeException("All JDoodle API keys are exhausted or failed.");
    }
}

