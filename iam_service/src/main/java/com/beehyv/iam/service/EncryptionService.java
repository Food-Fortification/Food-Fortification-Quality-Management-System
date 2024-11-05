package com.beehyv.iam.service;

import com.beehyv.iam.config.EncryptionFieldConfig;
import com.beehyv.iam.helper.EncryptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {


    public static Object encryptField(String field, Object value) throws Exception {
        if (EncryptionFieldConfig.getFieldsToEncrypt().contains(field) && value != null) {
            return EncryptionHelper.encrypt(value.toString());
        }
        return value;
    }

    public static Object decryptField(String field, Object value) throws Exception {
        if (EncryptionFieldConfig.getFieldsToEncrypt().contains(field) && value != null) {
            return EncryptionHelper.decrypt(value.toString());
        }
        return value;
    }
}

