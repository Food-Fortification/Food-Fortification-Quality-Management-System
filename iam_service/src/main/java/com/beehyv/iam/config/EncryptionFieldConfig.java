package com.beehyv.iam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EncryptionFieldConfig {

    @Value("${encryption.fields}")
    private String encryptionFields;

    public List<String> getFieldsToEncrypt() {
        return Arrays.asList(encryptionFields.split(","));
    }
}
