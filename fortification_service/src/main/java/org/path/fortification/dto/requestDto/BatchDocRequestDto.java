package org.path.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchDocRequestDto extends BaseRequestDto {
    private Long id;
    @NotNull(message = "Batch id cannot be null")
    private Long batchId;
    @NotNull(message = "CategoryDoc id cannot be null")
    private Long categoryDocId;
    private String name;
    @NotNull(message = "Doc Path cannot be null")
    private String path;
    @FutureOrPresent(message = "InValid expiry Date")
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date expiry;
}
