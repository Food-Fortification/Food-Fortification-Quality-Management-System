package com.beehyv.iam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE state_lab_test_access SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class StateLabTestAccess extends Base{

    @EmbeddedId
    StateCategoryEntityTypeId stateCategoryEntityTypeId;

    private Boolean labSelectionAllowed;
    private Boolean blockWorkflowForTest;
    private Boolean isStateProcuredRawMaterials;

}
