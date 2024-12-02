package org.path.broadcast.utils;

import org.path.parent.exceptions.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class HttpUtils {

    private static WebClient webClient;

    public static <T> T callGetAPI(String url, Class<T> tClass) throws JsonProcessingException {
        log.info("Calling the get url: " + url);
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(tClass)
                .block();
    }

    public static void callPostApi(String url, String body) {
        log.info("Calling the post url: " + url);
        webClient
                .post()
                .uri(url)
                .body(Mono.just(body), new ParameterizedTypeReference<String>() {
                })
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(CustomException::new))
                .toBodilessEntity()
                .block();
    }

    @Autowired
    public void setWebClient(WebClient webClientBean) {
        webClient = webClientBean;
    }

}
