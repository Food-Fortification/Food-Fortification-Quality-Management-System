package com.beehyv.iam.service;

import com.beehyv.iam.dto.external.ChildPurchaseOrderResponseDto;
import com.beehyv.iam.dto.external.PurchaseOrderRequestDto;
import com.beehyv.iam.dto.external.PurchaseOrderResponseDto;
import com.beehyv.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerDispatchableQuantityResponseDto;
import com.beehyv.iam.manager.ManufacturerCategoryManager;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.manager.PurchaseOrderManager;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.mapper.PurchaseOrderMapper;
import com.beehyv.iam.model.ExternalMetaData;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.model.PurchaseOrder;
import com.beehyv.parent.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseOrderService {
  private final PurchaseOrderManager purchaseOrderManager;
  private final ManufacturerManager manufacturerManager;
  private final ManufacturerCategoryManager manufacturerCategoryManager;
  private final BaseMapper<PurchaseOrderResponseDto, PurchaseOrderRequestDto, PurchaseOrder> purchaseOrderMapper = BaseMapper.getForClass(PurchaseOrderMapper.class);

  public static Map<String, String> commodityMap = Map.of(
          "CMD017", "RAW",
          "CMD022", "BOIL"
  );

  public void create(PurchaseOrderRequestDto dto){
    log.info("PurchaseOrderRequestDto: {}" , dto.toString());

    if(purchaseOrderManager.findExistingPurchaseOrder(dto.getMoTransactionId())){
      throw new CustomException("MoTransactionId already exists.", HttpStatus.BAD_REQUEST);
    }

    List<PurchaseOrder> entityList = this.toEntityList(dto);

    entityList.forEach(entity -> {
      Manufacturer sourceManufacturer;
      Manufacturer targetManufacturer;
      if(entity.getExternalSourceManufacturerId()!=null && entity.getExternalTargetManufacturerId()!=null){
        sourceManufacturer = manufacturerManager.findByExternalManufacturerId(entity.getExternalSourceManufacturerId());
        targetManufacturer = manufacturerManager.findByExternalManufacturerId(entity.getExternalTargetManufacturerId());
        if(sourceManufacturer==null){
          throw new CustomException("Manufacturer not found for the sourceExternalManufacturerId: " + entity.getExternalSourceManufacturerId(), HttpStatus.BAD_REQUEST);
        } else if (targetManufacturer==null) {
          throw new CustomException("Manufacturer not found for the targetExternalManufacturerId: " + entity.getExternalTargetManufacturerId(), HttpStatus.BAD_REQUEST);
        }
      }else {
        sourceManufacturer = manufacturerManager.findById(entity.getSourceManufacturerId());
        targetManufacturer = manufacturerManager.findById(entity.getTargetManufacturerId());
      }


      if(!sourceManufacturer.getTargetManufacturers().contains(targetManufacturer)){
        sourceManufacturer.getTargetManufacturers().add(targetManufacturer);
        manufacturerManager.update(sourceManufacturer);
      }

      entity.setSourceManufacturerId(sourceManufacturer.getId());
      entity.setExternalSourceManufacturerId(sourceManufacturer.getExternalManufacturerId());
      entity.setTargetManufacturerId(targetManufacturer.getId());
      entity.setExternalTargetManufacturerId(targetManufacturer.getExternalManufacturerId());
      entity.setSourceDistrictGeoId(sourceManufacturer.getAddress().getVillage().getDistrict().getGeoId());
      entity.setTargetDistrictGeoId(targetManufacturer.getAddress().getVillage().getDistrict().getGeoId());
      purchaseOrderManager.create(entity);
    });
  }

  public void update(PurchaseOrderRequestDto dto){
    //TODO: handle update later
    purchaseOrderManager.update(purchaseOrderMapper.toEntity(dto));
  }

  public void delete(Long id){
    purchaseOrderManager.delete(id);
  }

  public PurchaseOrderResponseDto getById(Long id){
    //TODO: handle mapper
    PurchaseOrder entity = purchaseOrderManager.findById(id);
    return purchaseOrderMapper.toDto(entity);
  }

  public Map<String,List<ChildPurchaseOrderResponseDto>> getBySourceTargetMapping(Long sourceManufacturerId, Long targetManufacturerId){
    List<PurchaseOrder> purchaseOrderList = purchaseOrderManager.findBySourceTargetMapping(sourceManufacturerId, targetManufacturerId);
    return purchaseOrderList.stream().collect(Collectors.groupingBy(PurchaseOrder::getPurchaseOrderId,
            Collectors.mapping(purchaseOrder -> {
              ChildPurchaseOrderResponseDto dto = new ChildPurchaseOrderResponseDto();
              dto.setChildPurchaseOrderId(purchaseOrder.getChildPurchaseOrderId());
              dto.setMaxDispatchableQuantity(purchaseOrder.getMaxDispatchableQuantity() - purchaseOrder.getDispatchedQuantity());
              dto.setCommodityId(purchaseOrder.getCommodityId());
              dto.setCommodityType(commodityMap.get(purchaseOrder.getCommodityId() == null ? "CMD017" : purchaseOrder.getCommodityId()));
              return dto;
            }, Collectors.toList())));
  }

  public Double getByPurchaseOrderIds(String purchaseOrderId, String childPurchaseOrderId){
    PurchaseOrder po = purchaseOrderManager.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId);
    if(po == null) return 0D;
    return po.getMaxDispatchableQuantity() - po.getDispatchedQuantity();
  }

  public void updateQuantityByPurchaseOrderIds(String purchaseOrderId, String childPurchaseOrderId, Double quantity){
    PurchaseOrder po = purchaseOrderManager.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId);
    if(po == null){
      throw new CustomException("No entry found for given purchaseOrderId and childPurchaseOrderId", HttpStatus.BAD_REQUEST);
    }
    po.setDispatchedQuantity(po.getDispatchedQuantity() + quantity);
    purchaseOrderManager.update(po);
  }

  public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByManufacturer(ManufacturerDispatchableQuantityRequestDto dto){
    return purchaseOrderManager.getManufacturerDispatchableQuantityAggregate(dto);
  }

  public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByDistrictGeoId(ManufacturerDispatchableQuantityRequestDto dto){
    List<ManufacturerDispatchableQuantityResponseDto> responseDtos = purchaseOrderManager.getDispatchableQuantityByDistrictGeoId(dto);
    List<Long> testManufacturerIds = manufacturerManager.getTestManufacturerIds();
    List<Long> filteredManufacturerIds = manufacturerCategoryManager.filterManufacturersByCategory(dto.getCategoryId(), responseDtos.stream().map(ManufacturerDispatchableQuantityResponseDto::getId).toList());
    return responseDtos.stream()
            .filter(d -> filteredManufacturerIds.contains(d.getId()) && !testManufacturerIds.contains(d.getId()))
            .toList();
  }

  public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByDistrictGeoIdForTarget(ManufacturerDispatchableQuantityRequestDto dto){
    List<ManufacturerDispatchableQuantityResponseDto> responseDtos = purchaseOrderManager.getDispatchableQuantityByDistrictGeoIdForTarget(dto);
    List<Long> testManufacturerIds = manufacturerManager.getTestManufacturerIds();
    List<Long> filteredManufacturerIds = manufacturerCategoryManager.filterManufacturersByCategory(dto.getCategoryId(), responseDtos.stream().map(ManufacturerDispatchableQuantityResponseDto::getId).toList());
    return responseDtos.stream()
            .filter(d -> filteredManufacturerIds.contains(d.getId()) && !testManufacturerIds.contains(d.getId()))
            .toList();
  }

  private List<PurchaseOrder> toEntityList(PurchaseOrderRequestDto dto) {
    String date = dto.getAllotedMonth().toString();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
    Date allotedMonthDate = new Date();
    try {
      allotedMonthDate = formatter.parse(date);
    } catch (ParseException e) {
      log.info("Error occurred while parsing alloted month date");
    }

    Date finalAllotedMonthDate = allotedMonthDate;

    return dto.getBufferGodownDetails().stream().map(bgd -> {
      PurchaseOrder entity = new PurchaseOrder();
      entity.setPurchaseOrderId(dto.getMoTransactionId());
      entity.setDmOrderNo(dto.getDmOrderNo());
      entity.setDmOrderDate(dto.getDmorderDate());
      entity.setDmDelieverDate(dto.getDmDeliverDate());
      entity.setExternalSourceManufacturerId(dto.getManufacturingid());
      entity.setExternalTargetManufacturerId(bgd.getBufferGodownId());
      entity.setMaxDispatchableQuantity(bgd.getAllotedQty());
      entity.setAllotedMonth(finalAllotedMonthDate);
      entity.setDispatchedQuantity(0D);
      entity.setCommodityId(dto.getCommodityId());
      entity.setChildPurchaseOrderId(bgd.getMoDestinationId());
      entity.setExternalMetaDataSet(dto.getExternalMetaDataRequestDtos().stream()
              .map(md -> new ExternalMetaData(null, md.getName(), md.getValue(), entity))
              .collect(Collectors.toSet()));
      return entity;
    }).toList();

  }
}
