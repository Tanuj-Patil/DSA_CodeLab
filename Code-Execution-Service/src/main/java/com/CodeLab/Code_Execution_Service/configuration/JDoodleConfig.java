package com.CodeLab.Code_Execution_Service.configuration;

import com.CodeLab.Code_Execution_Service.model.JDoodleKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties(prefix = "jdoodle")
public class JDoodleConfig {
    private List<JDoodleKey> keys;

    public List<JDoodleKey> getKeys() {
        return keys;
    }

    public void setKeys(List<JDoodleKey> keys) {
        this.keys = keys;
    }
}
