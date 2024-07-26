package com.beehyv.fortification.helper;

import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dto.iam.DistrictResponseDto;
import com.beehyv.fortification.dto.iam.NameAddressResponseDto;
import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.ManufacturerEmpanelRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.enums.DashboardExcelReportType;
import com.beehyv.fortification.enums.GeoAggregationType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class IamServiceRestHelper {
    private static final Logger log = LoggerFactory.getLogger(IamServiceRestHelper.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    public static Map<String, Map<String, String>> getNameAndAddress(List<Long> ids, String token) {
        String url = Constants.IAM_SERVICE_URL + "/manufacturer/list/details";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ids", ids)
                .build();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<List<NameAddressResponseDto>> response = null;
        try {
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<List<NameAddressResponseDto>>() {
                    });
        } catch (Exception ex) {
            log.error("error at calling rest api");
        }
        List<NameAddressResponseDto> nameAddressResponseDtoList = response.getBody();
        Map<String, Map<String, String>> result = new HashMap<>();
        nameAddressResponseDtoList.forEach(n -> {
            Map<String, String> nameAddressMap = new HashMap<>();
            nameAddressMap.put("name", n.getName());
            nameAddressMap.put("address", n.getCompleteAddress());
            nameAddressMap.put("licenseNo", n.getLicenseNo());
            result.put(n.getId().toString(), nameAddressMap);
        });
        return result;
    }

    public static Map<Long, ManufacturerListDashboardResponseDto> getManufacturersWithGeoFilter(Long categoryId, GeoAggregationType type, String geoId, String search, Integer pageNumber, Integer pageSize, String token) {
        String url = Constants.IAM_SERVICE_URL + "/manufacturers/list";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("categoryId", categoryId)
                .queryParam("type", type.name())
                .queryParam("geoId", geoId)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize)
                .queryParam("search", search)
                .build();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<ListResponse<ManufacturerListDashboardResponseDto>> response = null;
        try {
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<ListResponse<ManufacturerListDashboardResponseDto>>() {
                    });
        } catch (Exception ex) {
            log.error("Error at calling rest api: {}", ex.getMessage());
        }
        if (response == null || response.getBody() == null) return new HashMap<>();
        return Objects.requireNonNull(response.getBody()).getData()
                .stream().collect(Collectors.toMap(ManufacturerListDashboardResponseDto::getId, d -> d, (d1, d2) -> d1));
    }

    public static AddressResponseDto getManufacturerAddress(Long manufacturerId, String token) {
        String url = Constants.IAM_SERVICE_URL + "address/manufacturer/" + manufacturerId;
        return fetchResponse(url, AddressResponseDto.class, token);
    }

    public static HttpStatus addKeycloakRoles(List<String> roles, String token) {


        String url = Constants.IAM_SERVICE_URL + "role/create-roles";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(roles, requestHeaders);
        ResponseEntity<Map<Long, List<Long>>> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Map<Long, List<Long>>>() {
        });
        return response.getStatusCode();
    }

    public static List<DistrictResponseDto> getAllDistrictsByStateGeoId(String stateGeoId, String token) {
        String url = Constants.IAM_SERVICE_URL + "district/stateGeoId/" + stateGeoId;

        DistrictResponseDto[] districtArray = fetchResponse(url, DistrictResponseDto[].class, token);

        List<DistrictResponseDto> districtList = List.of(districtArray);

        return districtList;
    }

    public static <T> T fetchResponse(String url, Class<T> tClass, String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, tClass);
        return response.getBody();
    }

    public static <T> T fetchResponse(String url, ParameterizedTypeReference<T> responseType, String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return response.getBody();
    }

    public static Map<Long, ManufacturerAgencyResponseDto> getManufacturerAgenciesByIds(Long categoryId, GeoAggregationType type, String geoId, List<Long> mids, String token) {
        String url = Constants.IAM_SERVICE_URL + "manufacturer/list";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("categoryId", categoryId)
                .queryParam("type", type.name())
                .queryParam("geoId", geoId)
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(mids, requestHeaders);
        ResponseEntity<List<ManufacturerAgencyResponseDto>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<ManufacturerAgencyResponseDto>>() {
        });
        return Objects.requireNonNull(response.getBody())
                .stream().collect(Collectors.toMap(ManufacturerAgencyResponseDto::getId, d -> d, (d1, d2) -> d1));
    }

    public static Map<Long, ManufacturerAgencyResponseDto> getManufacturerNamesByIds(List<Long> manufacturerIds, String token) {
        String url = Constants.IAM_SERVICE_URL + "manufacturer/list/manufacturer-name";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(manufacturerIds, requestHeaders);
        ResponseEntity<List<ManufacturerAgencyResponseDto>> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<ManufacturerAgencyResponseDto>>() {
        });
        return Objects.requireNonNull(response.getBody()).stream()
                .collect(
                        Collectors.toMap(
                                ManufacturerAgencyResponseDto::getId,
                                dto -> dto
                        )
                );
    }

    public static Map<Long, ManufacturerAgencyResponseDto> getManufacturerNamesByIdsAndCategoryId(List<Long> manufacturerIds, Long categoryId, String token) {
        String url = Constants.IAM_SERVICE_URL + "manufacturer/list/manufacturer-name/category/" + categoryId;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(manufacturerIds, requestHeaders);
        ResponseEntity<List<ManufacturerAgencyResponseDto>> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<ManufacturerAgencyResponseDto>>() {
        });
        return Objects.requireNonNull(response.getBody()).stream()
                .collect(
                        Collectors.toMap(
                                ManufacturerAgencyResponseDto::getId,
                                dto -> dto
                        )
                );
    }

    public static void updateDispatchedQuantity(String url, String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        restTemplate.put(url, httpEntity);
    }

    public static List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantities(String token, DashboardRequestDto dto, List<String> districtGeoIds, Long sourceManufacturerId,
                                                                                              DashboardExcelReportType type, List<Long> sourceManufacturerIds, List<Long> targetManufacturerIds) {
        String url = Constants.IAM_SERVICE_URL + "purchase-order/districtGeoId/total-quantity";

        Map jsonData = new HashMap();
        jsonData.put("fromDate", dto.getFromDate());
        jsonData.put("toDate", dto.getToDate());
        jsonData.put("districtGeoIds", districtGeoIds);
        jsonData.put("manufacturerId", sourceManufacturerId);
        jsonData.put("categoryId", dto.getCategoryId());
        jsonData.put("type", type);
        jsonData.put("sourceManufacturerIds", sourceManufacturerIds);
        jsonData.put("targetManufacturerIds", targetManufacturerIds);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(jsonData), requestHeaders);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ManufacturerDispatchableQuantityResponseDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<ManufacturerDispatchableQuantityResponseDto>>() {
        });
        return response.getBody();
    }

    public static Map<String, LocationResponseDto> getDistrictByGeoIds(List<String> geoIds, String token) {

        String url = Constants.IAM_SERVICE_URL + "/district/geoIds";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(geoIds), requestHeaders);
        } catch (Exception e) {
            e.printStackTrace();
        }


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<LocationResponseDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<LocationResponseDto>>() {
        });

        if (response == null || response.getBody() == null) return new HashMap<>();
        return Objects.requireNonNull(response.getBody())
                .stream().collect(Collectors.toMap(LocationResponseDto::getGeoId, d -> d, (d1, d2) -> d1));
    }

    public static String getDistrictGeoIdByManufacturerId(Long manufacturerId, String token) {
        String url = Constants.IAM_SERVICE_URL + "manufacturer/" + manufacturerId + "/districtGeoId";
        return fetchResponse(url, String.class, token);
    }

    public static String getActionNameByManaufacturersIdandCategoryId(Long manufacturerId, Long categoryId, String token){
       try {String url = Constants.IAM_SERVICE_URL + "manufacturer-category/" + "action-name?manufacturerId=" + manufacturerId + "&categoryId=" + categoryId;
           String actionName = fetchResponse(url, String.class, token);
           return actionName;
       }
       catch (Exception exception){
           return null;
       }
    }

    public static Map<Long, List<Long>> getAllEmpanelManufacturersByCategory(DashboardRequestDto dto, Long sourceCategoryId, Long targetCategoryId, String token, String stateGeoId) {

        ManufacturerEmpanelRequestDto empanelRequestDto = new ManufacturerEmpanelRequestDto();
        empanelRequestDto.setSourceCategoryId(sourceCategoryId);
        empanelRequestDto.setTargetCategoryId(targetCategoryId);
        empanelRequestDto.setFromDate(dto.getFromDate());
        empanelRequestDto.setToDate(dto.getToDate());
        empanelRequestDto.setStateGeoId(stateGeoId);

        String url = Constants.IAM_SERVICE_URL + "empanel/manufacturers";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(empanelRequestDto, requestHeaders);
        ResponseEntity<Map<Long, List<Long>>> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Map<Long, List<Long>>>() {
        });
        return response.getBody();
    }
}
