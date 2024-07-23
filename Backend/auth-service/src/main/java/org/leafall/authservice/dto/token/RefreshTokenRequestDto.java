package org.leafall.authservice.dto.token;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequestDto {

    @NotEmpty(message = "Refresh request must contains refresh token")
    private String refreshToken;
}
