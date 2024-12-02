package org.path.lab.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@ToString
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SampleStateGeoId implements Serializable {
    private Long categoryId;
    private Long labId;
    private Long manufacturerId;

    @Column(length = 10)
    private String districtGeoId;
    @Column(length = 10)
    private String stateGeoId;
    @Column(length = 10)
    private String countryGeoId;

    private Integer sampleSentYear;
}
