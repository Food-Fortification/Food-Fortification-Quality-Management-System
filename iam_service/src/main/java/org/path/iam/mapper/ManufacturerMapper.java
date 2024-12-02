package org.path.iam.mapper;

import org.path.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import org.path.iam.dto.requestDto.ManufacturerDocsRequestDto;
import org.path.iam.dto.requestDto.ManufacturerPropertyRequestDto;
import org.path.iam.dto.requestDto.ManufacturerRequestDto;
import org.path.iam.dto.responseDto.ManufacturerCategoryResponseDto;
import org.path.iam.dto.responseDto.ManufacturerDocsResponseDto;
import org.path.iam.dto.responseDto.ManufacturerPropertyResponseDto;
import org.path.iam.dto.responseDto.ManufacturerResponseDto;
import org.path.iam.enums.ManufacturerType;
import org.path.iam.enums.VendorType;
import org.path.iam.model.*;
import org.path.iam.utils.DtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class ManufacturerMapper implements Mappable<ManufacturerResponseDto, ManufacturerRequestDto, Manufacturer>{

    private static final BaseMapper<ManufacturerDocsResponseDto, ManufacturerDocsRequestDto, ManufacturerDoc> manufacturerDocsMapper = BaseMapper.getForClass(ManufacturerDocsMapper.class);
    private static final BaseMapper<ManufacturerCategoryResponseDto, ManufacturerCategoryRequestDto, ManufacturerCategory> manufacturerCategoryMapper = BaseMapper.getForClass(ManufacturerCategoryMapper.class);
    public static final BaseMapper<ManufacturerPropertyResponseDto, ManufacturerPropertyRequestDto, ManufacturerProperty> manufacturerPropertyMapper = BaseMapper.getForClass(ManufacturerPropertyMapper.class);

    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    @Override
    public ManufacturerResponseDto toDto(Manufacturer entity) {
        ManufacturerResponseDto dto = new ManufacturerResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCompleteAddress(dtoMapper.convertAddressToCompleteAddress(entity.getAddress()));
        dto.setType(entity.getType());
        dto.setAccreditedByAgency(entity.getAccreditedByAgency());
        dto.setVendorType(entity.getVendorType());
        dto.setManufacturerType(entity.getManufacturerType());
        dto.setAgencyName(entity.getAgencyName());
        dto.setLicenseNumber(entity.getLicenseNumber());
        dto.setExternalManufacturerId(entity.getExternalManufacturerId());
        if(entity.getManufacturerProperties() != null) {
            Set<ManufacturerPropertyResponseDto> list = entity.getManufacturerProperties()
                    .stream().map(manufacturerPropertyMapper::toDto)
                    .collect(Collectors.toSet());
            dto.setManufacturerProperties(list);
        }
        if (entity.getManufacturerDocs()!=null) dto.setManufacturerDocs(entity.getManufacturerDocs().stream()
                .map(manufacturerDocsMapper::toDto)
                .collect(Collectors.toSet()));
        if (entity.getManufacturerCategories()!=null)dto.setManufacturerCategory(entity.getManufacturerCategories().stream()
                .map(m->{
                    ManufacturerCategoryResponseDto manufacturerCategoryResponseDto = new ManufacturerCategoryResponseDto();
                    BeanUtils.copyProperties(m,manufacturerCategoryResponseDto);
                    return manufacturerCategoryResponseDto;
                })
                .collect(Collectors.toSet()));
        if (entity.getManufacturerAttributes()!=null)dto.setManufacturerAttributes(entity.getManufacturerAttributes().stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        if(entity.getAddress()!=null) dto.setAddress(dtoMapper.mapToDto(entity.getAddress()));
        if (entity.getTargetManufacturers()!=null){
           dto.setTargetManufacturers(this.setTargetManufacturers(entity.getTargetManufacturers()));
        }
        dto.setTotalScore(entity.getTotalScore());
        return dto;
    }

    @Override
    public Manufacturer toEntity(ManufacturerRequestDto dto) {
        Manufacturer entity = new Manufacturer();
        if (dto.getId()!=null) entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        if (dto.getAccreditedByAgency()==null){
            entity.setAccreditedByAgency(false);
        }else {
            entity.setAccreditedByAgency(dto.getAccreditedByAgency());
        }
        if (dto.getVendorType()==null){
            entity.setVendorType(VendorType.Manufacturer);
        }else {
            entity.setVendorType(dto.getVendorType());
        }
        if (dto.getManufacturerType()==null){
            entity.setManufacturerType(ManufacturerType.PUBLIC);
        }else {
            entity.setManufacturerType(dto.getManufacturerType());
        }
        entity.setAgencyName(dto.getAgencyName());
        entity.setLicenseNumber(dto.getLicenseNumber());
        entity.setExternalManufacturerId(dto.getExternalManufacturerId());
        if (dto.getManufacturerDocs()!=null) entity.setManufacturerDocs(dto.getManufacturerDocs()
                .stream().map(manufacturerDocsMapper::toEntity)
                        .peek(d->d.setManufacturer(entity))
                .collect(Collectors.toSet()));
        if(dto.getTargetManufacturers()!=null)entity.setTargetManufacturers(dto.getTargetManufacturers()
                .stream().map(Manufacturer::new)
                .collect(Collectors.toSet()));
        if (dto.getAddress()!=null){
            Address address = dtoMapper.mapToEntity(dto.getAddress());
            address.setManufacturer(entity);
            entity.setAddress(address);
        }
        if (dto.getManufacturerCategory()!=null)entity.setManufacturerCategories(dto.getManufacturerCategory()
                .stream().map(manufacturerCategoryMapper::toEntity)
                .peek(m->m.setManufacturer(entity))
                .collect(Collectors.toSet()));
        if(dto.getManufacturerProperties() != null) {
            Set<ManufacturerProperty> list = dto.getManufacturerProperties()
                    .stream().map(manufacturerPropertyMapper::toEntity)
                    .peek(d -> {
                        d.setManufacturer(entity);
                    })
                    .collect(Collectors.toSet());
            entity.setManufacturerProperties(list);
        }
        return entity;
    }
    private Set<ManufacturerResponseDto> setTargetManufacturers(Set<Manufacturer> targetManufacturers){
     return targetManufacturers.stream().map(m->{
            ManufacturerResponseDto dto = new ManufacturerResponseDto();
            dto.setId(m.getId());
            dto.setName(m.getName());
            dto.setManufacturerType(m.getManufacturerType());
            if (m.getManufacturerCategories()!=null)dto.setManufacturerCategory(m.getManufacturerCategories().stream()
                    .map(mc->{
                        ManufacturerCategoryResponseDto manufacturerCategoryResponseDto = new ManufacturerCategoryResponseDto();
                        BeanUtils.copyProperties(mc,manufacturerCategoryResponseDto);
                        return manufacturerCategoryResponseDto;
                    })
                    .collect(Collectors.toSet()));
            return dto;
        }).collect(Collectors.toSet());
    }
}
