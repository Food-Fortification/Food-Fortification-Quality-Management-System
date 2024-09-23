package com.beehyv.lab.helper;

import com.beehyv.lab.dto.responseDto.CategoryResponseDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class RestHelper {
    public Boolean checkAccess(String url, String token){
        return this.fetchResponse(url, Boolean.class, token);
    }

    public List<CategoryResponseDto> getCategories(String url, String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<List<CategoryResponseDto>> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<List<CategoryResponseDto>>() {}
                );
        return responseEntity.getBody();
    }

    public static ListResponse<CategoryResponseDto> fetchCategoryListResponse(String url, String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<ListResponse<CategoryResponseDto>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ListResponse<CategoryResponseDto>>() {
                });
        return response.getBody();
    }

    public <T> T fetchResponse(String url, Class<T> tClass, String token){
        log.info("Calling get api for url: " + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity, tClass);
        log.info("Response received: "+response.getBody().toString());
        return response.getBody();
    }

    public <T> void updateData(String url, T requestBody, String token){
        log.info("Calling put api for url: " + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity;
        if (requestBody != null)
            httpEntity = new HttpEntity<>(requestBody,requestHeaders);
        else
            httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,httpEntity, String.class);
        log.info("Response received: " + response.getBody());
    }

    public <T> T postApi(String url, Object requestBody, Class<T> tClass, String token){
        log.info("Calling post api for url: " + url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity, tClass);
        log.info("Response received: "+response.getBody().toString());
        return response.getBody();
    }

}
