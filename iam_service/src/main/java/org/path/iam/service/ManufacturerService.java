package org.path.iam.service;

import org.path.iam.config.KeycloakCustomConfig;
import org.path.iam.dto.requestDto.ManufacturerRequestDto;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.responseDto.*;
import org.path.iam.enums.EntityType;
import org.path.iam.enums.GeoType;
import org.path.iam.enums.ManufacturerType;
import org.path.iam.enums.UserType;
import org.path.iam.manager.*;
import org.path.iam.model.*;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.helper.FortificationRestHelper;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.ManufacturerMapper;
import org.path.iam.utils.DtoMapper;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManufacturerService {
    private final ManufacturerManager manufacturerManager;
    private final BaseMapper<ManufacturerResponseDto, ManufacturerRequestDto, Manufacturer> manufacturerMapper = BaseMapper.getForClass(ManufacturerMapper.class);
    private final DtoMapper dtoMapper;
    private final KeycloakInfo keycloakInfo;
    private final UserManager userManager;
    private final KeycloakCustomConfig keycloakCustomConfig;
    private final AddressService addressService;
    private final ManufacturerCategoryManager manufacturerCategoryManager;
    private final StatusManager statusManager;
    private final StateLabTestAccessManager stateLabTestAccessManager;
    private final DistrictManager districtManager;
    private final StateManager stateManager;
    private final VillageManager villageManager;
    private final CountryManager countryManager;


    @Value("${config.realm.resource}")
    private String realmResource;
    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;

    public Manufacturer create(ManufacturerRequestDto manufacturerRequestDto, String accessToken){
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerRequestDto);
        if (manufacturer.getStatus()==null){
            Status status = statusManager.findByName("Active");
            manufacturer.setStatus(status);
        }
        String fortificationUrl = fortificationBaseUrl + "fortification/category";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(fortificationUrl)
                .queryParam("independentBatch", false)
                .build();
        ListResponse<CategoryResponseDto> response = FortificationRestHelper.fetchCategoryListResponse(builder.toUriString(), accessToken == null?keycloakInfo.getAccessToken():accessToken);
        boolean warehouseCategoryMatch = response.getData().stream()
                .anyMatch(c -> manufacturer.getManufacturerCategories().stream()
                        .anyMatch(mc -> mc.getCategoryId().equals(c.getId())));
        if (warehouseCategoryMatch && (manufacturerRequestDto.getIsRawMaterialsProcured()!=null)){
            manufacturer.setIsRawMaterialsProcured(manufacturerRequestDto.getIsRawMaterialsProcured());
        }else {
            manufacturer.setIsRawMaterialsProcured(false);
        }
       return manufacturerManager.create(manufacturer);
    }


    public Long createManufacturer(ManufacturerRequestDto manufacturerRequestDto){
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        if (manufacturerRequestDto.getAddress().getVillageId()==null){
          manufacturerRequestDto =  this.createMaterialManufacturerWithNewCountry(manufacturerRequestDto);
        }
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerRequestDto);
        if (manufacturer.getStatus()==null){
            Status status = statusManager.findByName("Active");
            manufacturer.setStatus(status);
        }
        if(manufacturer.getManufacturerCategories() == null || manufacturer.getManufacturerCategories().isEmpty()) {
            throw new CustomException("Manufacturer categories should not be empty", HttpStatus.BAD_REQUEST);
        }
        manufacturer.setManufacturerType(ManufacturerType.MATERIAL);
        manufacturer = manufacturerManager.create(manufacturer);
        Manufacturer currentManufacturer = manufacturerManager.findById(manufacturerId);
        if(currentManufacturer.getSourceManufacturers() == null) {
            currentManufacturer.setSourceManufacturers(new HashSet<>());
        }
        currentManufacturer.getSourceManufacturers().add(manufacturer);
        manufacturerManager.update(currentManufacturer);
       return manufacturer.getId();
    }

    public ManufacturerRequestDto createMaterialManufacturerWithNewCountry(ManufacturerRequestDto dto){
        String countryName = dto.getAddress().getCountryName();
        String stateName = dto.getAddress().getStateName();
        String districtName = dto.getAddress().getDistrictName();
        String villageName = dto.getAddress().getVillageName();
        Country country = countryManager.findByName(countryName.trim().toLowerCase());
        State state = null;
        District district = null;
        Village village = null;
        if (country == null){
            country = countryManager.create(new Country(null, countryName, null, null, null));
            state = stateManager.create(new State(null, stateName, country, null, null, null));
            district = districtManager.create(new District(null, districtName, null, state, null, null));
            village = villageManager.create(new Village(null,villageName, district, null, null, null));
        }else {
            state = stateManager.findByNameAndCountryId(stateName.trim().toLowerCase(), country.getId());
            if (state == null){
                state = stateManager.create(new State(null, stateName, country, null, null, null));
                district = districtManager.create(new District(null, districtName, null, state, null, null));
                village = villageManager.create(new Village(null,villageName, district, null, null, null));
            }else {
                district = districtManager.findByNameAndStateId(districtName.trim().toLowerCase(), state.getId());
                if (district == null){
                    district = districtManager.create(new District(null, districtName, null, state, null, null));
                    village = villageManager.create(new Village(null,villageName, district, null, null, null));
                }else {
                    village = villageManager.findByNameAndDistrictId(villageName.trim().toLowerCase(), district.getId());
                    if (village == null){
                        village = villageManager.create(new Village(null,villageName, district, null, null, null));
                    }
                }
            }
        }
        dto.getAddress().setVillageId(village.getId());
        return dto;
    }
    public ListResponse<ManufacturerResponseDto> getALlManufacturers(String search, Integer pageNumber,Integer pageSize){
        List<Manufacturer> entities = manufacturerManager.findAllManufacturers(search,pageNumber,pageSize);
        Long count = manufacturerManager.getCountForAllManufacturers(search);
        ListResponse<ManufacturerResponseDto> result = ListResponse.from(entities, manufacturerMapper::toDto, count);
        List<ManufacturerResponseDto> list = result.getData().stream()
                .peek(m -> m.setManufacturerAttributes(new HashSet<>())).toList();
        result.setData(list);
        return result;
    }
    public ListResponse<ManufacturerResponseDto> getAllMaterialManufacturers(Long categoryId, String search, Integer pageNumber, Integer pageSize){
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<Manufacturer> entities =  manufacturerManager.getSourceManufacturers(manufacturerId, categoryId, search, pageNumber, pageSize);
        Long count = manufacturerManager.getSourceManufacturersCount(manufacturerId, categoryId, search);
        ListResponse<ManufacturerResponseDto> result = ListResponse.from(entities, manufacturerMapper::toDto, count);
        List<ManufacturerResponseDto> list = result.getData().stream()
                .peek(m -> m.setManufacturerAttributes(new HashSet<>())).toList();
        result.setData(list);
        return result;
    }


    public ManufacturerResponseDto getManufacturerByID(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerManager.findById(manufacturerId);
        ManufacturerResponseDto dto = manufacturerMapper.toDto(manufacturer);
        User loggedInuser = userManager.findByEmail(EncryptionHelper.encrypt(keycloakInfo.getUserInfo().getOrDefault("email", "").toString()));
        if (loggedInuser != null) {
            List<ManufacturerCategoryAttributesResponseDto> sortedAttributesList = dto.getManufacturerAttributes().stream()
                    .filter(m -> Objects.equals(m.getUser().getId(), loggedInuser.getId()))
                    .sorted((o1, o2) -> {
                        if (Objects.equals(o1.getId(), o2.getId())) return 0;
                        return o1.getId() > o2.getId() ? -1 : 1;
                    }).toList();
            if (!sortedAttributesList.isEmpty()) {
                dto.setManufacturerAttributes(Collections.singleton(sortedAttributesList.get(0)));
            } else {
                dto.setManufacturerAttributes(new HashSet<>());
            }
            return dto;
        }
        dto.setManufacturerAttributes(new HashSet<>());
        return dto;
    }

    public String getManufacturerNameById(Long manufacturerId){
        return manufacturerManager.findManufacturerNameById(manufacturerId);
    }

    public void update(ManufacturerRequestDto manufacturerRequestDto){
        Manufacturer existingManufacturer = manufacturerManager.findById(manufacturerRequestDto.getId());
        manufacturerRequestDto.setLicenseNumber(existingManufacturer.getLicenseNumber());
        Manufacturer entity = manufacturerMapper.toEntity(manufacturerRequestDto);
        entity.setUsers(existingManufacturer.getUsers());
        entity.setExternalManufacturerId(existingManufacturer.getExternalManufacturerId());
        if (entity.getStatus()==null){
            Status status = statusManager.findByName("Active");
            entity.setStatus(status);
        }
        entity.setManufacturerAttributes(existingManufacturer.getManufacturerAttributes());
       Manufacturer updatedEntity = manufacturerManager.update(entity);
       reCalculateAttributes(updatedEntity);
    }

    public void delete(Long manufacturerId){
        manufacturerManager.delete(manufacturerId);
    }

    public ManufacturerDetailsResponseDto getManufacturerDetails(Long manufacturerId){
        long tokenManufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Manufacturer manufacturer = manufacturerManager.findById(manufacturerId == null ? tokenManufacturerId : manufacturerId);
        return dtoMapper.mapEntityToDetailsDto(manufacturer);
    }

    public List<NameAddressResponseDto> getListDetails(List<Long> ids) {
        List<Manufacturer> manufacturerList = manufacturerManager.findByIds(ids);
        List<NameAddressResponseDto> result = new ArrayList<>();
        manufacturerList.forEach(m->{
                NameAddressResponseDto dto = new NameAddressResponseDto();
                dto.setId(m.getId());
                dto.setName(m.getName());
                dto.setLicenseNo(m.getLicenseNumber());
                dto.setCompleteAddress(addressService.getCompleteAddressForManufacturer(m.getId()));
                result.add(dto);
        });
        return result;
    }
    public ListResponse<ManufacturerDetailsResponseDto> getByType(String type, String search, Long sourceCategoryId, Long manufacturerId, Integer pageNumber, Integer pageSize) {
        Long tokenManufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        boolean isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
        boolean isSuperMonitor = userRoles.stream().anyMatch(r -> r.contains("MONITOR"));
        Manufacturer manufacturer = manufacturerManager.findById(((isSuperAdmin || isSuperMonitor) && manufacturerId !=null && manufacturerId!=0L) ? manufacturerId : tokenManufacturerId);
        State state = manufacturer.getAddress().getVillage().getDistrict().getState();
        StateLabTestAccess stateLabTestAccess = stateLabTestAccessManager.findByStateAndCategoryAndEntityType(sourceCategoryId, EntityType.lot, state.getId());
        boolean isRawMaterialsProcured = false;
        if (stateLabTestAccess!=null) isRawMaterialsProcured = stateLabTestAccess.getIsStateProcuredRawMaterials();
        List<Long> targetCategoryIds = new ArrayList<>();
        if (sourceCategoryId!=null){
            try {
                targetCategoryIds = getTargetCategoryId(sourceCategoryId, Long.valueOf(state.getGeoId()));
            }catch(Exception e){
                throw new CustomException("Error at calling rest api", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        ManufacturerType manufacturerType = Objects.equals(type, ManufacturerType.PRIVATE.name())?ManufacturerType.PRIVATE:ManufacturerType.PUBLIC;
        List<Manufacturer> manufacturers;
        Long count;
        if (isSuperAdmin && manufacturerId !=null && manufacturerId!=0L && isRawMaterialsProcured){
            manufacturers =  manufacturerManager.findByIdAndType(manufacturerId, manufacturerType, search, Collections.singletonList(sourceCategoryId), pageNumber, pageSize);
            count = manufacturerManager.getCountForIdAndType(manufacturerId, manufacturerType, search, Collections.singletonList(sourceCategoryId));
        } else if (isSuperAdmin && manufacturerId != null && manufacturerId != 0L){
            manufacturers =  manufacturerManager.findByIdAndType(manufacturerId, manufacturerType, search, targetCategoryIds, pageNumber, pageSize);
            count = manufacturerManager.getCountForIdAndType(manufacturerId, manufacturerType, search, targetCategoryIds);
        } else if (isRawMaterialsProcured) {
            manufacturers =  manufacturerManager.findByIdAndType(tokenManufacturerId, manufacturerType, search, Collections.singletonList(sourceCategoryId), pageNumber, pageSize);
            count = manufacturerManager.getCountForIdAndType(tokenManufacturerId, manufacturerType, search, Collections.singletonList(sourceCategoryId));
        }else{
            manufacturers =  manufacturerManager.findByIdAndType(tokenManufacturerId, manufacturerType, search, targetCategoryIds, pageNumber, pageSize);
            count = manufacturerManager.getCountForIdAndType(tokenManufacturerId, manufacturerType, search, targetCategoryIds);
        }
        if (manufacturers.isEmpty()){
        manufacturers = manufacturerManager.findByTypeAndStatus((manufacturerId !=null && manufacturerId!=0L)? manufacturerId : tokenManufacturerId, manufacturerType, search, targetCategoryIds, pageNumber, pageSize);
        count = manufacturerManager.getCountForTypeAndStatus((manufacturerId !=null && manufacturerId!=0L)? manufacturerId : tokenManufacturerId, manufacturerType, search, targetCategoryIds);
        }
        return ListResponse.from(manufacturers, dtoMapper::mapEntityToDetailsDto, count);
    }

    private List<Long> getTargetCategoryId(Long sourceCategoryId, Long stateGeoId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = fortificationBaseUrl+"fortification/category/" +sourceCategoryId+"/next";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("stateGeoId", stateGeoId)
                .build();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(keycloakInfo.getAccessToken());
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<List<Long>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,httpEntity,
                new ParameterizedTypeReference<List<Long>>() {
        });
        return response.getBody();
    }

    public void reCalculateAttributes(Manufacturer entity){
        String manufacturerName = entity.getName();
        String completeAddress = addressService.getCompleteAddressForManufacturer(entity.getAddress());
        List<User> users = userManager.findAllByManufacturerId(entity.getId());
        List<String> usernameList = users.stream().map(User::getUserName).toList();
        RealmResource keycloakRealmResource = keycloakCustomConfig.getInstance().realm(realmResource);
        List<UserRepresentation> keycloakUsers =   usernameList.stream()
                .map(u-> keycloakRealmResource.users().search(u).get(0))
                .toList();
        keycloakUsers.forEach(u->{
           Map<String, List<String>> attributes =  u.getAttributes();
           attributes.put("manufacturerAddress",Collections.singletonList(completeAddress));
           attributes.put("manufacturerName", Collections.singletonList(manufacturerName));
            UserResource userResource =  keycloakCustomConfig.getInstance().realm(realmResource).users().get(u.getId());
            userResource.update(u);
        });
    }

    public Map<String, CategoryRoleResponseDto> getAllRoleCategoriesForManufacturer(Long manufacturerId) {
        List<ManufacturerCategory> allByManufacturerId = manufacturerCategoryManager.findAllByManufacturerId(
            manufacturerId);
        Map<String, List<Long>> stringListMap = new HashMap<>();
        List<Long> categoryIdsList = new ArrayList<>();
        allByManufacturerId.forEach(e ->
            categoryIdsList.add(e.getCategoryId())
        );
        stringListMap.put("MODULE", categoryIdsList);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(keycloakInfo.getAccessToken());
        String url = fortificationBaseUrl + "fortification/category/manufacturer/role-categories";
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(stringListMap), headers);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        return (Map<String, CategoryRoleResponseDto>) response.getBody();
    }

    public ListResponse<ManufacturerResponseDto> getAllManufacturerFromCategoryIds(String search,
                                                                                   UserType type,
                                                                                   List<Long> categoryIds,
                                                                                   Integer pageNumber,
                                                                                   Integer pageSize) {
        List<Manufacturer> manufacturerList;
        Long count;
        if (type!=null  && type.equals(UserType.LAB)){
            manufacturerList = manufacturerManager.findByCategoryIds(search, categoryIds, pageNumber, pageSize);
            count = manufacturerManager.getCountForFindByCategoryIds(search, categoryIds);
        }else {
            List<Long> targetCategoryIds = new ArrayList<>();
            categoryIds.forEach(c->{
                List<Long> targetCategoryIdsFromAPI = new ArrayList<>();
                try {
                    targetCategoryIdsFromAPI = this.getTargetCategoryId(c, null);
                }catch (Exception ignored){
                }
                if (!targetCategoryIdsFromAPI.isEmpty()) targetCategoryIds.addAll(targetCategoryIdsFromAPI);
            });
            if (targetCategoryIds.size()!=categoryIds.size()) return new ListResponse<>();
            manufacturerList = manufacturerManager.findByCategoryIds(search,
                    targetCategoryIds, pageNumber, pageSize);
            count = manufacturerManager.getCountForFindByCategoryIds(search,
                    targetCategoryIds);
        }
        ListResponse<ManufacturerResponseDto> result = ListResponse.from(manufacturerList, manufacturerMapper::toDto, count);
        List<ManufacturerResponseDto> list = result.getData().stream()
                .peek(m -> m.setManufacturerAttributes(new HashSet<>())).toList();
        result.setData(list);
        return result;
    }

    public List<Long> getTestManufacturerIds() {
        return manufacturerManager.getTestManufacturerIds();
    }

    public ListResponse<ManufacturerDetailsResponseDto> getAllManufacturersWithGeoFilter(String search, Long stateId, Long districtId, Integer pageNumber, Integer pageSize) {
        List<Manufacturer> entities = manufacturerManager.getAllManufacturersWithGeoFilter(search, stateId, districtId, pageNumber, pageSize);
        Long count = manufacturerManager.getAllManufacturersCountWithGeoFilter(search, stateId, districtId);
        return ListResponse.from(entities, dtoMapper::mapEntityToDetailsDto, count);
    }

    public ListResponse<ManufacturerDetailsResponseDto> getAllManufacturersBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize) {
        List<Manufacturer> entities = manufacturerManager.getAllManufacturersBySearchAndFilter(searchRequest, pageNumber, pageSize);
        Long count = manufacturerManager.getAllManufacturersCountBySearchAndFilter(searchRequest);
        return ListResponse.from(entities, dtoMapper::mapEntityToDetailsDto, count);
    }

    public List<Long> getManufacturerIdsByAgency(String agency) {
        return manufacturerManager.findManufacturerIdsByAgency(agency);
    }

    public List<ManufacturerAgencyResponseDto> getManufacturerAgenciesByIds(Long categoryId, GeoType type, String geoId, List<Long> manufacturerIds) {
        String filterBy = switch (type) {
            case district -> "district";
            case state -> "district.state";
            case country -> "district.state.country";
        };
        return manufacturerManager.getManufacturerAgenciesByIds(categoryId, filterBy, geoId, manufacturerIds);
    }

    public List<ManufacturerAgencyResponseDto> getManufacturerNamesByIds(List<Long> manufacturerIds) {
        List<Object[]> results = manufacturerManager.getManufacturerNamesByIds(manufacturerIds);
        return results.stream()
                .map(r -> {
                    ManufacturerAgencyResponseDto dto = new ManufacturerAgencyResponseDto();
                    dto.setId((Long) r[0]);
                    dto.setAgencyName((String) r[1]);
                    dto.setDistrictGeoId((String) r[2]);
                    dto.setDistrictName((String) r[3]);
                    dto.setStateName((String) r[4]);
                    dto.setLicenseNumber((String)r[5]);
                    dto.setStateGeoId((String) r[6]);
                    return dto;
                }).toList();
    }

    public Map<Long, String>  getManufacturerNamesByIdsAndCategoryId(List<Long> manufacturerIds, Long categoryId){
        List<Object[]> results = manufacturerManager.getManufacturerNamesByIdsAndCategoryId(manufacturerIds, categoryId);
        return results.stream()
                .collect(
                        Collectors.toMap(
                                r -> (Long) r[0],
                                r -> (String) r[1]
                        )
                );
    }

    public String getDistrictGeoIdByManufacturerId(Long manufacturerId) {
        return manufacturerManager.findById(manufacturerId).getAddress().getVillage().getDistrict().getGeoId();
    }

    public List<Long> filterManufacturersByCategoryId(Long categoryId, List<Long> manufacturerIds) {
        return manufacturerCategoryManager.filterManufacturersByCategory(categoryId, manufacturerIds);
    }

}
