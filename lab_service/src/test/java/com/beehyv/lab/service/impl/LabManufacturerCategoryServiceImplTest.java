package com.beehyv.lab.service.impl;
import com.beehyv.lab.dto.requestDto.LabManufacturerRequestDTO;
import com.beehyv.lab.dto.responseDto.LabListResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.Lab;
import com.beehyv.lab.entity.LabManufacturerCategoryMapping;
import com.beehyv.lab.manager.LabManufacturerCategoryManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.impl.LabManufacturerCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LabManufacturerCategoryServiceImplTest {

    @Mock
    private LabManufacturerCategoryManager labManufacturerCategoryManager;

    private DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    @InjectMocks
    private LabManufacturerCategoryServiceImpl labManufacturerCategoryService;

    @BeforeEach
    void setUp() {
        labManufacturerCategoryService = new LabManufacturerCategoryServiceImpl(labManufacturerCategoryManager);
    }

    @Test
    public void testCreate() {
        LabManufacturerRequestDTO labManufacturerRequestDTO = new LabManufacturerRequestDTO(
                null,
                1L,
                1L,
                1L
        );

        labManufacturerCategoryService.create(labManufacturerRequestDTO);

        Mockito.verify(labManufacturerCategoryManager, Mockito.times(1)).create(any(LabManufacturerCategoryMapping.class));
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        labManufacturerCategoryService.delete(id);

        Mockito.verify(labManufacturerCategoryManager, Mockito.times(1)).delete(id);
    }

    @Test
    public void testGetLabsByManufacturerId() {
        String search = "test";
        Long manufacturerId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;

        List<LabManufacturerCategoryMapping> labManufacturerCategoryMappingList = new ArrayList<>();
        LabManufacturerCategoryMapping mapping = new LabManufacturerCategoryMapping();
        Lab lab = new Lab();
        lab.setId(1L);
        lab.setName("Lab1");
        mapping.setLab(lab);
        labManufacturerCategoryMappingList.add(mapping);

        Mockito.when(labManufacturerCategoryManager.findAllLabsByManufacturerIdAndCategoryId(search, manufacturerId, null, pageNumber, pageSize))
                .thenReturn(labManufacturerCategoryMappingList);
        Mockito.when(labManufacturerCategoryManager.getCountForLabsByManufacturerIdAndCategoryId(search, manufacturerId, null))
                .thenReturn(1L);

        ListResponse<LabListResponseDTO> response = labManufacturerCategoryService.getLabsByManufacturerId(search, manufacturerId, pageNumber, pageSize);

        assertEquals(1, response.getData().size());
        assertEquals("Lab1", response.getData().get(0).getName());
    }
}
