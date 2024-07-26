package com.beehyv.iam.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FunctionUtilsTest {

    @Mock
    private JavaMailSender javaMailSender;


    @Test
    void testGetDistrictName() {
        String fssaiDistrictName = "district1";

        String districtName = FunctionUtils.getDistrictName(fssaiDistrictName);

        assertNull(districtName);
    }

    @Test
    void testRandomPasswordGenerator() {
        // Act
        String password = FunctionUtils.randomPasswordGenerator(8);


    }
}
