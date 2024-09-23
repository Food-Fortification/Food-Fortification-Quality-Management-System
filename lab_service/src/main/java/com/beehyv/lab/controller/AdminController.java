package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.LabSampleResponseDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.helper.RestHelper;
import com.beehyv.lab.service.LabSampleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin Controller")
@RequestMapping("admin")
@CrossOrigin(origins = {"*"})
public class AdminController {

    private final LabSampleService labSampleService;
    private final RestHelper restHelper;

    // move to dpg
    @PreAuthorize("@authorityChecker.hasSuperAdminAccessForCategory(#searchListRequest)")
    @PostMapping("samples")
    public ResponseEntity<ListResponse<LabSampleResponseDto>> getAllLabSamplesForSuperAdmin(
            @RequestBody SearchListRequest searchListRequest,
            @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<LabSampleResponseDto> dtoList = labSampleService.getAllLabSamplesForSuperAdmins(pageNumber, pageSize, searchListRequest);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
