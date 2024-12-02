package org.path.iam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE manufacturer_empanel SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class ManufacturerEmpanel extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long manufacturerId;
    private Long categoryId;
    private String stateGeoId;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date toDate;
}
