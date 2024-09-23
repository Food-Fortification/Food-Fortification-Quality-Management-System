package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.parent.exceptions.MapperException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class BaseMapper<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {
    public static final BaseMapper<BatchPropertyResponseDto, BatchPropertyRequestDto, BatchProperty> batchPropertyMapper = BaseMapper.getForClass(BatchPropertyMapper.class);
    public static final BaseMapper<LotPropertyResponseDto, LotPropertyRequestDto, LotProperty> lotPropertyMapper = BaseMapper.getForClass(LotPropertyMapper.class);

    public static final BaseMapper<BatchDocResponseDto, BatchDocRequestDto, BatchDoc> batchDocMapper = BaseMapper.getForClass(BatchDocMapper.class);
    public static final BaseMapper<CategoryResponseDto, CategoryRequestDto, Category> categoryMapper = BaseMapper.getForClass(CategoryMapper.class);
    public static final BaseMapper<SizeUnitResponseDto, SizeUnitRequestDto, SizeUnit> sizeUnitMapper = BaseMapper.getForClass(SizeUnitMapper.class);
    public static final BaseMapper<LotResponseDto, LotRequestDto, Lot> lotMapper = BaseMapper.getForClass(LotMapper.class);
    public static final BaseMapper<LotListResponseDTO, LotRequestDto, Lot> lotListMapper = BaseMapper.getForListClass(LotMapper.class);
    public static final BaseMapper<WastageResponseDto, WastageRequestDto, Wastage> wastageMapper = BaseMapper.getForClass(WastageMapper.class);
    public static final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> uomMapper = BaseMapper.getForClass(UOMMapper.class);
    public static final BaseMapper<MixMappingResponseDto, MixMappingRequestDto, MixMapping> mixMapper = BaseMapper.getForClass(MixMappingMapper.class);
    public static final BaseMapper<StateResponseDto, StateRequestDto, State> stateMapper = BaseMapper.getForClass(StateMapper.class);
    Mappable<ResponseDto, RequestDto, E> mapper;
    ListMappable<ResponseDto, RequestDto, E> listMapper;

    public <M extends Mappable<ResponseDto, RequestDto, E>> BaseMapper(Class<M> mappableClass) throws MapperException {
        try {
            this.mapper = mappableClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }

    public <M extends ListMappable<ResponseDto, RequestDto, E>> BaseMapper(Class<M> mappableClass, String type) throws MapperException {
        try {
            this.listMapper = mappableClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }

    public static <ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base, M extends Mappable<ResponseDto, RequestDto, E>>
    BaseMapper<ResponseDto, RequestDto, E> getForClass(Class<M> mappableClass) {
        return new BaseMapper<>(mappableClass);
    }

    public static <ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base, M extends ListMappable<ResponseDto, RequestDto, E>>
    BaseMapper<ResponseDto, RequestDto, E> getForListClass(Class<M> mappableClass) {
        return new BaseMapper<>(mappableClass, "list");
    }

    // Map Base JPA Entity into given dto
    public void toBaseDto(E entity, ResponseDto dto) {
        if(entity == null) return;
        if(entity.getUuid() != null) {
            dto.setUuid(entity.getUuid().toString());
        }
//        dto.setCreatedBy(entity.getCreatedBy());
//        dto.setModifiedBy(entity.getModifiedBy());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setModifiedDate(entity.getModifiedDate());
        if (entity.getStatus() != null && !(entity instanceof Status)) {
            StatusResponseDto statusResponseDto = new StatusResponseDto();
            statusResponseDto.setId(entity.getStatus().getId());
            statusResponseDto.setName(entity.getStatus().getName());
            statusResponseDto.setDescription(entity.getStatus().getDescription());
        }
    }

    // Map given dto into given JPA Entity
    public void toBaseEntity(RequestDto dto, E entity) {
        if (dto.getStatusId() != null) {
            Status status = new Status();
            status.setId(dto.getStatusId());
            entity.setStatus(status);
        }
    }

    public E toEntity(RequestDto dto) throws MapperException {
        E entity = mapper.toEntity(dto);
        toBaseEntity(dto, entity);
        return entity;
    }

    public ResponseDto toDto(E entity) throws MapperException {
        ResponseDto dto = mapper.toDto(entity);
        toBaseDto(entity, dto);
        return dto;
    }

    public ResponseDto toListDTO(E entity) throws MapperException {
        ResponseDto dto = listMapper.toListDto(entity);
        return dto;
    }
}
