package org.path.broadcast.dto.responseDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotificationResponseDto {

    private String eventName;
    private String data;
}
