package com.beehyv.iam.dto.requestDto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogsRequestDto {

    private Long id;
    private LocalDateTime timestamp;
    private String level;
    private Long manufacturerId;
    private Long userId;
    private String message;
    private String entityName;
}
