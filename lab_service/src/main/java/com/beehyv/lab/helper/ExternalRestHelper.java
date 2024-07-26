package com.beehyv.lab.helper;

import com.beehyv.lab.dto.responseDto.FssaiCertificateResponseDTO;
import com.beehyv.lab.manager.ExternalMetaDataManager;
import com.beehyv.parent.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class ExternalRestHelper {
    private static final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ExternalMetaDataManager externalMetaDataManager;

    public <T> void postApi(String url, String requestBody) {
        log.info("Calling post api for url: " + url);
        log.info("Request body: {}", requestBody);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("username",externalMetaDataManager.findByKeyAndService("username","TCS").getValue());
        String pass = externalMetaDataManager.findByKeyAndService("password","TCS").getValue();
        requestHeaders.add("password",new String(Base64.getDecoder().decode(pass)));
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        log.info("Response received: "+ response.getBody().toString());
    }
    public static List<FssaiCertificateResponseDTO> validateFssaiCertificateNo(String url){

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Content-Type", "application/json");

        log.info("Calling fssai api for lab category: " + url);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<FssaiCertificateResponseDTO>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<FssaiCertificateResponseDTO>>() {
            });
            log.info("Received response from fssai auth api");
            if(Objects.requireNonNull(response.getBody()).isEmpty()){
                log.info("labs doesn't exist for this category");
                return null;
                //TODO: Handle exceptions when
            }
            return response.getBody();
        }
        catch (Exception e){
            throw new CustomException("Error occurred in calling Fssai API", HttpStatus.BAD_REQUEST);
        }

    }

}