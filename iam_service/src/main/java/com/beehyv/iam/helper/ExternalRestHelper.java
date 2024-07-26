package com.beehyv.iam.helper;

import com.beehyv.iam.dto.external.FssaiLicenseResponseDto;
import com.beehyv.parent.exceptions.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ExternalRestHelper {

    public static void callFssaiPostApi(String url, String apiAccessKey, String requestID){
        Map jsonData = new HashMap();
        jsonData.put("requestID", requestID);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("API-ACCESS-KEY", apiAccessKey);
        requestHeaders.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Request id for calling fssai api is "+requestID+ " access key is "+apiAccessKey);
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(jsonData), requestHeaders);
        } catch (Exception e) {
            log.error("Error at calling fssai auth api with message : {}",e.getMessage());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("Response from fssai auth api is : {}",response);
        if(response.getStatusCodeValue() != 200){
            throw new CustomException("Error occurred in calling Fssai API", HttpStatus.BAD_REQUEST);
        }
    }

    public static List<FssaiLicenseResponseDto> validateFssaiLicenseNo(String url){

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Content-Type", "application/json");

        log.info("Calling fssai api for license: " + url);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<FssaiLicenseResponseDto>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<FssaiLicenseResponseDto>>() {
            });
            log.info("Response from fssai auth api is : {}",response);
            if(Objects.requireNonNull(response.getBody()).isEmpty()){
                throw new CustomException("Manufacturer doesn't exist for the license number!", HttpStatus.BAD_REQUEST);
            }
            return response.getBody();
        }
        catch (Exception e){
            throw new CustomException("Error occurred in calling Fssai API", HttpStatus.BAD_REQUEST);
        }

    }
}
