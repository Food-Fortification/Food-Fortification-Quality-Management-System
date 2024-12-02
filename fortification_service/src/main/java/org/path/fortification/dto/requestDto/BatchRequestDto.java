package org.path.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchRequestDto extends BaseRequestDto {
    private Long id;
    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
    private Long stateId;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfManufacture;
    @FutureOrPresent(message = "Expiry Date must be greater than Current date")
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfExpiry;
    private String licenseId;
    private String batchNo;
    private Long uomId;
    @PositiveOrZero(message = "Total Quantity cannot be less than 0")
    private Double totalQuantity;
    @PositiveOrZero(message = "Remaining Quantity cannot be less than 0")
    private Double remainingQuantity;

    private String comments;
    private Set<BatchPropertyRequestDto> batchProperties;
    private Set<BatchDocRequestDto> batchDocs;
    private Set<LotRequestDto> lots;
    private Set<MixMappingRequestDto> mixes;
    private Set<WastageRequestDto> wastes;
}
