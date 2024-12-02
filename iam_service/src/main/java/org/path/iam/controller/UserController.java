package org.path.iam.controller;


import org.path.iam.dto.requestDto.LoginRequestDto;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.UserListResponseDto;
import org.path.iam.enums.UserType;
import org.path.iam.service.UserService;
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
    public ResponseEntity<?> addUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(service.addUser(userRequestDto, false), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @PostMapping(path = "/all-users")
    public ResponseEntity<?> getAllUsers(@RequestBody(required = false) SearchListRequest searchListRequest,
                                         @RequestParam(required = false) UserType type,
                                         @RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize) {
        ListResponse<UserListResponseDto> allUsers = service.getAllUsers(searchListRequest, pageNumber, pageSize, type);
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequestDto userRequestDto) {
        service.updateUser(userId, userRequestDto);
        return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody LoginRequestDto dto) {
        service.resetPassword(dto);
        return ResponseEntity.ok("Successfully Reset Password");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam("userName") String userName) {
        service.deleteUser(userName);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/check-details")
    public ResponseEntity<?> checkUserDetails(@RequestParam(required = false) String userName,
                                              @RequestParam(required = false) String email) {
        return ResponseEntity.ok(service.checkUserDetails(userName, email));
    }

    @PostMapping(path = "admin/all-users")
    public ResponseEntity<?> getAllUsersForAdmin(@RequestBody(required = false) SearchListRequest searchListRequest,
                                                 @RequestParam(required = false) UserType type,
                                                 @RequestParam(required = false) Integer pageNumber,
                                                 @RequestParam(required = false) Integer pageSize) {
        ListResponse<UserListResponseDto> allUsers = service.getAllUsersForAdmin(searchListRequest, pageNumber, pageSize, type);
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping("/notification/last-seen")
    public ResponseEntity<?> updateNotificationLastSeenTime() {
        service.updateNotificationLastSeenTime();
        return ResponseEntity.ok("Last read time has been updated.");
    }

    @GetMapping("/notification/last-seen")
    public ResponseEntity<?> getNotificationLastSeenTime() {
        return ResponseEntity.ok(service.getNotificationLastSeenTime());
    }

}
