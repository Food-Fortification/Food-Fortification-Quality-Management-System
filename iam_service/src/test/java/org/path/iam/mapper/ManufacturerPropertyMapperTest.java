package org.path.iam.mapper;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.path.iam.config.EncryptionFieldConfig;
import org.path.iam.dto.requestDto.ManufacturerPropertyRequestDto;
import org.path.iam.dto.responseDto.ManufacturerPropertyResponseDto;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.model.ManufacturerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.path.parent.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class ManufacturerPropertyMapperTest {

    private ManufacturerPropertyMapper manufacturerPropertyMapper;


    @BeforeEach
    void setUp() {
        manufacturerPropertyMapper = new ManufacturerPropertyMapper();
    }

    private void withMocks(Runnable testLogic) {
        try (MockedStatic<EncryptionHelper> mockedStatic = mockStatic(EncryptionHelper.class);
             MockedStatic<EncryptionFieldConfig> mockedStatic1 = mockStatic(EncryptionFieldConfig.class)) {

            mockedStatic.when(() -> EncryptionHelper.encrypt(Mockito.anyString())).thenReturn("Encrypted String");
            mockedStatic.when(() -> EncryptionHelper.decrypt(Mockito.anyString())).thenReturn("Decrypted String");
            mockedStatic1.when(EncryptionFieldConfig::getFieldsToEncrypt)
                    .thenReturn(Arrays.asList("mobileNumber", "emailId", "Test Property"));

            // Execute test logic
            testLogic.run();
        }
    }

    @Test
    void testToDto() {
        withMocks(() -> {
            // Create ManufacturerProperty object
            ManufacturerProperty manufacturerProperty = new ManufacturerProperty();
            manufacturerProperty.setId(1L);
            manufacturerProperty.setName("Test Property");
            manufacturerProperty.setValue("Test Value");

            // Perform mapping
            ManufacturerPropertyResponseDto responseDto = manufacturerPropertyMapper.toDto(manufacturerProperty);

            // Assertions
            assertEquals(1L, responseDto.getId());
            assertEquals("Decrypted String", responseDto.getValue());
        });
    }

    @Test
    void testToEntity() {
        withMocks(() -> {
            // Create ManufacturerPropertyRequestDto object
            ManufacturerPropertyRequestDto requestDto = new ManufacturerPropertyRequestDto();
            requestDto.setManufacturerId(1L);
            requestDto.setId(1L);
            requestDto.setName("Test Property");
            requestDto.setValue("Test Value");

            // Perform mapping
            ManufacturerProperty entity = manufacturerPropertyMapper.toEntity(requestDto);

            // Assertions
            assertEquals(1L, entity.getId());
            assertEquals("Decrypted String", entity.getValue());
            assertEquals(1L, entity.getManufacturer().getId());
        });
    }


}
