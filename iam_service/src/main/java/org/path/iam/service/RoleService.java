package org.path.iam.service;

import org.path.iam.config.KeycloakCustomConfig;
import org.path.iam.dto.requestDto.AssignRoleRequestDto;
import org.path.iam.dto.requestDto.RemoveRoleRequestDto;
import org.path.iam.dto.requestDto.RoleRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.RoleResponseDto;
import org.path.iam.manager.*;
import org.path.iam.model.*;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.RoleMapper;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
  @RequiredArgsConstructor
  @Transactional
  @Slf4j
  public class RoleService {
    private final RoleManager roleManager;
    private final KeycloakCustomConfig keycloakCustomConfig;
    private final UserManager userManager;
    private final UserRoleCategoryManager userRoleCategoryManager;
    private final KeycloakInfo keycloakInfo;
    private final StatusManager statusManager;
    private final BaseMapper<RoleResponseDto, RoleRequestDto, Role> mapper = BaseMapper.getForClass(RoleMapper.class);
    private final LabUsersManager labUsersManager;

    @Value("${config.realm.resource}")
    private String realmResource;
    @Transactional
    public List<Long> assignRole(User user, List<RoleRequestDto> rolesMap, boolean isSuperAdminProxy){
        boolean isSuperAdmin;
        boolean isAdmin;
        boolean isInspectionRole;
        long manufacturerId;
        if (!isSuperAdminProxy){
            Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
            isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
            isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
            isInspectionRole = rolesMap.stream().anyMatch(r -> r.getRoleName().equals("INSPECTION"));
            manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        }else {
            isSuperAdmin = true;
            isAdmin = false;
            isInspectionRole = false;
            manufacturerId = user.getManufacturer().getId();
        }
      long userOrganisationId;
      if (isInspectionRole){
        userOrganisationId = 0L;
      }else {
        userOrganisationId = user.getManufacturer() != null ? user.getManufacturer().getId(): user.getLabUsers().getLabId();
      }
      if (isSuperAdmin || (isAdmin && (Objects.equals(userOrganisationId, manufacturerId)))) {
        List<Long> userRoleCategoryIds = new ArrayList<>();
        userRoleCategoryIds=assignUserRole(user, rolesMap);
        return userRoleCategoryIds;
      }else {
        throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
      }

    }
    public List<Long> assignRole(AssignRoleRequestDto dto){
      User user = userManager.findByName(dto.getUserName());
       return this.assignRole(user, dto.getRoles(), false);
    }
    public void assignLabRole(Long labId, String roleCategory, Long categoryId){
      List<LabUsers> labUsers =  labUsersManager.findUsersByLabId(labId);
      for (LabUsers labUser : labUsers) {
        User user = userManager.findByLabUserId(labUser.getId());
        if (user != null) {
          Set<UserRoleCategory> userRoleCategories = user.getRoleCategories();
          if (!userRoleCategories.isEmpty()) {
              UserRoleCategory userRoleCategory = userRoleCategories.iterator().next();
              String roleName = userRoleCategory.getRole().getName();
              RoleRequestDto roleRequestDto = new RoleRequestDto();
              roleRequestDto.setRoleName(roleName);
              roleRequestDto.setRoleCategoryType("LAB");
              roleRequestDto.setCategoryId(categoryId);
              roleRequestDto.setCategoryName(roleCategory);
              List<RoleRequestDto> roleRequestDtoList = Collections.singletonList(roleRequestDto);
              log.info("assigning roles to the lab users");
              try {
                assignUserRole(user, roleRequestDtoList);
              } catch (CustomException e) {
                throw new CustomException("error while assigning role", HttpStatus.BAD_REQUEST);
              }
              log.info("lab users roles updated");
          }
        }
      }
    }
    public List<Long> assignUserRole(User user, List<RoleRequestDto> rolesMap){
        List<Long> userRoleCategoryIds = new ArrayList<>();
        List<Role> dbRoles = roleManager.findByNames(rolesMap.stream().map(RoleRequestDto::getRoleName).distinct().toList());
        List<String> roles = new ArrayList<>();
        List<UserRoleCategory> userRoleCategories = userRoleCategoryManager.findByUserId(user.getId());
        rolesMap.forEach(c-> {
            roles.add(String.format("%s_%s_%s",c.getCategoryName(),c.getRoleName(),c.getRoleCategoryType()));
            Long roleId = dbRoles.stream().filter(role ->Objects.equals(role.getName(),c.getRoleName())).map(Role::getId).findFirst().get();
            Status status = statusManager.findByName("Active");
            Optional<UserRoleCategory> matchingUserRoleCategory=  userRoleCategories.stream().filter(uc->uc.getCategory().equals(c.getCategoryName()) && uc.getUser().getId().equals(user.getId()) && uc.getRole().getId().equals(roleId)).findFirst();
            if(!matchingUserRoleCategory.isPresent()) {
                UserRoleCategory userRoleCategory = userRoleCategoryManager.create(new UserRoleCategory(c.getCategoryName(), new User(user.getId()), new Role(roleId), c.getRoleCategoryType(), status));
                userRoleCategoryIds.add(userRoleCategory.getId());
            }
            else {
                userRoleCategoryIds.add(matchingUserRoleCategory.get().getId());
            }

        });
        log.info(roles.toString());
        UserResource userResource = getUserResourceByUserName(user.getUserName());

        List<RoleRepresentation> rolesToAdd = new LinkedList<>();
        roles.forEach(c -> {
            try {
                RoleRepresentation roleRepresentation = getKeycloakRealmResource().roles().get(c).toRepresentation();
                if (roleRepresentation != null) {
                    rolesToAdd.add(roleRepresentation);

                } else {
                    log.error("Role {} not found in Keycloak for user {}", c, user.getUserName());
                }
            } catch (NotFoundException e) {
                log.error("Role {} not found in Keycloak for user {}", c, user.getUserName());
            }
        });
        userResource.roles().realmLevel().add(rolesToAdd);
        return userRoleCategoryIds;
    }
    public UserResource getUserResourceByUserName(String userName){
      RealmResource keycloakRealmResource = keycloakCustomConfig.getInstance().realm(realmResource);
      String userId = keycloakRealmResource
              .users()
              .search(userName)
              .get(0)
              .getId();
      return keycloakRealmResource.users().get(userId);
    }
    public RealmResource getKeycloakRealmResource(){
      return keycloakCustomConfig.getInstance().realm(realmResource);
    }
    @Transactional
    public void removeRole(RemoveRoleRequestDto dto){
      Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
      boolean isSuperAdmin = userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
      boolean isAdmin = userRoles.stream().anyMatch(r -> r.contains("ADMIN"));
      User user = userManager.findByName(dto.getUserName());
      long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
      long userOrganisationId;
      if (isAdmin){
        userOrganisationId = user.getManufacturer() != null ? user.getManufacturer().getId(): user.getLabUsers().getLabId();
      }else {
        userOrganisationId = 0L;
      }
      if (isSuperAdmin || (isAdmin && (Objects.equals(userOrganisationId, manufacturerId)))){
        UserRoleCategory roleCategory = userRoleCategoryManager.findById(dto.getRoleCategoryId());
        userRoleCategoryManager.delete(dto.getRoleCategoryId());
        String keycloakRole = String.format("%s_%s_%s",roleCategory.getCategory(), roleCategory.getRole().getName(), roleCategory.getRoleCategoryType());
        RoleRepresentation roleToDelete = getKeycloakRealmResource().roles().get(keycloakRole).toRepresentation();
        UserResource userResource = getUserResourceByUserName(dto.getUserName());
        userResource.roles().realmLevel().remove(Collections.singletonList(roleToDelete));
      }else {
        throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
      }
    }
    public ListResponse<RoleResponseDto> getRoles(Integer pageNumber,Integer pageSize){
      List<Role> entities = roleManager.findAll(pageNumber,pageSize);
      Long count = roleManager.getCount(entities.size(),pageNumber,pageSize);
      return ListResponse.from(entities.stream().filter(r -> !Objects.equals(r.getName(), "INSPECTION")).toList(),mapper::toDto,count);
    }

      public void createRoles(List<RoleRequestDto> dtos) {

          RealmResource keycloakRealmResource = keycloakCustomConfig.getInstance().realm(realmResource);

          List<RoleRepresentation> existing = keycloakRealmResource.roles().list();
          dtos = dtos.stream().filter(roleRequestDto -> !existing.stream().map(RoleRepresentation::getName).toList().contains(roleRequestDto.getRoleName())).collect(Collectors.toList());
          dtos.forEach(roleRequestDto -> {
              RoleRepresentation roleRepresentation = new RoleRepresentation();
              roleRepresentation.setName(roleRequestDto.getRoleName());
              keycloakRealmResource.roles().create(roleRepresentation);
          });


      }

    public List<Long> updateUserRoles(AssignRoleRequestDto dto){

        List<Long> userRoleCategoryIds = new ArrayList<>();
        User user = userManager.findByName(dto.getUserName());
        List<RoleRequestDto> rolesMap = dto.getRoles();
        List<Role> dbRoles = roleManager.findByNames(rolesMap.stream().map(RoleRequestDto::getRoleName).distinct().collect(Collectors.toList()));
        List<String> roles = new ArrayList<>();
        List<UserRoleCategory> userRoleCategories = userRoleCategoryManager.findByUserId(user.getId());
        Set<String> newRoleKeys = rolesMap.stream()
                .map(c -> String.format("%s_%s_%s", c.getCategoryName(), c.getRoleName(), c.getRoleCategoryType()))
                .collect(Collectors.toSet());
        userRoleCategories.forEach(uc -> {
            String roleKey = String.format("%s_%s_%s", uc.getCategory(), uc.getRole().getName(), uc.getRoleCategoryType());
            if (!newRoleKeys.contains(roleKey)) {
                RemoveRoleRequestDto removeRoleRequestDto = new RemoveRoleRequestDto(user.getUserName(), uc.getId());
                removeRole(removeRoleRequestDto);
            }
        });
        return assignUserRole(user,rolesMap);
    }
  }
