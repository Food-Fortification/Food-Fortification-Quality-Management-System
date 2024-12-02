package org.path.iam.service;

import org.path.iam.config.KeycloakCustomConfig;
import org.path.iam.dto.requestDto.LoginRequestDto;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.requestDto.UserRoleCategoryRequestDto;
import org.path.iam.dto.responseDto.*;
import org.path.iam.manager.*;
import org.path.iam.model.LabUsers;
import org.path.iam.model.Manufacturer;
import org.path.iam.model.User;
import org.path.iam.model.UserRoleCategory;
import org.path.iam.enums.RoleCategoryType;
import org.path.iam.enums.UserType;
import org.path.iam.exception.KeycloakException;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.helper.FortificationRestHelper;
import org.path.iam.helper.LabRestHelper;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.UserMapper;
import org.path.iam.mapper.UserRoleCategoryMapper;
import org.path.iam.utils.Credentials;
import org.path.iam.utils.DtoMapper;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final KeycloakCustomConfig keycloakCustomConfig;
    private final BaseMapper<UserResponseDto, UserRequestDto, User> mapper = BaseMapper.getForClass(UserMapper.class);
    private final BaseMapper<UserRoleCategoryResponseDto, UserRoleCategoryRequestDto, UserRoleCategory> userRoleCategoryMapper = BaseMapper.getForClass(UserRoleCategoryMapper.class);
    private final UserManager userManager;
    private final RoleService roleService;
    private final LabUsersManager labUsersManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);
    private final PasswordEncoder passwordEncoder;
    private final ManufacturerManager manufacturerManager;
    private final KeycloakInfo keycloakInfo;
    private final AddressService addressService;
    private final ManufacturerCategoryService manufacturerCategoryService;
    private final UserRoleCategoryManager userRoleCategoryManager;
    private final StatusManager statusManager;

    @Value("${service.lab.baseUrl}")
    private String labBaseUrl;

    @Value("${config.realm.resource}")
    private String realmResource;

    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;

    @Transactional(rollbackFor = Exception.class)
    public Long addUser(UserRequestDto userRequestDto, boolean isSuperAdmin) {
        User userNameInDb = userManager.findByName(userRequestDto.getUserName());
        User userEmailInDb = userManager.findByEmail(EncryptionHelper.encrypt(userRequestDto.getEmail()));
        if (userNameInDb != null) throw new CustomException("User Name already Exists", HttpStatus.BAD_REQUEST);
        if (userEmailInDb != null) throw new CustomException("Email already Exists", HttpStatus.BAD_REQUEST);
        Map<String, List<String>> attributes = new HashMap<>();
        List<String> manufacturerId = new ArrayList<>();
        List<String> manufacturerName = new ArrayList<>();
        List<String> manufacturerAddress = new ArrayList<>();
        List<String> labId = new ArrayList<>();
        if (userRequestDto.getManufacturerId() != null) {
            manufacturerId.add(String.valueOf(userRequestDto.getManufacturerId()));
            Manufacturer manufacturer = manufacturerManager.findById(userRequestDto.getManufacturerId());
            manufacturerName.add(manufacturer.getName());
            manufacturerAddress.add(addressService.getCompleteAddressForManufacturer(userRequestDto.getManufacturerId()));
            attributes.put("manufacturerId", manufacturerId);
            attributes.put("manufacturerName", manufacturerName);
            attributes.put("manufacturerAddress", manufacturerAddress);
        } else if (userRequestDto.getLabId() != null) {
            labId.add(String.valueOf(userRequestDto.getLabId()));
            attributes.put("labId", labId);
            this.setLabAttributes(userRequestDto.getLabId(), attributes);
        }
        checkPassword(userRequestDto.getPassword());
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userRequestDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userRequestDto.getUserName());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setAttributes(attributes);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setEmailVerified(true);

        User userToSave = mapper.toEntity(userRequestDto);
        if (userToSave.getStatus() == null) userToSave.setStatus(statusManager.findByName("Active"));
        userToSave.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userToSave.setIsDeleted(false);
        User userCreated = userManager.create(userToSave);
        if (userRequestDto.getLabId() != null && userRequestDto.getLabId() != 0) {
            LabUsers labUsers = new LabUsers(userRequestDto.getLabId(), Collections.singleton(userCreated));
            labUsers = labUsersManager.create(labUsers);
            userCreated.setLabUsers(labUsers);
            userManager.update(userCreated);
        }
        boolean superAdmin;
        if (isSuperAdmin){
            superAdmin = true;
        }else {
            Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
            superAdmin = roles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
        }
        if (!userRequestDto.getRolesMap().isEmpty() && !superAdmin) {
            List<Long> categoryIds = manufacturerCategoryService.getCategoriesForManufacturer(userRequestDto.getManufacturerId());
            boolean noMatch = categoryIds.stream().noneMatch(c -> userRequestDto.getRolesMap().stream().anyMatch(cid -> Objects.equals(cid.getCategoryId(), c)));
            if (noMatch) throw new KeycloakException("Cannot assign this role to user");
        }
        UsersResource instance = getInstance();
        Response response = instance.create(user);
        if (response.getStatus() == 201) {
            try {
                roleService.assignRole(userCreated, userRequestDto.getRolesMap(), isSuperAdmin);
            } catch (Exception e) {
                log.info("could not assign role to the user" + e.getMessage());
                e.printStackTrace();
            }
            return userCreated.getId();
        } else {
            throw new KeycloakException("Keycloak user Registration Error");
        }
    }

    private void setLabAttributes(Long labId, Map<String, List<String>> attributes) {
        RestTemplate restTemplate = new RestTemplate();
        String token = keycloakInfo.getAccessToken();
        String url = labBaseUrl + "lab/address/" + labId + "/lab-address";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("labIds", Collections.singletonList(labId))
                .build();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<NameAddressResponseDto> response;
        try {
            log.info("Url for lab address api: {}", url);
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, NameAddressResponseDto.class);
            log.info("Response: {{}}: ", response.getBody());
        } catch (Exception ex) {
            log.error("error at calling lab api for setting address in lab attributes");
            log.error(ex.getMessage());
            ex.printStackTrace();
            throw new KeycloakException("Could not create User");
        }
        String address = response.getBody().getCompleteAddress();
        String labName = response.getBody().getName();
        attributes.put("manufacturerAddress", Collections.singletonList(address));
        attributes.put("manufacturerName", Collections.singletonList(labName));
    }

    public UserResponseDto getUser(Long id) {
        User user = userManager.findById(id);
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        boolean isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
        boolean isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        String roleName=user.getRoleCategories().iterator().next().getRole().getName();
        long userOrganisationId=0;
        if(!"INSPECTION".equals(roleName)) {
            userOrganisationId= user.getManufacturer() != null ? user.getManufacturer().getId() : user.getLabUsers().getLabId();
        }
        if (isSuperAdmin || (isAdmin && (Objects.equals(userOrganisationId, manufacturerId)))) {
            UserResponseDto result = mapper.toDto(user);
            List<UserRoleCategory> roleCategories = userRoleCategoryManager.findAllByUserId(id);
            if (!Objects.isNull(roleCategories) && !roleCategories.isEmpty()) {
                Set<UserRoleCategoryResponseDto> roleCategoryResponseDtos = roleCategories.stream()
                        .map(userRoleCategoryMapper::toDto)
                        .collect(Collectors.toSet());
                result.setRoleCategory(roleCategoryResponseDtos);
            }
            return result;
        }else {
            throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(LoginRequestDto dto) {
        checkPassword(dto.getPassword());
        User existingUser = userManager.findById(dto.getUserId());
        if(existingUser.getRoleCategories().stream().anyMatch(r -> Objects.equals(r.getRole().getName(), "ADMIN"))){
            throw new CustomException("Admin password cannot be changed, contact administrator", HttpStatus.BAD_REQUEST);
        }
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        boolean isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
        boolean isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        long userOrganisationId = existingUser.getManufacturer() != null ? existingUser.getManufacturer().getId(): existingUser.getLabUsers().getLabId();
        if (isSuperAdmin || (isAdmin && (Objects.equals(userOrganisationId, manufacturerId)))){
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            userManager.update(existingUser);
            CredentialRepresentation credentials = Credentials
                    .createPasswordCredentials(dto.getPassword());
            String keycloakUserId = getKeycloakUserId(existingUser.getUserName()).getId();
            UserResource userResource = getInstance().get(keycloakUserId);
            userResource.resetPassword(credentials);
        }else {
            throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        }

    }

    public UserRepresentation getKeycloakUserId(String userName) {
        RealmResource keycloakRealmResource = keycloakCustomConfig.getInstance().realm(realmResource);
        return keycloakRealmResource
                .users()
                .search(userName)
                .get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long userId, UserRequestDto userRequestDto) {
        User existingUser = userManager.findById(userId);
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        boolean isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
        boolean isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        long userOrganisationId = existingUser.getManufacturer() != null ? existingUser.getManufacturer().getId(): existingUser.getLabUsers().getLabId();
        if (isSuperAdmin || (isAdmin && (Objects.equals(userOrganisationId, manufacturerId)))){
            UserRepresentation existingKeycloakUser = getKeycloakUserId(existingUser.getUserName());
            String keycloakUserId = existingKeycloakUser.getId();
            UserRepresentation user = new UserRepresentation();
            BeanUtils.copyProperties(existingKeycloakUser, user);
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            if("Active".equals(statusManager.findById(userRequestDto.getStatusId()).getName()) || userRequestDto.getStatusId()==null) {
                user.setEnabled(true);
            }
            else {
                user.setEnabled(false);
            }
            User userToUpdate = UserMapper.toEntity(userRequestDto, existingUser);
            userToUpdate.setStatus(statusManager.findById(userRequestDto.getStatusId()));
            userManager.update(userToUpdate);
            UserResource userResource = getInstance().get(keycloakUserId);
            if (userResource != null) {
                userResource.update(user);
            } else {
                throw new KeycloakException("Error at updating user in keycloak");
            }
        }else {
            throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        }

    }

    @Transactional
    public void deleteUser(String userName)  {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        boolean isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
        boolean isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
        User user = userManager.findByName(userName);
        long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        long userOrganisationId = user.getManufacturer() != null ? user.getManufacturer().getId(): user.getLabUsers().getLabId();
        if (isSuperAdmin || (isAdmin && (Objects.equals(userOrganisationId, manufacturerId)))){
            UsersResource usersResource = getInstance();
            List<UserRepresentation> users = usersResource.search(userName, true);
            if (users.size() != 0) {
                UserResource userResource = getInstance().get(users.get(0).getId());
                userResource.remove();
            }
            if (user != null) {
                userManager.delete(user.getId());
            } else {
                throw new KeycloakException("User not found with the given userName:" + userName);
            }
        }else {
            throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        }

    }

    public void checkPassword(String pwd) {
        if (pwd.length() < 8) throw new CustomException("Password should have minimum length of 8", HttpStatus.BAD_REQUEST);
        if (!pwd.matches(".*[0-9].*")) throw new CustomException("Password should have at least one digit", HttpStatus.BAD_REQUEST);
        if (!pwd.matches(".*[a-z].*")) throw new CustomException("Password should have at least one lower case letter", HttpStatus.BAD_REQUEST);
        if (!pwd.matches(".*[A-Z].*")) throw new CustomException("Password should have at least one upper case letter", HttpStatus.BAD_REQUEST);
        if (!pwd.matches(".*[%@#$^].*")) throw new CustomException("Password should have at least one special character (@,#,$,%,^)", HttpStatus.BAD_REQUEST);
        if (pwd.contains(" ")) throw new CustomException("Password should not have any space", HttpStatus.BAD_REQUEST);
    }


    public ListResponse<UserListResponseDto> getAllUsers(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize, UserType type) {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        boolean isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
        if (!isAdmin) throw new CustomException("Permission Denied", HttpStatus.NO_CONTENT);
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<UserRoleCategory> entities;
        Long count;
        if (type != null && type.equals(UserType.LAB)) {
            entities = userRoleCategoryManager.findAllLabUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize);
            count = userRoleCategoryManager.getCountForAllLabUsersBySearchAndFilter(searchListRequest);
        } else if (type != null && type.equals(UserType.INSPECTION)){
           entities = userRoleCategoryManager.findAllInspectionUsers(searchListRequest, pageNumber, pageSize);
           count = userRoleCategoryManager.getCountForInspectionUsers(searchListRequest);

        }else {
            entities = userRoleCategoryManager.findAllUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);
            count = userRoleCategoryManager.getCountForALlUsersBySearchAndFilter(searchListRequest, manufacturerId);
        }
        return ListResponse.from(entities, dtoMapper::mapToDto, count);
    }

    public ListResponse<UserListResponseDto> getAllUsersForAdmin(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize, UserType type) {
        List<UserRoleCategory> entities;
        Long count;
        if (type != null && type.equals(UserType.LAB)) {
            entities = userRoleCategoryManager.findAllLabUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize);
            count = userRoleCategoryManager.getCountForAllLabUsersBySearchAndFilter(searchListRequest);
        } else if (type != null && type.equals(UserType.INSPECTION)){
            entities = userRoleCategoryManager.findAllInspectionUsers(searchListRequest, pageNumber, pageSize);
            count = userRoleCategoryManager.getCountForInspectionUsers(searchListRequest);
        }else {
            entities = userRoleCategoryManager.findAllUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize, 0L);
            count = userRoleCategoryManager.getCountForALlUsersBySearchAndFilter(searchListRequest, 0L);
        }
        return ListResponse.from(entities, dtoMapper::mapToDto, count);
    }

    public UsersResource getInstance() {
        return keycloakCustomConfig.getInstance().realm(realmResource).users();
    }


    public boolean checkUserDetails(String userName, String email) {
        if (userName != null && email == null) {
            User user = userManager.findByName(userName);
            return user != null;
        } else if (userName == null && email != null) {
            User user = userManager.findByEmail(EncryptionHelper.encrypt(email));
            return user != null;
        } else {
            return false;
        }
    }

    public void updateNotificationLastSeenTime(){
        User loggedInuser = userManager.findByEmail(EncryptionHelper.encrypt(keycloakInfo.getUserInfo().getOrDefault("email", "").toString()));
        if(loggedInuser==null){
            throw new CustomException("User not found",HttpStatus.BAD_REQUEST);
        }
        loggedInuser.setNotificationLastSeenTime(LocalDateTime.now());
        userManager.update(loggedInuser);
    }

    public UserCategoryListResponseDto getNotificationLastSeenTime(){
        User loggedInuser = userManager.findByEmail(EncryptionHelper.encrypt(keycloakInfo.getUserInfo().getOrDefault("email", "").toString()));
        if(loggedInuser==null){
            throw new CustomException("User not found",HttpStatus.BAD_REQUEST);
        }
        List<String> categoryNames = null;
        List<Long> categoryIds = new ArrayList<>();
        UserCategoryListResponseDto dto = new UserCategoryListResponseDto();

        String fortificationUrl = fortificationBaseUrl + "fortification/category";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(fortificationUrl)
            .queryParam("independentBatch", false)
            .build();
        ListResponse<CategoryResponseDto> response = FortificationRestHelper.fetchCategoryListResponse(builder.toUriString(), keycloakInfo.getAccessToken());

        if(loggedInuser.getManufacturer() != null){
            categoryIds = loggedInuser.getManufacturer().getManufacturerCategories().stream().map(c -> c.getCategoryId()).toList();
            dto.setRoleCategoryType(RoleCategoryType.MODULE);
        } else if (loggedInuser.getLabUsers() != null) {
            String url = labBaseUrl + "lab/lab/category/labId/" + keycloakInfo.getUserInfo().getOrDefault("labId", 0L).toString();
            categoryIds = LabRestHelper.fetchCategoryIdsByLabId(url, keycloakInfo.getAccessToken());
            dto.setRoleCategoryType(RoleCategoryType.LAB);
        }

        List<Long> finalCategoryIds = categoryIds;
        categoryNames = response.getData().stream()
            .filter(r -> finalCategoryIds.contains(r.getId()))
            .map(r -> r.getName())
            .collect(Collectors.toList());

        dto.setCategoryNames(categoryNames);
        dto.setNotificationLastSeenTime(loggedInuser.getNotificationLastSeenTime());
        return dto;
    }
}
