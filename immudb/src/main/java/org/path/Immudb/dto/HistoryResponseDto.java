package org.path.Immudb.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponseDto {
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
    private NameAddressResponseDto target;
}
