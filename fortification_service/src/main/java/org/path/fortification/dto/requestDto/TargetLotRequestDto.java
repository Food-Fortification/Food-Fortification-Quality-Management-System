package org.path.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TargetLotRequestDto extends BaseRequestDto{

  @PastOrPresent
  @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
  private Date dateOfDispatch;
  private Long targetManufacturerId;
  private String externalTargetManufacturerId;
  private Set<MixMappingRequestDto> mixes;
  private String comments;
}
