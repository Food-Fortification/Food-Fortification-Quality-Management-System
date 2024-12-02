package org.path.iam.helper;

import org.path.parent.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Component
public class EncryptionHelper {

    private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final String TRANSFORMATION = "AES";
    @Value("${encryption.secret.iv}")
    private String encryptionIv;
    @Value("${encryption.secret.key}")
    private String encryptionKey;
    private static String key;
    private static String ivKey;

    @PostConstruct
    public void init(){
        System.out.println("encrypt key is " + encryptionKey);
        System.out.println("encrypt iv is " + encryptionIv);
        key = encryptionKey;
        ivKey = encryptionIv;
    }



    public static String encrypt(String data)  {
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] iv = ivKey.getBytes(StandardCharsets.UTF_8);
            IvParameterSpec ivParams = new IvParameterSpec(iv);

            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            byte[] iv = ivKey.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), TRANSFORMATION);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e){
            throw new CustomException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}

