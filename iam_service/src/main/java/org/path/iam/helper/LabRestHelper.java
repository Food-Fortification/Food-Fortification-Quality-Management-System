package org.path.iam.helper;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LabRestHelper {

  public static List<Long> fetchCategoryIdsByLabId (String url, String token){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setBearerAuth(token);
    HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
    ResponseEntity<List<Long>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<List<Long>>() {
        });
    return response.getBody();
  }

}
