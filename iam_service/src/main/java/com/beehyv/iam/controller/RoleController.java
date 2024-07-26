package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.AssignRoleRequestDto;
import com.beehyv.iam.dto.requestDto.RemoveRoleRequestDto;
import com.beehyv.iam.dto.requestDto.RoleRequestDto;
import com.beehyv.iam.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@CrossOrigin(origins = {"*"})
public class RoleController {

    private final RoleService roleService;
    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequestDto dto){
        return new ResponseEntity<>( roleService.assignRole(dto), HttpStatus.OK);
    }
    @PutMapping("/remove")
    public ResponseEntity<?> removeRole(@RequestBody RemoveRoleRequestDto dto) {
        roleService.removeRole(dto);
        return ResponseEntity.ok("Successfully Removed Role");
    }
    @GetMapping
    public ResponseEntity<?> getRoles(@RequestParam(required = false) Integer pageNumber,
                                      @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(roleService.getRoles(pageNumber,pageSize));
    }

    @PostMapping("/assign-lab-role")
    public ResponseEntity<?> assignLabRole(@RequestParam Long labId,
                                           @RequestParam String roleCategory,
                                           @RequestParam Long categoryId){
        roleService.assignLabRole(labId,roleCategory,categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * create list of roles given - used in dsl
     * @param roles
     * @return
     */
    @PostMapping("/create-roles")
    public ResponseEntity<?> createRoles(@RequestBody List<String> roles){
        roleService.createRoles(roles.stream()
                .map(role -> new RoleRequestDto(role, "", 0L, ""))
                .collect(Collectors.toList()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
