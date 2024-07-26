package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.QuantityAggregatesExcelRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.GeoAggregationType;
import com.beehyv.fortification.enums.GeoManufacturerProductionResponseType;
import com.beehyv.fortification.enums.GeoManufacturerQuantityResponseType;
import com.beehyv.fortification.enums.GeoManufacturerTestingResponseType;
import com.beehyv.fortification.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/count")
    public ResponseEntity<List<StateGeoDto>> getCount(@RequestParam(required = false) Integer year, @RequestBody DashboardRequestDto dto) {
        List<StateGeoDto> response = dashboardService.getDashboardCountData(year, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
