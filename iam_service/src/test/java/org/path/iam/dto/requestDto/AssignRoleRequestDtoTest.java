package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssignRoleRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        AssignRoleRequestDto dto = new AssignRoleRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getUserName()).isNull();
        assertThat(dto.getRoles()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        RoleRequestDto role1 = new RoleRequestDto();
        RoleRequestDto role2 = new RoleRequestDto();
        List<RoleRequestDto> roles = List.of(role1, role2);

        AssignRoleRequestDto dto = new AssignRoleRequestDto("testUser", roles);

        assertThat(dto.getUserName()).isEqualTo("testUser");
        assertThat(dto.getRoles()).isEqualTo(roles);
    }

    @Test
    void testSettersAndGetters() {
        AssignRoleRequestDto dto = new AssignRoleRequestDto();
        RoleRequestDto role1 = new RoleRequestDto();
        RoleRequestDto role2 = new RoleRequestDto();
        List<RoleRequestDto> roles = List.of(role1, role2);

        dto.setUserName("anotherUser");
        dto.setRoles(roles);

        assertThat(dto.getUserName()).isEqualTo("anotherUser");
        assertThat(dto.getRoles()).isEqualTo(roles);
    }

    @Test
    void testToString() {
        RoleRequestDto role1 = new RoleRequestDto();
        RoleRequestDto role2 = new RoleRequestDto();
        List<RoleRequestDto> roles = List.of(role1, role2);

        AssignRoleRequestDto dto = new AssignRoleRequestDto("testUser", roles);

        String dtoString = dto.toString();
        assertThat(dtoString).contains("testUser");
    }
}
