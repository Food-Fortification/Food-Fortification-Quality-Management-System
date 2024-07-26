package com.beehyv.iam.controller;


import com.beehyv.iam.dto.external.FssaiRequestDto;
import com.beehyv.iam.dto.requestDto.LoginRequestDto;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.requestDto.UserRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.UserListResponseDto;
import com.beehyv.iam.enums.UserType;
import com.beehyv.iam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
@CrossOrigin(origins = {"*"})
public class UserController {


   private final UserService service;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(service.addUser(userRequestDto, false),HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getUser(id));
    }

    @PostMapping(path = "/all-users")
    public ResponseEntity<?> getAllUsers(@RequestBody(required = false) SearchListRequest searchListRequest,
                                         @RequestParam(required = false) UserType type,
                                         @RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize){
        ListResponse<UserListResponseDto> allUsers = service.getAllUsers(searchListRequest, pageNumber, pageSize, type);
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequestDto userRequestDto){
        service.updateUser(userId, userRequestDto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @PutMapping(path = "/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody LoginRequestDto dto){
        service.resetPassword(dto);
        return ResponseEntity.ok("Successfully Reset Password");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam("userName") String userName){
        service.deleteUser(userName);
        return ResponseEntity.ok().build();
    }
    @GetMapping(path = "/check-details")
    public ResponseEntity<?> checkUserDetails(@RequestParam(required = false) String userName,
                                              @RequestParam(required = false)String email){
        return ResponseEntity.ok(service.checkUserDetails(userName, email));
    }
  @PostMapping(path = "admin/all-users")
  public ResponseEntity<?> getAllUsersForAdmin(@RequestBody(required = false) SearchListRequest searchListRequest,
      @RequestParam(required = false) UserType type,
      @RequestParam(required = false) Integer pageNumber,
      @RequestParam(required = false) Integer pageSize){
    ListResponse<UserListResponseDto> allUsers = service.getAllUsersForAdmin(searchListRequest, pageNumber, pageSize, type);
    return ResponseEntity.ok(allUsers);
  }

  @PutMapping("/notification/last-seen")
  public ResponseEntity<?> updateNotificationLastSeenTime(){
      service.updateNotificationLastSeenTime();
    return ResponseEntity.ok("Last read time has been updated.");
  }

  @GetMapping("/notification/last-seen")
  public ResponseEntity<?> getNotificationLastSeenTime(){
    return ResponseEntity.ok(service.getNotificationLastSeenTime());
  }

    @PostMapping("/redirect-url")
    public ResponseEntity<?> fssaiRedirectUrl(@RequestHeader("API-ACCESS-KEY") String apiAccessKey, @RequestBody FssaiRequestDto dto){
        return ResponseEntity.ok(service.fssaiRedirectUrl(apiAccessKey, dto));
    }

    @PostMapping("/checkUser")
    public ResponseEntity<?> checkUser(@RequestParam String licenseNo){
        return ResponseEntity.ok(service.checkUser(licenseNo));
    }

    @PostMapping("/encoded-password")
    public ResponseEntity<?> generatePassword(@RequestParam String licenseNo){
        return ResponseEntity.ok(service.generateEncodedPassword(licenseNo));
    }
}
