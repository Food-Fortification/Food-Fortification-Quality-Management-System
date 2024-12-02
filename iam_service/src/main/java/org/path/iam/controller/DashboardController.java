package org.path.iam.controller;

import org.path.iam.enums.GeoType;
import org.path.iam.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manufacturers")
@Tag(name = "Dashboard Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/count")
    public ResponseEntity<?> manufacturerCountByGeoId(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam GeoType type,
                                                      @RequestParam String geoId){
        return ResponseEntity.ok(dashboardService.getCategoryCounts(categoryId, type, geoId));
    }
    @GetMapping("categories/count")
    public ResponseEntity<?> manufacturerCategoriesCountByGeoId(@RequestParam GeoType type,
                                                                @RequestParam String geoId){
        return ResponseEntity.ok(dashboardService.getCategoriesCounts(type, geoId));
    }


    @GetMapping("/list")
    public ResponseEntity<?> manufacturerListByGeoId(@RequestParam(required = false) Long categoryId,
                                                     @RequestParam GeoType type,
                                                     @RequestParam String geoId,
                                                     @RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String search){
        return ResponseEntity.ok(dashboardService.getManufacturerListByGeoId(categoryId, type, geoId, search, pageNumber, pageSize));
    }
}
