package org.path.iam.model;

import org.path.iam.enums.ComplianceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerAttributeScore extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Attribute attribute;


    @Enumerated(value = EnumType.STRING)
    private ComplianceType compliance;

    private String value;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "attribute_category_score_id")
    private AttributeCategoryScore attributeCategoryScore;

}
