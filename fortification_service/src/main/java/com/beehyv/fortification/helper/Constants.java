package com.beehyv.fortification.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class Constants {
    public static String FORTIFICATION_BATCH_QR_CODE = "FORTIFICATION_BATCH_QR_CODE";
    public static String FORTIFICATION_LOT_QR_CODE = "FORTIFICATION_LOT_QR_CODE";

    @Value("${FORTIFICATION_UI_BASE_URL}")
    public String FORTIFICATION_UI_BASE_URL;

    public static String LAB_SERVICE_URL;
    public static String IAM_SERVICE_URL;

    public static String QRCODE_UI_URL;
    @Value("${IAM_BASE_URL}")
    public String IAM_BASE_URL;
    @Value("${LAB_BASE_URL}")
    public String LAB_BASE_URL;

// TODO :: delete commented
//    public static Map<String, String> categoryNamePrefixMap = Map.of(
//            "PREMIX", "P",
//            "FRK", "F",
//            "MILLER", "R"
//    );
    public static Map<String, List<String>> sampleStateMap = Map.of(
            "toReceive", List.of("Receive Sample"),
            "inProgress", List.of("Submit Test Reports", "Reject Sample without testing")
    );

    public static String getCategoryNamePrefix(String categoryName) {
//         return Optional.ofNullable(categoryNamePrefixMap.get(categoryName.toUpperCase())).orElse("M");
        return Optional.of(categoryName.toUpperCase().substring(0, 1)).orElse("M");
    }

    @PostConstruct
    public void setProperties() {
        LAB_SERVICE_URL = LAB_BASE_URL + "lab/";
        QRCODE_UI_URL = FORTIFICATION_UI_BASE_URL;
        IAM_SERVICE_URL = IAM_BASE_URL + "iam/";

    }
}

