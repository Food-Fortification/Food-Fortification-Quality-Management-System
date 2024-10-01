# User Roles and Permissions

**User Roles**

User roles are used to restrict what components a user can see and what data a user can access and edit. These roles are stored in the backend and are available in the frontend after a successful login. At the moment there are two places, depending on your system setup, where the roles can be defined.

**Keycloak**

When using Keycloak as an authenticator, the roles are assigned through so called Role-Mappings. To assign a new role to a user, this role first has to be created in the realm. To do this go to the Keycloak admin console, select the realm for which you want to create a role and go to the Realm roles menu item. Here a new role can be created and also a description can be provided for it. This description should explain non-technical users, what this role is there for. After a role has been created, the role can be assigned to a user. This can either be done using the assignRole API from swagger or via the Keycloak admin console.

Below is the code for assigning a role to a User

```java
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
```
