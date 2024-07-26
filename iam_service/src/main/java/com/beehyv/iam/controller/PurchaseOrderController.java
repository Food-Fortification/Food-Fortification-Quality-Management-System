package com.beehyv.iam.controller;

import com.beehyv.iam.dto.external.PurchaseOrderRequestDto;
import com.beehyv.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import com.beehyv.iam.service.PurchaseOrderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/purchase-order")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class PurchaseOrderController {
  private final PurchaseOrderService purchaseOrderService;

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody PurchaseOrderRequestDto purchaseOrderRequestDto){
    purchaseOrderService.create(purchaseOrderRequestDto);
    return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PurchaseOrderRequestDto dto){
    dto.setId(id);
    purchaseOrderService.update(dto);
    return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    purchaseOrderService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id){
    return ResponseEntity.ok(purchaseOrderService.getById(id));
  }

  @GetMapping("/get-by-mapping")
  public ResponseEntity<?> getBySourceTargetMapping(@RequestParam Long sourceManufacturerId,
                                                    @RequestParam Long targetManufacturerId) {
    return ResponseEntity.ok(purchaseOrderService.getBySourceTargetMapping(sourceManufacturerId, targetManufacturerId));
  }

  @GetMapping("/dispatchable-quantity")
  public ResponseEntity<?> getByPurchaseOrderIds(@RequestParam String purchaseOrderId,
                                                    @RequestParam String childPurchaseOrderId) {
    return ResponseEntity.ok(purchaseOrderService.getByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId));
  }

  @PutMapping("/dispatchable-quantity")
  public ResponseEntity<?> updateQuantityByPurchaseOrderIds(@RequestParam String purchaseOrderId,
                                                            @RequestParam String childPurchaseOrderId,
                                                            @RequestParam Double quantity) {
    purchaseOrderService.updateQuantityByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId, quantity);
    return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
  }

  @PostMapping("/manufacturer/total-quantity")
  public ResponseEntity<?> getDispatchableQuantityByManufacturer(@RequestBody ManufacturerDispatchableQuantityRequestDto dto) {
    return new ResponseEntity<>(purchaseOrderService.getDispatchableQuantityByManufacturer(dto),HttpStatus.OK);
  }

  @PostMapping("/districtGeoId/total-quantity")
  public ResponseEntity<?> getDispatchableQuantityByDistrictGeoId(@RequestBody ManufacturerDispatchableQuantityRequestDto dto) {
    return new ResponseEntity<>(purchaseOrderService.getDispatchableQuantityByDistrictGeoId(dto),HttpStatus.OK);
  }

  @PostMapping("/districtGeoId/total-quantity-target")
  public ResponseEntity<?> getDispatchableQuantityByDistrictGeoIdForTarget(@RequestBody ManufacturerDispatchableQuantityRequestDto dto) {
    return new ResponseEntity<>(purchaseOrderService.getDispatchableQuantityByDistrictGeoIdForTarget(dto),HttpStatus.OK);
  }
}
