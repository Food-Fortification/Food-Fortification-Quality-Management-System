package org.path.iam.controller;

import org.path.iam.dto.requestDto.LoginRequestDto;
import org.path.iam.service.LoginService;
import org.path.parent.fileUpload.service.StorageClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
@RequiredArgsConstructor
@Tag(name = "Login Controller")
@CrossOrigin(origins = {"*"})
public class LoginController {

   private final LoginService loginService;

    @Autowired
    private StorageClient storageClient;
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


    @GetMapping("/storage/get-meta-file")
    public ResponseEntity<?> getFileFromS3(@RequestParam("key") String key,@RequestParam("type")String type)
    {
        Resource file = storageClient.getFile(key,type);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
