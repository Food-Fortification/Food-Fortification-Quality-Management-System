package com.beehyv.iam.utils;

import com.beehyv.iam.dto.requestDto.*;
import com.beehyv.iam.dto.responseDto.*;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.mapper.UserMapper;
import com.beehyv.iam.model.*;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@org.mapstruct.Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,imports = {UUID.class})
public interface DtoMapper {

    BaseMapper<UserResponseDto, UserRequestDto, User> userMapper = BaseMapper.getForClass(UserMapper.class);
    @Mapping(target = "vendorType",source = "vendorType")
    @Mapping(target = "completeAddress",expression = "java(convertAddressToCompleteAddress(manufacturer.getAddress()))")
    ManufacturerDetailsResponseDto mapEntityToDetailsDto(Manufacturer manufacturer);

    @Mapping(target = "uuid",expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "status",ignore = true)
    Manufacturer mapBulkUploadFileToManufacturer(BulkUploadRequestDto bulkUploadRequestDto);

    LocationResponseDto mapToDto(Country country);

    ManufacturerEmpanelResponseDto mapToDto(ManufacturerEmpanel entity);
    ManufacturerEmpanel mapToEntity(ManufacturerEmpanelRequestDto dto);

    @Mapping(source = "stateCategoryEntityTypeId.state",target = "stateResponseDto")
    @Mapping(source = "stateCategoryEntityTypeId.categoryId",target = "categoryId")
    @Mapping(source = "stateCategoryEntityTypeId.entityType",target = "entityType")
    StateLabTestAccessResponseDto mapToDto(StateLabTestAccess stateLabTestAccess);
    @Mapping(target = "stateCategoryEntityTypeId.state.id",source = "stateId")
    @Mapping(target = "stateCategoryEntityTypeId.categoryId",source = "categoryId")
    @Mapping(target = "stateCategoryEntityTypeId.entityType",source = "entityType")
    @Mapping(target = "status.id",source = "statusId")
    StateLabTestAccess mapToEntity(StateLabTestAccessRequestDto dto);

    LocationResponseDto mapToDto(State state);
    LocationResponseDto mapToDto(District district);

    LocationResponseDto mapToDto(Village village);
    @Mapping(target = "uuid",expression = "java(UUID.randomUUID().toString())")
    Country mapToEntityCountry(LocationRequestDto dto);
    @Mapping(target = "country.id",source = "countryId")
    @Mapping(target = "uuid",expression = "java(UUID.randomUUID().toString())")
    State mapToEntity(StateRequestDto dto);
    @Mapping(target = "country",source = "country")
    StateResponseDto mapToResponseDto(State state);
    @Mapping(target = "state.id",source = "stateId")
    @Mapping(target = "uuid",expression = "java(UUID.randomUUID().toString())")
    District mapToEntity(DistrictRequestDto dto);
    State mapToEntity(StateResponseDto dto);

    @Mapping(target = "state",expression = "java(mapToResponseDto(district.getState()))")
    DistrictResponseDto mapToResponseDto(District district);
    @Mapping(target = "district",source = "district")
    VillageResponseDto mapToResponseDto(Village village);
    @Mapping(target = "district.id",source = "districtId")
    @Mapping(target = "uuid",expression = "java(UUID.randomUUID().toString())")
    Village mapToEntity(VillageRequestDto dto);
    @Mapping(target = "village.id",source = "villageId")
    @Mapping(target = "uuid",expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "manufacturer.id",source = "manufacturerId")
    Address mapToEntity(AddressRequestDto dto);
    @Mapping(target = "village",expression = "java(mapToResponseDto(address.getVillage()))")
    @Mapping(target = "manufacturerDetails",expression = "java(mapEntityToDetailsDto(address.getManufacturer()))")
    AddressResponseDto mapToDto(Address address);
    @Mapping(target = "id",source = "user.id")
    @Mapping(target = "userName",source = "user.userName")
    @Mapping(target = "firstName",source = "user.firstName")
    @Mapping(target = "lastName",source = "user.lastName")
    @Mapping(target = "email",source = "user.email")
    @Mapping(target = "role",source = "role.name")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "status", source = "user.status.name")
    UserListResponseDto mapToDto(UserRoleCategory userRoleCategory);
    @Mapping(target = "laneOne",source = "address.laneOne")
    @Mapping(target = "laneTwo",source = "address.laneTwo")
    @Mapping(target = "pinCode",source = "address.pinCode")
    @Mapping(target = "latitude",source = "address.latitude")
    @Mapping(target = "longitude",source = "address.longitude")
    @Mapping(target = "villageName",source = "address.village.name")
    @Mapping(target = "stateName",source = "address.village.district.state.name")
    @Mapping(target = "districtName",source = "address.village.district.name")
    @Mapping(target = "districtGeoId",source = "address.village.district.geoId")
    @Mapping(target = "countryName",source = "address.village.district.state.country.name")
    @Mapping(target = "completeAddress",expression = "java(convertAddressToCompleteAddress(entity.getAddress()))")
    ManufacturerListDashboardResponseDto mapToDto(Manufacturer entity);

    @Mapping(target = "attributeCategory", expression = "java(new AttributeCategory(dto.getAttributeCategoryId()))")
    Attribute mapToEntity(AttributeRequestDto dto);

    @AfterMapping
    default void mapToEntity(@MappingTarget Attribute entity){
        if (entity.getDefaultScore() == null) entity.setDefaultScore(3);
    }

    @Mapping(target = "attributeCategory", expression = "java(mapToDto(entity.getAttributeCategory()))")
    @Mapping(target = "attributeCategory.attributes",ignore = true)
    AttributeResponseDto mapToDto(Attribute entity);

    @Mapping(target = "attributes", expression = "java(dto.getAttributes().stream().map(a -> mapToEntity(a)).collect(java.util.stream.Collectors.toSet()))")
    AttributeCategory mapToEntity(AttributeCategoryRequestDto dto);

    @AfterMapping
    default void mapToEntity(@MappingTarget AttributeCategory entity){
        entity.getAttributes().forEach(a -> a.setAttributeCategory(entity));
    }

    @Mapping(target = "attributes", expression = "java(entity.getAttributes().stream().map(a -> mapToDto(a)).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "attributes.attributeCategory", ignore = true)
    AttributeCategoryResponseDto mapToDto(AttributeCategory entity);

    @Mapping(target = "attributeCategory", expression = "java(new AttributeCategory(dto.getAttributeCategoryId()))")
    @Mapping(target = "manufacturerCategoryAttributes", expression = "java(new ManufacturerCategoryAttributes(dto.getManufacturerCategoryAttributesId()))")
    @Mapping(target = "attributeScore", expression = "java(dto.getManufacturerAttributeScores().stream().map(m -> mapToEntity(m)).collect(java.util.stream.Collectors.toSet()))")
    AttributeCategoryScore mapToEntity(AttributeCategoryScoreRequestDto dto);

    @AfterMapping
    default void mapToEntity(@MappingTarget AttributeCategoryScore entity){
        entity.getAttributeScore().forEach(ma -> ma.setAttributeCategoryScore(entity));
    }

    @Mapping(target = "attributeCategory", expression = "java(mapToDto(entity.getAttributeCategory()))")
    @Mapping(target = "attributeScore", expression = "java(entity.getAttributeScore().stream().map(a -> mapToDto(a)).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "manufacturerCategoryAttributes", expression = "java(mapToDto(entity.getManufacturerCategoryAttributes()))")
    @Mapping(target = "manufacturerCategoryAttributes.attributeCategoryScores", ignore = true)
    @Mapping(target = "manufacturerCategoryAttributes.manufacturer", ignore = true)
    @Mapping(target = "attributeScore.attributeCategoryScore", ignore = true)
    AttributeCategoryScoreResponseDto mapToDto(AttributeCategoryScore entity);

    @Mapping(target = "attribute", expression = "java(new Attribute(dto.getAttributeId()))")
    @Mapping(target = "attributeCategoryScore", expression = "java(new AttributeCategoryScore(dto.getAttributeCategoryScoreId()))")
    ManufacturerAttributeScore mapToEntity(ManufacturerAttributeScoreRequestDto dto);

    @Mapping(target = "attribute", expression = "java(mapToDto(entity.getAttribute()))")
    @Mapping(target = "attributeCategoryScore", expression = "java(mapToDto(entity.getAttributeCategoryScore()))")
    @Mapping(target = "attributeCategoryScore.attributeScore", ignore = true)
    @Mapping(target = "attributeCategoryScore.manufacturerCategoryAttributes", ignore = true)
    ManufacturerAttributeScoreResponseDto mapToDto(ManufacturerAttributeScore entity);

    @Mapping(target = "manufacturer", expression = "java(new Manufacturer(dto.getManufacturerId()))")
    @Mapping(target = "user", expression = "java(new User(dto.getUserId()))")
    @Mapping(target = "attributeCategoryScores", expression = "java(dto.getAttributeCategoryScores().stream().map(a -> mapToEntity(a)).collect(java.util.stream.Collectors.toSet()))")
    ManufacturerCategoryAttributes mapToEntity(ManufacturerCategoryAttributesRequestDto dto);

    @AfterMapping
    default void mapToEntity(@MappingTarget ManufacturerCategoryAttributes entity){
        entity.getAttributeCategoryScores().forEach(a -> a.setManufacturerCategoryAttributes(entity));
    }

    @Mapping(target = "manufacturer", expression = "java(manufacturerMapper.toDto(entity.getManufacturer()))")
    @Mapping(target = "user", expression = "java(userMapper.toDto(entity.getUser()))")
    @Mapping(target = "attributeCategoryScores", expression = "java(entity.getAttributeCategoryScores().stream().map(a -> mapToDto(a)).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "manufacturer.manufacturerAttributes", ignore = true)
    @Mapping(target = "manufacturer.completeAddress", ignore = true)
    @Mapping(target = "manufacturer.targetManufacturers", ignore = true)
    @Mapping(target = "manufacturer.address.manufacturerDetails", ignore = true)
    @Mapping(target = "manufacturer.manufacturerDocs", ignore = true)
    ManufacturerCategoryAttributesResponseDto mapToDto(ManufacturerCategoryAttributes entity);


    default String convertAddressToCompleteAddress(Address address){
        if (address==null) return null;
        String laneOne = address.getLaneOne();
        String laneTwo= address.getLaneTwo();
        String latitude= String.valueOf(address.getLatitude());
        String longitude = String.valueOf(address.getLongitude());
        Village village = Optional.ofNullable(address.getVillage()).orElse(new Village());
        String village_name = village.getName();
        String district_name = null;
        String state_name = null;
        String country_name = null;
        if (village.getDistrict() != null) {
            district_name = village.getDistrict().getName();
            if(village.getDistrict().getState() !=  null) {
                state_name = village.getDistrict().getState().getName();
                if(village.getDistrict().getState().getCountry() != null) {
                    country_name = village.getDistrict().getState().getCountry().getName();
                }
            }
        }
        String pincode = address.getPinCode();
        return Stream.of(laneOne, laneTwo, village_name,district_name,state_name,country_name,pincode,latitude,longitude)
                .filter(str -> !Objects.equals(str, "null") && !StringUtils.isEmpty(str))
                .collect(Collectors.joining(", "));
    }

    StatusResponseDto mapToResponseDto(Status status);
    Status mapToEntity(StatusRequestDto dto);
}
