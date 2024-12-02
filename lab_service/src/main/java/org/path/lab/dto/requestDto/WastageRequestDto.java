package org.path.lab.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WastageRequestDto {
    private Long id;
    private Long batchId;
    private Long lotId;
    @PositiveOrZero(message = "Quantity must be positive")
    private Double wastageQuantity;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date reportedDate;
    private String comments;
    @NotNull(message = "Uom id cannot be null")
    private Long uomId;
}