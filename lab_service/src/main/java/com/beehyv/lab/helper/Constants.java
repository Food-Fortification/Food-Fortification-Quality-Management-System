package com.beehyv.lab.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Constants {
    @Value("${FORTIFICATION_BASE_URL}")
    public String fortificationUrl;

    @Value("${IAM_BASE_URL}")
    public String iamUrl;

    public static String FORTIFICATION_BASE_URL;
    public static String IAM_BASE_URL;

    @PostConstruct
    public void setProperties(){
        FORTIFICATION_BASE_URL = fortificationUrl;
        IAM_BASE_URL = iamUrl;
    }

}
