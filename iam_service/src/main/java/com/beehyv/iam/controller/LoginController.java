package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.LoginRequestDto;
import com.beehyv.iam.service.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
@RequiredArgsConstructor
@Tag(name = "Login Controller")
@CrossOrigin(origins = {"*"})
public class LoginController {

   private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, @RequestParam String mobileVersion){
        AccessTokenResponse accessTokenResponse = loginService.login(loginRequestDto, mobileVersion);
        return ResponseEntity.ok(accessTokenResponse);
    }
    @DeleteMapping(path = "/logout")
    public ResponseEntity<?> logout(){
        loginService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(loginService.refreshToken(refreshToken));
    }
    @GetMapping("/details")
    public ResponseEntity<?> userDetails(){
        return ResponseEntity.ok(loginService.getUserDetails());
    }
}
