package com.beehyv.Immudb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BatchEventEntity implements Serializable {
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
        return "(entityId, manufacturerName,licenseNumber, manufacturerAddress, type, state, comments, dateOfAction," +
                " createdBy, createdDate)";
    }
}
