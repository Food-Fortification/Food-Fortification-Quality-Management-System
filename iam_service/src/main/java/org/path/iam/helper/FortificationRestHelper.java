package org.path.iam.helper;

import org.path.iam.dto.responseDto.CategoryResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FortificationRestHelper {
    public static List<Long> fetchResponse(String url, String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<List<Long>> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity, new ParameterizedTypeReference<List<Long>>() {});
        return response.getBody();
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
}
