package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.GeoAggregationType;
import com.beehyv.fortification.enums.GeoManufacturerQuantityResponseType;
import com.beehyv.fortification.service.BatchService;
import com.beehyv.fortification.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Dashboard Controller")
@RequestMapping("dashboard")
public class DashboardController {

    private DashboardService dashboardService;
    private BatchService batchService;

    @PostMapping(value = "/count")
    public ResponseEntity<List<StateGeoDto>> getCount(@RequestParam(required = false) Integer year, @RequestBody DashboardRequestDto dto) {
        List<StateGeoDto> response = dashboardService.getDashboardCountData(year, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("list/batches")
    public ResponseEntity<ListResponse<BatchListResponseDTO>> getAllBatches(@RequestParam(required = false) Integer pageNumber,
                                                                            @RequestParam(required = false) Integer pageSize,
                                                                            @RequestBody DashboardRequestDto dto,
                                                                            @RequestParam(required = false) String responseType,
                                                                            @RequestParam Long manufacturerId,
                                                                            @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<BatchListResponseDTO> batches = batchService.getAllBatchesInSuperMonitor(responseType, dto.getFromDate(),dto.getToDate(),dto.getCategoryId(), pageNumber, pageSize, searchListRequest,manufacturerId);
        return new ResponseEntity<>(batches, HttpStatus.OK);
    }

    @PostMapping("/geography/manufacturers/{responseType}")
    public ResponseEntity<?> getManufacturerQuantities(
            @PathVariable GeoManufacturerQuantityResponseType responseType,
            @RequestParam(required = false) Long categoryId,
            @RequestParam GeoAggregationType type,
            @RequestParam String geoId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) @Min(1) Integer pageNumber,
            @RequestParam(required = false) @Min(5) Integer pageSize,
            @RequestParam(required = false) String search,
            @RequestBody DashboardRequestDto dto
    ) {
        ListResponse<?> results = dashboardService.getManufacturersQuantities(responseType, categoryId, type, geoId, search, year, pageNumber, pageSize, dto);
        return ResponseEntity.ok(results);
    }

    @GetMapping("recompile/{stateTypeName}")
    public ResponseEntity<String> recompileGeoData(@PathVariable String stateTypeName) {
        StateType stateType = StateType.valueOf(stateTypeName.toUpperCase());
        dashboardService.recompileGeoData(stateType);
        return ResponseEntity.ok("Success");
    }
}
