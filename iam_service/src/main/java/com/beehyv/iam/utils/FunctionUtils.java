package com.beehyv.iam.utils;

import com.beehyv.iam.model.Manufacturer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.beehyv.iam.dto.responseDto.FssaiDistrictJsonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FunctionUtils {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    static char[] SYMBOLS = "%@#$^".toCharArray();
    static char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static char[] NUMBERS = "0123456789".toCharArray();

    public static List<FssaiDistrictJsonDto> getDistrictList() {
        TypeReference<List<FssaiDistrictJsonDto>> typeReference = new TypeReference<List<FssaiDistrictJsonDto>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/FssaiDistrictMap.json");
        try {
            return new ObjectMapper().readValue(inputStream, typeReference);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static String getDistrictName(String fssaiDistrictName) {
        List<FssaiDistrictJsonDto> districtJsonDtoList = getDistrictList();
        for (FssaiDistrictJsonDto districtJsonDto : districtJsonDtoList) {
            if (districtJsonDto.getFssaiDistrictName().equals(fssaiDistrictName)) {
                return districtJsonDto.getDistrictName();
            }
        }
        return null; // Return null if key not found
    }


    public void sendMail(String recipient, Manufacturer manufacturer, String username, String password)
    {
        try {
            String message = "Manufacturer is created with username - %s and password - %s.";
            message = String.format(message, username, password);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(message);
            mailMessage.setSubject("Manufacturer Details");

            javaMailSender.send(mailMessage);
        }
        catch (Exception e) {
            //TODO: save in db
        }
    }

    public static String randomPasswordGenerator(int length){
        char[] password = new char[length];
        Random rand = new Random();
        password[0] = UPPERCASE[rand.nextInt(UPPERCASE.length)];
        password[1] = SYMBOLS[rand.nextInt(SYMBOLS.length)];
        password[2] = NUMBERS[rand.nextInt(NUMBERS.length)];

        //populate rest of the password with random chars
        for (int i = 3; i < length; i++) {
            password[i] = LOWERCASE[rand.nextInt(LOWERCASE.length)];
        }

        return password.toString();
    }


}
