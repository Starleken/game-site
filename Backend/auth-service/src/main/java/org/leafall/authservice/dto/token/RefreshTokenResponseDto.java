package org.leafall.authservice.dto.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponseDto {

    private String accessToken;
    private String refreshToken;
}
