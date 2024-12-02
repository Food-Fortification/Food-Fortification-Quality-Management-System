package org.path.lab.controller;

import org.path.lab.dto.responseDto.AggregatedResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleStateGeoLabResponseDto;
import org.path.lab.enums.GeoAggregationType;
import org.path.lab.service.impl.DashboardServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dashboard Controller")
@RequestMapping("dashboard")
@CrossOrigin(origins = {"*"})
public class DashboardController {


    @Autowired
    private DashboardServiceImpl dashboardService;

    @GetMapping("/count")
    public ResponseEntity<?> getCount(@QueryParam("from_date") String create_date_start,
                                      @QueryParam("to_date") String create_date_end){
       Object response = dashboardService.getCount(create_date_start, create_date_end);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("labs/count")
    public ResponseEntity<?> labsCountByGeoId(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam GeoAggregationType type,
                                                      @RequestParam String geoId){
        return ResponseEntity.ok(dashboardService.getCategoryCounts(categoryId, type, geoId));
    }
    @GetMapping("categories/count")
    public ResponseEntity<?> labCategoriesCountByGeoId(@RequestParam GeoAggregationType type,
                                                                @RequestParam String geoId){
        return ResponseEntity.ok(dashboardService.getCategoriesCounts(type, geoId));
    }

    @GetMapping("/geography/count/samples")
    public ResponseEntity<?> getAggregatedSampleStatesCount(
            @RequestParam(required = false) Long categoryId,
            @RequestParam GeoAggregationType type,
            @RequestParam(required = false) Integer year,
            @RequestParam String geoId
    ) {
        AggregatedResponseDto results = dashboardService.getAggregatedSampleStatesCount(categoryId, type, geoId, year);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/geography/labs")
    public ResponseEntity<ListResponse<SampleStateGeoLabResponseDto>> getLabsGeoSampleCount(
            @RequestParam(required = false) Long categoryId,
            @RequestParam GeoAggregationType type,
            @RequestParam String geoId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) @Min(1) Integer pageNumber,
            @RequestParam(required = false) @Min(5) Integer pageSize,
            @RequestParam(required = false) String search
    ) {
        ListResponse<SampleStateGeoLabResponseDto> results = dashboardService.getSampleSateGeoCountLabs(categoryId, type, geoId, search,
                year, pageNumber, pageSize);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/recompile")
    public ResponseEntity<String> recompileSampleGeoData() {
        dashboardService.recompile();
        return ResponseEntity.ok("Success");
    }

}
