package com.beehyv.fortification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BatchEventEntity {
    private Long id;
    private String entityId;
    private String manufacturerName;
    private String manufacturerAddress;
    private String type;
    private String state;
    private String comments;
    private Timestamp dateOfAction;
    private String createdBy;
    private Timestamp createdDate;
    private String licenseNumber;

    @JsonIgnore
    public String getColumnNames(){
        return "(entityId,type,state,comments,dateOfAction,createdBy,createdDate)";
    }
}
