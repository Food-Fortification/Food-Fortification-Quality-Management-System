package com.beehyv.iam.service;

import com.beehyv.iam.config.KeycloakCustomConfig;
import com.beehyv.iam.dto.external.FssaiLicenseResponseDto;
import com.beehyv.iam.dto.requestDto.*;
import com.beehyv.iam.dto.responseDto.*;
import com.beehyv.iam.enums.*;
import com.beehyv.iam.helper.ExternalRestHelper;
import com.beehyv.iam.helper.FortificationRestHelper;
import com.beehyv.iam.manager.*;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.mapper.ManufacturerMapper;
import com.beehyv.iam.model.*;
import com.beehyv.iam.utils.DtoMapper;
import com.beehyv.iam.utils.FunctionUtils;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Constants;
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
    private final ExternalMetaDataManager externalMetaDataManager;
    private final FssaiManufacturerErrorLogService fssaiManufacturerErrorLogService;
    private final UserService userService;
    private final LoginService loginService;

    @Value("${config.realm.resource}")
    private String realmResource;
    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;
    @Value(("${service.superadmin.username}"))
    private String superadminUsername;
    @Value("${service.lab.baseUrl}")
    private String labBaseUrl;
    @Value(("${service.superadmin.password}"))
    private String superadminPassword;
    private static final RestTemplate restTemplate = new RestTemplate();

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

    public String createFssaiManufacturer(FssaiManufacturerRequestDto fssaiManufacturerRequestDto) {
       Manufacturer existingManufacturer =  manufacturerManager.findByLicenseNo(fssaiManufacturerRequestDto.getLicenseNumber());
       if(existingManufacturer != null) return "Manufacturer with provided license number already exists";
       ExternalMetaData fssaiLicenseNoApi = externalMetaDataManager.findByKey("fssaiLicenseNo");
        List<FssaiLicenseResponseDto> fssaiLicenseResponseDtoList = new ArrayList<>();
       try {
           fssaiLicenseResponseDtoList = ExternalRestHelper.validateFssaiLicenseNo(fssaiLicenseNoApi.getValue() + fssaiManufacturerRequestDto.getLicenseNumber());
       }catch (Exception e){
           return "Validation of License number with Fssai failed, Please contact Administrator";
       }
       try {
           String districtName = FunctionUtils.getDistrictName(fssaiManufacturerRequestDto.getDistrictName());
           if (districtName != null){
               fssaiManufacturerRequestDto.setDistrictName(districtName);
           }else {
               return "Invalid District Name provided, Please contact Administrator";
           }
           List<String> categoryNames  = new ArrayList<>();
           if (fssaiLicenseResponseDtoList.stream().anyMatch(dto -> UserCategory.Yes.equals(dto.getPremix()))) categoryNames.add("PREMIX");
           if (fssaiLicenseResponseDtoList.stream().anyMatch(dto -> UserCategory.Yes.equals(dto.getFRK()))) categoryNames.add("FRK");
           if (fssaiLicenseResponseDtoList.stream().anyMatch(dto -> UserCategory.Yes.equals(dto.getMiller()))) categoryNames.add("MILLER");
           fssaiManufacturerRequestDto.setCategoryNames(categoryNames);
           String fortificationUrl = fortificationBaseUrl + "fortification/category";
           UriComponents builder = UriComponentsBuilder.fromHttpUrl(fortificationUrl)
                   .queryParam("independentBatch", false)
                   .build();
           AccessTokenResponse accessTokenResponse =  loginService.login(new LoginRequestDto(superadminUsername, superadminPassword, null), externalMetaDataManager.findByKey("apkVersion").getValue());
           ListResponse<CategoryResponseDto> response = FortificationRestHelper.fetchCategoryListResponse(builder.toUriString(), accessTokenResponse.getToken());
           ManufacturerRequestDto manufacturerRequestDto = getManufacturerRequestDto(fssaiManufacturerRequestDto, response);

           Village village = villageManager.findByDistrictNameAndVillageName(fssaiManufacturerRequestDto.getVillageName(), fssaiManufacturerRequestDto.getDistrictName());
           if (village == null) {
               village = new Village();
               village.setName(fssaiManufacturerRequestDto.getName());
               village.setDistrict(districtManager.findByName(fssaiManufacturerRequestDto.getDistrictName()));
               village = villageManager.create(village);
           }

           AddressRequestDto addressRequestDTO = getAddressRequestDto(fssaiManufacturerRequestDto, village);
           manufacturerRequestDto.setAddress(addressRequestDTO);

           Manufacturer manufacturer = create(manufacturerRequestDto, accessTokenResponse.getToken());
           if (fssaiLicenseResponseDtoList.stream().noneMatch(FssaiLicenseResponseDto::getLicenseActiveFlag))manufacturer.setLicenseStatus(false);

           String[] s = manufacturer.getName().split(" ");
           UserRequestDto userRequestDto = new UserRequestDto();
           if(s.length>=1){
               userRequestDto.setFirstName(s[0]);
           }
           if(s.length>1){
               String lastName = String.join(" ", Arrays.copyOfRange(s, 1, s.length));
               userRequestDto.setLastName(lastName);
           }
           userRequestDto.setEmail(fssaiManufacturerRequestDto.getEmailAddress());
           userRequestDto.setUserName(fssaiManufacturerRequestDto.getLicenseNumber());
           userRequestDto.setPassword(userService.generatePassword(manufacturer));
           userRequestDto.setManufacturerId(manufacturer.getId());
           userRequestDto.setRolesMap(
                   manufacturer.getManufacturerCategories()
                           .stream().map(c -> {
                               RoleRequestDto roleRequestDto = new RoleRequestDto();
                               roleRequestDto.setRoleName("ADMIN");
                               roleRequestDto.setRoleCategoryType("MODULE");
                               roleRequestDto.setCategoryId(c.getCategoryId());
                               roleRequestDto.setCategoryName(response.getData().stream().filter(category -> Objects.equals(category.getId(), c.getCategoryId())).findFirst().orElseThrow().getName());
                               response.getData().forEach(r -> {
                                   if (r.getId().equals(c.getId())) {
                                       roleRequestDto.setCategoryName(r.getName());
                                   }
                               });
                               return roleRequestDto;
                           }).toList()
           );
           userService.addUser(userRequestDto, true);
           return "Successfully Created";
       }catch (Exception e){
           try {
               fssaiManufacturerErrorLogService.create(new ObjectMapper().writeValueAsString(fssaiManufacturerRequestDto), e.getMessage());
           } catch (JsonProcessingException ex) {
               log.info("Error in object mapper in create fssai manufacturer method.");
               throw new CustomException("Oops! Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
           }
           throw new CustomException("Something went wrong while creating manufacturer in ForTrace, please contact administrator", HttpStatus.BAD_REQUEST);
       }
    }

    private ManufacturerRequestDto getManufacturerRequestDto(FssaiManufacturerRequestDto fssaiManufacturerRequestDto, ListResponse<CategoryResponseDto> response){
        ManufacturerRequestDto manufacturerRequestDto = new ManufacturerRequestDto();
        manufacturerRequestDto.setName(fssaiManufacturerRequestDto.getName());
        manufacturerRequestDto.setStatusId(statusManager.findByName("Active").getId());
        manufacturerRequestDto.setAccreditedByAgency(fssaiManufacturerRequestDto.getAccreditedByAgency());
        manufacturerRequestDto.setAgencyName(fssaiManufacturerRequestDto.getAgencyName());
        manufacturerRequestDto.setLicenseNumber(fssaiManufacturerRequestDto.getLicenseNumber());
        manufacturerRequestDto.setManufacturerType(fssaiManufacturerRequestDto.getManufacturerType());
        manufacturerRequestDto.setManufacturerType(fssaiManufacturerRequestDto.getManufacturerType());
        manufacturerRequestDto.setVendorType(fssaiManufacturerRequestDto.getVendorType());

        manufacturerRequestDto.setManufacturerCategory(
                fssaiManufacturerRequestDto.getCategoryNames()
                        .stream().map(categoryName -> {
                            ManufacturerCategoryRequestDto manufacturerCategoryRequestDto = new ManufacturerCategoryRequestDto();
                            response.getData().forEach(r -> {
                                if (r.getName().equals(categoryName)) {
                                    manufacturerCategoryRequestDto.setIsEnabled(true);
                                    manufacturerCategoryRequestDto.setCategoryId(r.getId());
                                }
                            });
                            return manufacturerCategoryRequestDto;
                        }).collect(Collectors.toSet())
        );
        return manufacturerRequestDto;
    }

    private static AddressRequestDto getAddressRequestDto(FssaiManufacturerRequestDto fssaiManufacturerRequestDto, Village village) {
        AddressRequestDto addressRequestDTO = new AddressRequestDto();
        addressRequestDTO.setVillageId(village.getId());
        addressRequestDTO.setLatitude(fssaiManufacturerRequestDto.getLatitude());
        addressRequestDTO.setLongitude(fssaiManufacturerRequestDto.getLongitude());
        addressRequestDTO.setLaneOne(fssaiManufacturerRequestDto.getLaneOne());
        addressRequestDTO.setLaneTwo(fssaiManufacturerRequestDto.getLaneTwo());
        addressRequestDTO.setPinCode(fssaiManufacturerRequestDto.getPinCode());
        return addressRequestDTO;
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

    public NameAddressResponseDto getMaterialManufacturer(String licenseNo, Long categoryId){
        Manufacturer manufacturer = manufacturerManager.findByLicenseNoAndCategoryId(licenseNo, categoryId);
        if(manufacturer == null){
            manufacturer = manufacturerManager.findByLicenseNo(licenseNo);
            if(manufacturer != null){
                ManufacturerCategory manufacturerCategory = new ManufacturerCategory();
                manufacturerCategory.setCategoryId(categoryId);
                manufacturerCategory.setIsEnabled(true);
                manufacturerCategory.setCanSkipRawMaterials(false);
                manufacturerCategory.setManufacturer(manufacturer);
                Set<ManufacturerCategory> manufacturerCategories = manufacturer.getManufacturerCategories();
                manufacturerCategories.add(manufacturerCategory);
                manufacturer.setManufacturerCategories(manufacturerCategories);
                manufacturer = manufacturerManager.update(manufacturer);
            }
        }

        if(manufacturer == null) {
            ExternalMetaData fssaiLicenseNoApi = externalMetaDataManager.findByKey("fssaiLicenseNo");
            List<FssaiLicenseResponseDto> dtoList = ExternalRestHelper.validateFssaiLicenseNo(fssaiLicenseNoApi.getValue() + licenseNo);
            District district;
            String districtName = FunctionUtils.getDistrictName(dtoList.get(0).getDistrictName());
            if (districtName != null) dtoList.get(0).setDistrictName(districtName);
            try{
                district = districtManager.findByName(dtoList.get(0).getDistrictName());
            }catch (Exception e){
                State state = stateManager.findByNameAndCountryId(dtoList.get(0).getStateName(), countryManager.findByName("India").getId());
                district = new District();
                district.setName(dtoList.get(0).getDistrictName());
                district.setState(state);
                district = districtManager.create(district);
            }
            Village village = new Village();
            village.setName(dtoList.get(0).getVillageName() == null ? dtoList.get(0).getTalukName() : dtoList.get(0).getVillageName());
            village.setDistrict(district);
            village = villageManager.create(village);

            AddressRequestDto addressRequestDto = new AddressRequestDto();
            addressRequestDto.setCountryName("India");
            addressRequestDto.setStateName(dtoList.get(0).getStateName());
            addressRequestDto.setDistrictName(dtoList.get(0).getDistrictName());
            addressRequestDto.setVillageId(village.getId());
            addressRequestDto.setVillageName(village.getName());
            addressRequestDto.setPinCode(dtoList.get(0).getPremisePincode());

            String[] address = dtoList.get(0).getPremiseAddress().split(",", 2);
            if (address.length == 2) {
                addressRequestDto.setLaneOne(address[0]);
                addressRequestDto.setLaneTwo(address[1]);
            } else {
                addressRequestDto.setLaneOne(dtoList.get(0).getPremiseAddress());
            }

            ManufacturerCategoryRequestDto manufacturerCategoryRequestDto = new ManufacturerCategoryRequestDto();
            manufacturerCategoryRequestDto.setCategoryId(categoryId);

            ManufacturerRequestDto manufacturerRequestDto = new ManufacturerRequestDto();
            manufacturerRequestDto.setLicenseNumber(dtoList.get(0).getLicenseNo());
            manufacturerRequestDto.setName(dtoList.get(0).getCompanyName());
            manufacturerRequestDto.setAgencyName(dtoList.get(0).getCompanyName());
            manufacturerRequestDto.setManufacturerCategory(Set.of(manufacturerCategoryRequestDto));
            manufacturerRequestDto.setAddress(addressRequestDto);
            manufacturerRequestDto.setVendorType(VendorType.Manufacturer);
            manufacturerRequestDto.setManufacturerType(ManufacturerType.PRIVATE);
            manufacturer = create(manufacturerRequestDto, null);
        }

        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Manufacturer currentManufacturer = manufacturerManager.findById(manufacturerId);
        if(currentManufacturer.getSourceManufacturers() == null) {
            currentManufacturer.setSourceManufacturers(new HashSet<>());
        }
        currentManufacturer.getSourceManufacturers().add(manufacturer);
        manufacturerManager.update(currentManufacturer);

        NameAddressResponseDto responseDto = new NameAddressResponseDto();
        responseDto.setId(manufacturer.getId());
        responseDto.setName(manufacturer.getName());
        return responseDto;
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
        User loggedInuser = userManager.findByEmail(keycloakInfo.getUserInfo().getOrDefault("email", "").toString());
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
        manufacturers = manufacturerManager.findByTypeAndStatus(manufacturerType, search, targetCategoryIds, pageNumber, pageSize);
        count = manufacturerManager.getCountForTypeAndStatus(manufacturerType, search, targetCategoryIds);
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

    public Long getManufacturerIdByExternalManufacturerId(String externalManufacturerId){
        Manufacturer manufacturer = manufacturerManager.findByExternalManufacturerId(externalManufacturerId);
        return manufacturer == null ? 0L : manufacturer.getId();
    }

    public String getExternalManufacturerIdByManufacturerId(Long manufacturerId){
        Manufacturer manufacturer = manufacturerManager.findExternalManufacturerIdByManufacturerId(manufacturerId);
        return manufacturer == null ? "" : manufacturer.getExternalManufacturerId();
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

    public NameAddressResponseDto validateFssaiLicenseNo(String licenseNo, Long categoryId){
        ExternalMetaData fssaiLicenseNoApi = externalMetaDataManager.findByKey("fssaiLicenseNo");
        ExternalRestHelper.validateFssaiLicenseNo(fssaiLicenseNoApi.getValue() + licenseNo);
        List<Long> targetCategoryIds = FortificationRestHelper.fetchResponse(fortificationBaseUrl + "fortification/category/" + categoryId + "/next", keycloakInfo.getAccessToken());
        Manufacturer manufacturer = manufacturerManager.findByLicenseNoAndCategoryId(licenseNo, targetCategoryIds);
        if(manufacturer == null) throw new CustomException("Manufacturer doesn't exist for the license number!", HttpStatus.BAD_REQUEST);
        NameAddressResponseDto responseDto = new NameAddressResponseDto();
        responseDto.setId(manufacturer.getId());
        responseDto.setName(manufacturer.getName());
        responseDto.setLicenseNo(manufacturer.getLicenseNumber());
        return responseDto;
    }

    public String findLicenseNumberById(Long id){
        try{
         return manufacturerManager.findLicenseNumberById(id);
        }
        catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String findLicenseNumberByName(String name){
        try{
            return manufacturerManager.findLicenseNumberByName(name);
        }
        catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
