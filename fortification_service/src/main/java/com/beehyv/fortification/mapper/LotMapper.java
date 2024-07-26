package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

import static com.beehyv.fortification.mapper.BaseMapper.*;

public class LotMapper implements Mappable<LotResponseDto, LotRequestDto, Lot>, ListMappable<LotListResponseDTO, LotRequestDto, Lot> {
    private final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> uomMapper = getForClass(UOMMapper.class);
    private final BaseMapper<BatchResponseDto, BatchRequestDto, Batch> batchMapper = getForClass(BatchMapper.class);
    private final BaseMapper<StateResponseDto, StateRequestDto, State> stateMapper = getForClass(StateMapper.class);
    private final BaseMapper<TransportQuantityDetailsResponseDto, TransportQuantityDetailsRequestDto, TransportQuantityDetails> transportQuantityDetailsMapper = getForClass(TransportQuantityDetailsMapper.class);

    @Override
    public LotResponseDto toDto(Lot entity) {
        LotResponseDto dto = mapToDtoHelper(entity);
        if (!entity.getSourceBatchMapping().stream().filter(b -> b.getBatch() == null).toList().isEmpty()) {
            dto.setTargetLots(entity.getSourceBatchMapping()
                    .stream()
                    .filter(b -> b.getBatch() == null)
                    .map(BatchLotMapping::getTargetLot)
                    .map(this::mapToDtoHelper)
                    .sorted(Comparator.comparing(LotResponseDto::getId).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }
        if (!entity.getTargetBatchMapping().stream().map(BatchLotMapping::getSourceLot).toList().isEmpty()) {
            dto.setSourceLots(entity.getTargetBatchMapping()
                    .stream()
                    .map(BatchLotMapping::getSourceLot)
                    .map(this::mapToDtoHelper)
                    .sorted(Comparator.comparing(LotResponseDto::getId).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }
        if(entity.getTransportQuantityDetails()!=null){
            dto.setTransportQuantityDetailsResponseDto(transportQuantityDetailsMapper.toDto(entity.getTransportQuantityDetails()));

        }
        if(entity.getLotProperties() != null) {
            Set<LotPropertyResponseDto> list = entity.getLotProperties()
                    .stream().map(lotPropertyMapper::toDto)
                    .collect(Collectors.toSet());
            dto.setLotProperties(list);
        }

        if(entity.getLotProperties() != null){
            Optional<LotProperty> manufactureLotNumber = entity.getLotProperties().stream().filter(lp -> lp.getName().equals("manufacture_lotNumber")).findFirst();
            manufactureLotNumber.ifPresent(lotProperty -> dto.setManufacturerLotNumber(lotProperty.getValue()));
        }
        if(dto.getManufacturerLotNumber() == null){
            Batch batch = null;
            if(entity.getBatch() !=null){
                batch = entity.getBatch();
            }else {
                Optional<Lot> sourceLot = entity.getTargetBatchMapping().stream().map(BatchLotMapping::getSourceLot).toList().stream().filter(l -> l.getBatch() != null).findFirst();
                if(sourceLot.isPresent()) batch = sourceLot.get().getBatch();
            }
            if(batch != null){
                Optional<BatchProperty> manufactureBatchNumber = batch.getBatchProperties().stream().filter(bp -> bp.getName().equals("manufacture_batchNumber")).findFirst();
                manufactureBatchNumber.ifPresent(batchProperty -> dto.setManufacturerLotNumber(batchProperty.getValue()));
                dto.setBatch(batchMapper.toDto(batch));
            }
        }
        return dto;
    }

    @Override
    public Lot toEntity(LotRequestDto dto) {
        Lot entity = new Lot();
        BeanUtils.copyProperties(dto, entity,"lotNo");
        if(dto.getUomId() != null) entity.setUom(new UOM(dto.getUomId()));
        if(dto.getBatchId() != null) entity.setBatch(new Batch(dto.getBatchId()), entity);
        if(dto.getStateId() != null) entity.setState(new State(dto.getStateId()));
        if(dto.getSizeUnits() != null) {
            Set<SizeUnit> set = dto.getSizeUnits()
                    .stream().map(sizeUnitMapper::toEntity)
                    .peek(d -> d.setLot(entity))
                    .collect(Collectors.toSet());
            entity.setSizeUnits(set);
        }
        if(dto.getWastes()!=null){
            Set<Wastage> set = dto.getWastes()
                    .stream().map(wastageMapper::toEntity)
                    .peek(d -> d.setLot(entity))
                    .collect(Collectors.toSet());
            entity.setWastes(set);

        }
        if(dto.getTransportQuantityDetailsRequestDto() != null) {
            entity.setTransportQuantityDetails(transportQuantityDetailsMapper.toEntity(dto.getTransportQuantityDetailsRequestDto()));
        }
        if(dto.getLotProperties() != null) {
            Set<LotProperty> list = dto.getLotProperties()
                    .stream().map(lotPropertyMapper::toEntity)
                    .peek(d -> {
                        d.setLot(entity);
                    })
                    .collect(Collectors.toSet());
            entity.setLotProperties(list);
        }
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AccessToken token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        LotProperty lotProperty = new LotProperty(null, "createdBy", token.getName(), entity);
        Set<LotProperty> lotProperties = entity.getLotProperties();
        if (lotProperties == null){
            lotProperties = new HashSet<>();
        }
        lotProperties.add(lotProperty);
        entity.setLotProperties(lotProperties);
        return entity;
    }

    @Override
    public LotListResponseDTO toListDto(Lot entity) {
        if(entity == null) return null;
        LotListResponseDTO lotListResponseDTO = new LotListResponseDTO();
        BeanUtils.copyProperties(entity, lotListResponseDTO);
        if(entity.getUom() != null) {
            lotListResponseDTO.setUom(entity.getUom().getName());
        }
        if(entity.getBatch() != null) {
            BatchResponseDto batchResponseDto = new BatchResponseDto();
            batchResponseDto.setId(entity.getBatch().getId());
            batchResponseDto.setBatchNo(entity.getBatch().getBatchNo());
            batchResponseDto.setUuid(entity.getBatch().getUuid());
            batchResponseDto.setComments(entity.getBatch().getComments());
            batchResponseDto.setPrefetchedInstructions(entity.getBatch().getPrefetchedInstructions());
            batchResponseDto.setManufacturerId(entity.getBatch().getManufacturerId());
            lotListResponseDTO.setBatch(batchResponseDto);
            lotListResponseDTO.setDateOfManufacture(entity.getBatch().getDateOfManufacture());
            lotListResponseDTO.setDateOfExpiry(entity.getBatch().getDateOfExpiry());
            entity.getBatch().getBatchProperties().stream()
                    .filter(bp -> bp.getName().equals("manufacture_batchNumber"))
                    .findFirst()
                    .ifPresent(bp -> lotListResponseDTO.setManufacturerBatchNumber(bp.getValue()));
            entity.getBatch().getBatchProperties().stream()
                    .filter(bp -> bp.getName().equals("batch_name"))
                    .findFirst()
                    .ifPresent(bp -> lotListResponseDTO.setBatchName(bp.getValue()));
        }
        if (entity.getCategory() != null) lotListResponseDTO.setCategory(categoryMapper.toDto(entity.getCategory()));
        if (entity.getState() != null) {
            lotListResponseDTO.setState(entity.getState().getDisplayName());
        }
        if(entity.getLotProperties() != null) {
            Set<LotPropertyResponseDto> list = entity.getLotProperties()
                    .stream().map(lotPropertyMapper::toDto)
                    .collect(Collectors.toSet());
            lotListResponseDTO.setLotProperties(list);
            entity.getLotProperties().stream().filter(lotProperty -> lotProperty.getName().equals("manufacture_lotNumber"))
                    .findFirst().ifPresent(lotProperty -> lotListResponseDTO.setManufacturerLotNumber(lotProperty.getValue()));
            lotListResponseDTO.setCreatedBy(null);
            entity.getLotProperties().stream().filter(lotProperty -> lotProperty.getName().equals("createdBy"))
                    .findFirst().ifPresent(lotProperty -> lotListResponseDTO.setCreatedBy(lotProperty.getValue()));
        }
        assert entity.getBatch() != null;
        return lotListResponseDTO;
    }

    private LotResponseDto mapToDtoHelper(Lot lot) {
        LotResponseDto dto = new LotResponseDto();
        BeanUtils.copyProperties(lot, dto);
        if (lot.getUuid() != null) dto.setQrcode("lot_" + lot.getUuid());
        if (lot.getUom() != null) {
            dto.setUom(uomMapper.toDto(lot.getUom()));
        }
        if (lot.getCategory() != null) dto.setCategory(categoryMapper.toDto(lot.getCategory()));
        if (lot.getBatch() != null) {
            dto.setBatch(batchMapper.toDto(lot.getBatch()));
            dto.setDateOfManufacture(lot.getBatch().getDateOfManufacture());
            dto.setDateOfExpiry(lot.getBatch().getDateOfExpiry());
        }
        if (lot.getSizeUnits() != null) {
            Set<SizeUnitResponseDto> set = lot.getSizeUnits()
                    .stream().map(sizeUnitMapper::toDto)
                    .collect(Collectors.toSet());
            dto.setSizeUnits(set);
        }
        if (lot.getState() != null) {
            dto.setState(stateMapper.toDto(lot.getState()));
        }
        if (lot.getWastes() != null) {
            Set<WastageResponseDto> set = lot.getWastes()
                    .stream().map(wastageMapper::toDto)
                    .sorted(Comparator.comparing(WastageResponseDto::getId).reversed())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            dto.setWastes(set);
        }
        return dto;
    }
}
