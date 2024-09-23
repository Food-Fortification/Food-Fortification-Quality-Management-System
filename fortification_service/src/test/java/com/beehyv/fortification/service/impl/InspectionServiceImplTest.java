package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.BatchListResponseDTO;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.manager.BatchManager;
import com.beehyv.fortification.manager.LotManager;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InspectionServiceImplTest {

    @Mock
    private BatchManager batchManager;

    @Mock
    private LotManager lotManager;

    @Mock
    private UriComponents uriComponents;

    @Mock
    private UriComponentsBuilder uriComponentsBuilder;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private RestTemplate restTemplate;

    @Spy
    @InjectMocks
    private InspectionServiceImpl inspectionService;

    @Mock
    private UriTemplateHandler uriTemplateHandler;

    private MockedStatic<RestTemplate> mockStaticRest;
    private MockedStatic<IamServiceRestHelper> mockStatic;
    private MockedStatic<UriComponentsBuilder> uriComponentsBuilderMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStatic = mockStatic(IamServiceRestHelper.class);
        uriComponentsBuilderMockedStatic = Mockito.mockStatic(UriComponentsBuilder.class);
    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
        uriComponentsBuilderMockedStatic.close();
    }

    @Test
    void testGetAllBatches_SampleStateAll() {
        // Arrange
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 0;
        Integer pageSize = 10;
        List<Batch> batches = List.of(new Batch(), new Batch());
        Long count = 2L;

        when(batchManager.findAllBatchesForInspection(anyLong(), any(SearchListRequest.class), anyList(), anyList(), any(Integer.class), any(Integer.class)))
                .thenReturn(batches);
        when(batchManager.getCountForInspection(anyLong(), any(SearchListRequest.class), anyList()))
                .thenReturn(count);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(List.of(1, 2));
        // Act
        ListResponse<BatchListResponseDTO> response = inspectionService.getAllBatches(categoryId, searchListRequest, pageNumber, pageSize);

        // Assert
        assertNotNull(response);
    }

//    @Test
//    void testGetAllBatches_SampleStateNotAll() {
//        // Arrange
//        Long categoryId = 1L;
//        SearchListRequest searchListRequest = new SearchListRequest();
//        searchListRequest.setSearchCriteriaList(Arrays.asList(new SearchCriteria("state",Arrays.asList("value"))));
//        Integer pageNumber = 0;
//        Integer pageSize = 10;
//        String sampleState = "PENDING";
//        List<Integer> batchIds = List.of(1, 2, 3);
//        List<Batch> batches = List.of(new Batch(), new Batch());
//        Long count = 2L;
//
//        mockStaticRest = mockStatic(RestTemplate.class);
////        when(RestTemplate.).thenReturn(restTemplate);
//        ListResponse<Integer> labResponse = new ListResponse<>(count,batchIds);
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(ListResponse.class)))
//                .thenReturn(new ResponseEntity<>(labResponse, HttpStatus.OK));
//        when(batchManager.findAllBatchesForInspection(anyLong(), any(SearchListRequest.class), anyList(), anyList(), isNull(), isNull()))
//                .thenReturn(batches);
//        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(),any())).thenReturn(List.of(1,2));
//
//        Map<Long, LabNameAddressResponseDto> labMap = new HashMap<>();
//        when(restTemplate.exchange(any(), any(),any(), (Class<Object>) any())).thenReturn(new ResponseEntity<>(labMap, HttpStatus.OK));
//        when(restTemplate.exchange(any(), any(),any(), (ParameterizedTypeReference<Object>) any())).thenReturn(new ResponseEntity<>(labMap, HttpStatus.OK));
////        when(restTemplate.execute(any(), any(),any(),any())).thenReturn(new ResponseEntity<>(labMap, HttpStatus.OK));
////        when(restTemplate.getUriTemplateHandler()).thenReturn(uriTemplateHandler);
////        when(uriTemplateHandler.expand(any(), (Object) any())).thenReturn(null);
////        when(restTemplate.doe)
//
////        doReturn(labResponse).when(inspectionService)
////                .getEntityIdsFromLabForSampleState(any(), sampleState, categoryId, pageNumber, pageSize);
//
//        UriComponentsBuilder uriComponentsBuilderSpy = Mockito.spy(UriComponentsBuilder.class);
//        when(UriComponentsBuilder.fromHttpUrl(Mockito.anyString())).thenReturn(uriComponentsBuilderSpy);
//        when(UriComponentsBuilder.fromHttpUrl(any(String.class))).thenReturn(uriComponentsBuilder);
//        when(uriComponentsBuilder.queryParam(any(), anyCollection())).thenReturn(uriComponentsBuilder);
//        when(uriComponentsBuilder.queryParam(any(), (Object) any())).thenReturn(uriComponentsBuilder);
//        when(uriComponentsBuilder.build()).thenReturn(uriComponents);
//        when(uriComponents.toUriString()).thenReturn("https://example.com/api/endpoint");
//
//        // Act
//        ListResponse<BatchListResponseDTO> response = inspectionService.getAllBatches(categoryId, searchListRequest, pageNumber, pageSize);
//
//        // Assert
//        assertNotNull(response);
//        mockStaticRest.close();
//    }

    @Test
    void testGetAllLots_SampleStateAll() {
        // Arrange
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 0;
        Integer pageSize = 10;
        List<Lot> lots = List.of(new Lot(), new Lot());
        Long count = 2L;

        when(lotManager.findAllLotsForInspection(anyLong(), any(SearchListRequest.class), anyList(), anyList(), any(Integer.class), any(Integer.class)))
                .thenReturn(lots);
        when(lotManager.getCountForInspection(anyLong(), any(SearchListRequest.class), anyList()))
                .thenReturn(count);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(List.of(1, 2));


        // Act
        ListResponse<LotListResponseDTO> response = inspectionService.getAllLots(categoryId, searchListRequest, pageNumber, pageSize);

        // Assert
        assertNotNull(response);
    }

    @Test
    void testGetAllLots_SampleStateNotAll() {
        // Arrange
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sampleState = "PENDING";
        List<Integer> lotIds = List.of(1, 2, 3);
        List<Lot> lots = List.of(new Lot(), new Lot());
        Long count = 2L;

        ListResponse<Integer> labResponse = new ListResponse<>(count, lotIds);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(ListResponse.class)))
                .thenReturn(new ResponseEntity<>(labResponse, HttpStatus.OK));
        when(lotManager.findAllLotsForInspection(anyLong(), any(SearchListRequest.class), anyList(), anyList(), isNull(), isNull()))
                .thenReturn(lots);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(List.of(1, 2));

        ListResponse<LotListResponseDTO> response = inspectionService.getAllLots(categoryId, searchListRequest, pageNumber, pageSize);

        assertNotNull(response);

    }
}