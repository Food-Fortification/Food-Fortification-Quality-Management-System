package org.path.iam.service;

import lombok.RequiredArgsConstructor;
import org.path.iam.config.EncryptionFieldConfig;
import org.path.iam.helper.EncryptionHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    private final EncryptionFieldConfig encryptionFieldConfig;



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

