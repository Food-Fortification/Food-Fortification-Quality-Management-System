package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.ManufacturerRequestDto;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.ManufacturerDetailsResponseDto;
import com.beehyv.iam.dto.responseDto.ManufacturerResponseDto;
import com.beehyv.iam.enums.GeoType;
import com.beehyv.iam.enums.UserType;
import com.beehyv.iam.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/manufacturer")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    @PostMapping
    public ResponseEntity<?> createManufacturer(@Valid @RequestBody ManufacturerRequestDto manufacturerRequestDto){
        manufacturerService.create(manufacturerRequestDto, null);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @PostMapping("/material-manufacturers")
    public ResponseEntity<?> createManufacturerMaterialSources(@Valid @RequestBody ManufacturerRequestDto manufacturerRequestDto){
        return new ResponseEntity<>(manufacturerService.createManufacturer(manufacturerRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllManufacturers(@RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize,
        @RequestParam(required = false) String search) {
        return ResponseEntity.ok(manufacturerService.getALlManufacturers(search, pageNumber, pageSize));
    }

    @GetMapping("/material-manufacturers/{categoryId}")
    public ResponseEntity<?> getAllSourceMaterialManufacturers(@RequestParam(required = false) Integer pageNumber,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String search,
                                                               @PathVariable Long categoryId) {
        ListResponse<ManufacturerResponseDto> manufacturers = manufacturerService.getAllMaterialManufacturers(categoryId, search, pageNumber, pageSize);
        return ResponseEntity.ok(manufacturers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long manufacturerId){
        return ResponseEntity.ok(manufacturerService.getManufacturerByID(manufacturerId));
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<?> getManufacturerNameById(@PathVariable("id") Long manufacturerId){
        return ResponseEntity.ok(manufacturerService.getManufacturerNameById(manufacturerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManufacturer(@PathVariable("id") Long manufacturerId,
                                                @Valid @RequestBody ManufacturerRequestDto manufacturerRequestDto){
        manufacturerRequestDto.setId(manufacturerId);
        manufacturerService.update(manufacturerRequestDto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturerById(@PathVariable("id") Long manufacturerId){
        manufacturerService.delete(manufacturerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/manufacturer-details")
    public ResponseEntity<?> getManufacturerDetailsForLoggedInUser(@RequestParam(required = false) Long manufacturerId ){
        return ResponseEntity.ok(manufacturerService.getManufacturerDetails(manufacturerId));
    }
    @GetMapping(path = "/list/details")
    public ResponseEntity<?> listDetails(@RequestParam List<Long> ids){
        return new ResponseEntity<>(manufacturerService.getListDetails(ids),HttpStatus.OK);
    }
    @GetMapping(path = "/{type}/list")
    public ResponseEntity<?> getListByType(@PathVariable String type,
                                           @RequestParam(required = false) String search,
                                           @RequestParam(required = false) Long manufacturerId,
                                           @RequestParam(required = false) Long category_id,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize){
        return new ResponseEntity<>(manufacturerService.getByType(type, search, category_id, manufacturerId, pageNumber, pageSize),HttpStatus.OK);
    }

    @GetMapping(path = "/roleCategories/{manufacturerId}")
    public ResponseEntity<?> getAllRoleCategoriesForManufacturerId(
        @PathVariable("manufacturerId") Long manufacturerId) {
        return new ResponseEntity<>(manufacturerService.getAllRoleCategoriesForManufacturer(manufacturerId),
            HttpStatus.OK);
    }

    @GetMapping(path = "/categoryIds")
    public ResponseEntity<?> getAllManufacturerFromCategoryIds(
        @RequestParam List<Long> categoryIds,
        @RequestParam(required = false) UserType type,
        @RequestParam(required = false)String search,
        @RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(manufacturerService.getAllManufacturerFromCategoryIds(search, type, categoryIds, pageNumber, pageSize),HttpStatus.OK);
    }
    @GetMapping("/test-manufacturers")
    public ResponseEntity<?> getTestManufacturerIds(){
        return new ResponseEntity<>(manufacturerService.getTestManufacturerIds() , HttpStatus.OK);
    }

    @GetMapping("/geo/list")
    public ResponseEntity<ListResponse<ManufacturerDetailsResponseDto>> getAllManufacturersWithGeoFilter(@RequestParam(required = false) String search,
                                                                                                         @RequestParam(required = false) Long stateId,
                                                                                                         @RequestParam(required = false) Long districtId,
                                                                                                         @RequestParam(required = false) Integer pageNumber,
                                                                                                         @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(manufacturerService.getAllManufacturersWithGeoFilter(search, stateId, districtId, pageNumber, pageSize));
    }

    @PostMapping("/filter/list")
    public ResponseEntity<ListResponse<ManufacturerDetailsResponseDto>> getAllManufacturersBySearchAndFilter(@RequestBody(required = false) SearchListRequest searchRequest,
                                                                                                             @RequestParam(required = false) Integer pageNumber,
                                                                                                             @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(manufacturerService.getAllManufacturersBySearchAndFilter(searchRequest, pageNumber, pageSize));
    }

    @GetMapping("agency/{agency}/list")
    public ResponseEntity<?> getManufacturerIdsByAgency(@PathVariable String agency){
        return new ResponseEntity<>(manufacturerService.getManufacturerIdsByAgency(agency), HttpStatus.OK);
    }

    @PostMapping("list")
    public ResponseEntity<?> getManufacturerAgenciesByIds(@RequestParam(required = false) Long categoryId,
                                              @RequestParam GeoType type,
                                              @RequestParam String geoId,
                                              @RequestBody List<Long> manufacturerIds){
        return new ResponseEntity<>(manufacturerService.getManufacturerAgenciesByIds(categoryId, type, geoId, manufacturerIds), HttpStatus.OK);
    }

    @GetMapping("/{id}/districtGeoId")
    public ResponseEntity<?> getDistrictGeoIdByManufacturerId(@PathVariable Long id){
        return new ResponseEntity<>(manufacturerService.getDistrictGeoIdByManufacturerId(id), HttpStatus.OK);
    }

    @PostMapping("filter/category/{categoryId}")
    public ResponseEntity<?> filterManufacturersByCategoryId(@PathVariable Long categoryId, @RequestBody List<Long> manufacturerIds){
        return new ResponseEntity<>(manufacturerService.filterManufacturersByCategoryId(categoryId, manufacturerIds), HttpStatus.OK);
    }

    @PostMapping("list/manufacturer-name")
    public ResponseEntity<?> getManufacturerNamesByIds(@RequestBody List<Long> manufacturerIds){
        return new ResponseEntity<>(manufacturerService.getManufacturerNamesByIds(manufacturerIds), HttpStatus.OK);
    }

    @PostMapping("list/manufacturer-name/category/{categoryId}")
    public ResponseEntity<?> getManufacturerNamesByIdsAndCategoryId(@RequestBody List<Long> manufacturerIds, @PathVariable Long categoryId){
        return new ResponseEntity<>(manufacturerService.getManufacturerNamesByIdsAndCategoryId(manufacturerIds, categoryId), HttpStatus.OK);
    }
}
