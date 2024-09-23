package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.ManufacturerDocsRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.ManufacturerDocsResponseDto;
import com.beehyv.iam.manager.ManufacturerDocManager;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.model.ManufacturerDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ManufacturerDocServiceTest {
    @Mock
    private BaseMapper<ManufacturerDocsResponseDto, ManufacturerDocsRequestDto, ManufacturerDoc> mapper;

    @Mock
    private ManufacturerDocManager manufacturerDocManager;

    @InjectMocks
    private ManufacturerDocService manufacturerDocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
        @Test
        public void testCreate() {
            // Arrange
            ManufacturerDocsRequestDto requestDto = new ManufacturerDocsRequestDto();
            ManufacturerDoc manufacturerDoc = new ManufacturerDoc();

            // Mock the mapper
            when(mapper.toEntity(requestDto)).thenReturn(manufacturerDoc);
             when(manufacturerDocManager.create(any())).thenReturn(manufacturerDoc);
            // Act
            manufacturerDocService.create(requestDto);


        }*/
    @Test
    void testGetAllManufacturerDocs() {
        int pageNumber = 1;
        int pageSize = 10;
        List<ManufacturerDoc> manufacturerDocs = new ArrayList<>();
        when(manufacturerDocManager.findAll(pageNumber, pageSize)).thenReturn(manufacturerDocs);

        ListResponse<ManufacturerDocsResponseDto> response = manufacturerDocService.getALlManufacturerDocs(pageNumber, pageSize);

        verify(manufacturerDocManager).findAll(pageNumber, pageSize);
        assertEquals(manufacturerDocs.size(), response.getData().size());
    }

    @Test
    void testGetById() {
        Long manufacturerDocId = 1L;
        ManufacturerDoc manufacturerDoc = new ManufacturerDoc();
        when(manufacturerDocManager.findById(manufacturerDocId)).thenReturn(manufacturerDoc);

        ManufacturerDocsResponseDto responseDto = manufacturerDocService.getById(manufacturerDocId);

        verify(manufacturerDocManager).findById(manufacturerDocId);
        assertEquals(manufacturerDoc.getId(), responseDto.getId());
    }

//    @Test
//    void testUpdateManufacturerDoc() {
//        ManufacturerDocsRequestDto requestDto = new ManufacturerDocsRequestDto();
//        manufacturerDocService.updateManufacturerDoc(requestDto);
//        verify(manufacturerDocManager).update(any(ManufacturerDoc.class));
//    }

    @Test
    void testDeleteManufacturer() {
        Long manufacturerDocId = 1L;
        manufacturerDocService.deleteManufacturer(manufacturerDocId);
        verify(manufacturerDocManager).delete(manufacturerDocId);
    }
}
