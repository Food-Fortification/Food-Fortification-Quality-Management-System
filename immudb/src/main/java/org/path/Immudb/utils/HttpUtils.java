package org.path.Immudb.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class HttpUtils {

  private static WebClient webClient;

  @Autowired
  public void setWebClient(WebClient webClientBean) {
    webClient = webClientBean;
  }

  public static String callGetAPI(String url, String token) {
    log.info("Calling the get url: "+ url);
    if(token==null){
      return webClient
          .get()
          .uri(url)
          .retrieve()
          .bodyToMono(String.class)
          .block();
    }else {
      return WebClient.create()
          .get()
          .uri(url)
          .headers(h -> h.setBearerAuth(token))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    }

  }

  public static void callDeleteAPI(String url) {
    webClient.delete()
        .uri(url)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

}
