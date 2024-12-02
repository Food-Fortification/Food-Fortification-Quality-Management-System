package org.path.lab.controller;

import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabSampleResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.helper.RestHelper;
import org.path.lab.service.LabSampleService;
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
