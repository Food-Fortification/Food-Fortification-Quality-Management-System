package org.path.lab.entity;


import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sample_state_geo")
@SQLDelete(sql = "UPDATE sample_state_geo SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class SampleStateGeo extends Base {

    @EmbeddedId
    private  SampleStateGeoId stateGeoId;

    private Long inTransitCount = 0L;
    private Long underTestingCount = 0L;
    private Long testedCount = 0L;
    private Long rejectedCount = 0L;

    public void addInTransitCount(Integer count) {
        this.inTransitCount += count;
    }

    public void addUnderTestingCount(Integer count) {
        this.inTransitCount -= count;
        this.underTestingCount += count;
    }

    public void addTestedCount(Integer count) {
        this.testedCount += count;
        this.underTestingCount -= count;
    }

    public void addRejectedCount(Integer count) {
        this.rejectedCount += count;
        this.underTestingCount -= count;
    }
}
