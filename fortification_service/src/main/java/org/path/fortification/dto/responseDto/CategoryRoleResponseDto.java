package org.path.fortification.dto.responseDto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRoleResponseDto {
    private Long id;
    private List<String> roles;
}
