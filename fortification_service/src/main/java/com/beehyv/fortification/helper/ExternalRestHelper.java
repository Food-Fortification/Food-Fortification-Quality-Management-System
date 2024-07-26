package com.beehyv.fortification.helper;

import com.beehyv.fortification.dto.external.ExternalResponseDto;
import com.beehyv.fortification.manager.ExternalMetaDataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Slf4j
@Component
public class ExternalRestHelper {
    private static final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ExternalMetaDataManager externalMetaDataManager;

    public <T> ExternalResponseDto postApi(String url, String requestBody) {
        log.info("Calling post api for url: " + url);
        log.info("Request body for external Post Api: {}", requestBody);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("username",externalMetaDataManager.findByKeyAndService("username","TCS").getValue());
        String pass = externalMetaDataManager.findByKeyAndService("password","TCS").getValue();
        requestHeaders.add("password",new String(Base64.getDecoder().decode(pass)));
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<ExternalResponseDto> response = restTemplate.postForEntity(url, httpEntity, ExternalResponseDto.class);
        log.info("Response received: " + response.getBody());
        return response.getBody();
    }

}
