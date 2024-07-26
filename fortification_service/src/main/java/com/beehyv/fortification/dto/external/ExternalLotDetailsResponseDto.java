package com.beehyv.fortification.dto.external;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExternalLotDetailsResponseDto {
    private Long manufacturerId;
    private Long categoryId;
    private Long lotId;
}
