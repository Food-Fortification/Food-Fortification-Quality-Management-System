package org.path.iam.manager;

import org.path.iam.dao.PurchaseOrderDao;
import org.path.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import org.path.iam.dto.responseDto.ManufacturerDispatchableQuantityResponseDto;
import org.path.iam.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseOrderManager extends BaseManager<PurchaseOrder, PurchaseOrderDao> {

  private final PurchaseOrderDao dao;
  public PurchaseOrderManager(PurchaseOrderDao dao) {
    super(dao);
    this.dao = dao;
  }

  public List<PurchaseOrder> findBySourceTargetMapping(Long sourceManufacturerId, Long targetManufacturerId){
    return dao.findBySourceTargetMapping(sourceManufacturerId, targetManufacturerId);
  }

  public Boolean findExistingPurchaseOrder(String purchaseOrderId){
    return dao.findExistingPurchaseOrder(purchaseOrderId);
  }

  public PurchaseOrder findByPurchaseOrderIds(String purchaseOrderId, String childPurchaseOrderId){
    return dao.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId);
  }

  public List<ManufacturerDispatchableQuantityResponseDto> getManufacturerDispatchableQuantityAggregate(ManufacturerDispatchableQuantityRequestDto dto){
    return dao.getManufacturerDispatchableQuantityAggregate(dto);
  }

  public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByDistrictGeoId(ManufacturerDispatchableQuantityRequestDto dto){
    return dao.getDispatchableQuantityByDistrictGeoId(dto);
  }

  public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByDistrictGeoIdForTarget(ManufacturerDispatchableQuantityRequestDto dto){
    return dao.getDispatchableQuantityByDistrictGeoIdForTarget(dto);
  }
}
