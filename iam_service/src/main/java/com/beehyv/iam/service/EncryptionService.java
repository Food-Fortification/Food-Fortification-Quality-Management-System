package com.beehyv.iam.service;

import com.beehyv.iam.config.EncryptionFieldConfig;
import com.beehyv.iam.helper.EncryptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Autowired
    private EncryptionHelper encryptionHelper;
    @Autowired
    private EncryptionFieldConfig encryptionFieldConfig;

    public Object encryptField(String field, Object value) throws Exception {
        if (encryptionFieldConfig.getFieldsToEncrypt().contains(field) && value != null) {
            return encryptionHelper.encrypt(value.toString());
        }
        return value;
    }

    public Object decryptField(String field, Object value) throws Exception {
        if (encryptionFieldConfig.getFieldsToEncrypt().contains(field) && value != null) {
            return encryptionHelper.decrypt(value.toString());
        }
        return value;
    }
}

