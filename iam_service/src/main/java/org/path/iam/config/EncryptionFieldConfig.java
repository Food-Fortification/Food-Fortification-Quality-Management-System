package org.path.iam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class EncryptionFieldConfig {

    @Value("${encryption.fields}")
    private String fields;

    private static String encryptionFields;

    @PostConstruct
    public void init(){
        encryptionFields = fields;
    }

    public static List<String> getFieldsToEncrypt() {
        return Arrays.asList(encryptionFields.split(","));
    }
}
