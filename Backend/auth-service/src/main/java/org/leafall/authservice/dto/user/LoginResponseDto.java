package org.leafall.authservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
