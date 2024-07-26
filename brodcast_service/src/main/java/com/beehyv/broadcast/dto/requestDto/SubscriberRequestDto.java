package com.beehyv.broadcast.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberRequestDto {

    private Long id;
    private Integer stateGeoId;
    private String name;
    @URL
    private String url;
}
