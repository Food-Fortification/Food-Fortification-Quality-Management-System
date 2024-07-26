package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.BatchRequestDto;
import com.beehyv.fortification.dto.requestDto.LotRequestDto;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.BatchListResponseDTO;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.helper.Constants;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.manager.BatchManager;
import com.beehyv.fortification.manager.LotManager;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.BatchMapper;
import com.beehyv.fortification.mapper.LotMapper;
import com.beehyv.fortification.service.InspectionService;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InspectionServiceImpl implements InspectionService {

    private final BaseMapper<BatchListResponseDTO, BatchRequestDto, Batch> batchListMapper = BaseMapper.getForListClass(BatchMapper.class);
    private final BaseMapper<LotListResponseDTO, LotRequestDto, Lot> lotListMapper = BaseMapper.getForListClass(LotMapper.class);

    private final BatchManager batchManager;
    private final LotManager lotManager;
    private final KeycloakInfo keycloakInfo;

    @Override
    public ListResponse<BatchListResponseDTO> getAllBatches(Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize) {
        String sampleState = this.getSampleStateFromSearchRequest(searchListRequest);
        List<Long> testManufacturerIds = this.getTestManufacturerIds();
        List<Batch> entities;
        if (sampleState.equalsIgnoreCase("ALL")) {
            entities = batchManager.findAllBatchesForInspection(categoryId, searchListRequest, testManufacturerIds, new ArrayList<>(), pageNumber, pageSize);
            Long count = batchManager.getCountForInspection(categoryId, searchListRequest, testManufacturerIds);
            return ListResponse.from(entities, batchListMapper::toListDTO, count);
        } else {
            ListResponse batchIdsListResponse = this.getEntityIdsFromLabForSampleState(StateType.BATCH, sampleState, categoryId, pageNumber, pageSize);
            List<Long> batchIds = ((List<Integer>) batchIdsListResponse.getData()).stream().map(Integer::longValue).toList();
            if (batchIds.isEmpty()) return new ListResponse<>();
            entities = batchManager.findAllBatchesForInspection(categoryId, searchListRequest, testManufacturerIds, batchIds, null, null);
            return ListResponse.from(entities, batchListMapper::toListDTO, batchIdsListResponse.getCount());
        }
    }


    @Override
    public ListResponse<LotListResponseDTO> getAllLots(Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize) {
        String sampleState = this.getSampleStateFromSearchRequest(searchListRequest);
        List<Long> testManufacturerIds = this.getTestManufacturerIds();
        List<Lot> entities;
        if (sampleState.equalsIgnoreCase("ALL")) {
            entities = lotManager.findAllLotsForInspection(categoryId, searchListRequest, testManufacturerIds, new ArrayList<>(), pageNumber, pageSize);
            Long count = lotManager.getCountForInspection(categoryId, searchListRequest, testManufacturerIds);
            return ListResponse.from(entities, lotListMapper::toListDTO, count);
        } else {
            ListResponse lotIdsListResponse = this.getEntityIdsFromLabForSampleState(StateType.LOT, sampleState, categoryId, pageNumber, pageSize);
            List<Long> lotIds = ((List<Integer>) lotIdsListResponse.getData()).stream().map(Integer::longValue).toList();
            if (lotIds.isEmpty()) return new ListResponse<>(null, new ArrayList<>());
            entities = lotManager.findAllLotsForInspection(categoryId, searchListRequest, testManufacturerIds, lotIds, null, null);
            return ListResponse.from(entities, lotListMapper::toListDTO, lotIdsListResponse.getCount());
        }
    }

    private String getSampleStateFromSearchRequest(SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if (searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        String sampleState;
        try {
            sampleState = ((List<String>) map.get("state")).get(0);
        } catch (Exception e) {
            sampleState = "";
        }

        if (sampleState != "" && sampleState != "ALL" && sampleState != null)
            return sampleState;
        return "ALL";
    }

    private List<Long> getTestManufacturerIds() {
        String url = Constants.IAM_SERVICE_URL + "/manufacturer/test-manufacturers";
        List<Integer> testManufacturerIds = (List<Integer>) IamServiceRestHelper.fetchResponse(url, List.class, keycloakInfo.getAccessToken());
        return testManufacturerIds.stream().mapToLong(Integer::longValue).boxed().toList();
    }

    private ListResponse getEntityIdsFromLabForSampleState(StateType type, String sampleState, Long categoryId, Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = new RestTemplate();
        UriTemplateHandler uriTemplateHandler = new UriTemplateHandler() {
            @Override
            public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriTemplate);
                URI uri = builder.buildAndExpand(uriVariables).toUri();
                return uri;
            }

            @Override
            public URI expand(String uriTemplate, Object... uriVariables) {
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriTemplate);
                URI uri = builder.buildAndExpand(uriVariables).toUri();
                return uri;
            }
        };
        restTemplate.setUriTemplateHandler(uriTemplateHandler);
        String url = Constants.LAB_SERVICE_URL + "inspection/samples/" + type.name().toLowerCase() + "/category/" + categoryId;
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sampleState", sampleState)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize)
                .build();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(keycloakInfo.getAccessToken());
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<ListResponse> response = null;
        try {
            log.info(String.format("Url for calling lab api to get Entity ids: %s", builder.toUriString()));
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<ListResponse>() {
                    });
            log.info("Response from lab for entity ids: {}", response.getBody());
        } catch (Exception ex) {
            log.error(String.format("Error at calling lab api for entity ids: %s", ex.getMessage()));
            ex.printStackTrace();
            return null;
        }
        return response.getBody();
    }
}
