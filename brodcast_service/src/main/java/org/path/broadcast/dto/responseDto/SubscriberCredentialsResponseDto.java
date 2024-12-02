package org.path.broadcast.dto.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberCredentialsResponseDto {
    private Long id;
    private String clientId;
    private String clientSecret;
}
