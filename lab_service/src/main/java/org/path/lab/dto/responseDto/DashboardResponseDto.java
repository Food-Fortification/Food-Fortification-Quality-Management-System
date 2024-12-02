package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto {
    private Long total;
    private Long totalLabCategories;
    private List<DashboardCountResponseDto> data;
    private Long categoryId;
}
