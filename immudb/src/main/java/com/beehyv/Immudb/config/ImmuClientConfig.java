package com.beehyv.Immudb.config;

import io.codenotary.immudb4j.ImmuClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class ImmuClientConfig {
    @Value("${immudb.address}")
    private String immudbAddr;

    @Value("${immudb.port}")
    private int immudbPort;

    @Bean
    ImmuClient setImmuClient() throws IOException {
        ImmuClient client = null;
        try {
            client = ImmuClient.newBuilder()
                    .withServerUrl(immudbAddr)
                    .withServerPort(immudbPort)
                    .build();
            log.info("Built immudb client");
        } catch (Exception e) {
            log.error("Exception while initialising Immuclient ", e);
            throw e;
        }
        return client;
    }
}
