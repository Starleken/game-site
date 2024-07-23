package org.leafall.authservice.dto.user;

import lombok.Builder;
import lombok.Data;
import org.leafall.authservice.entity.UserEntityRole;

import java.util.Date;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String email;
    private String username;
    private UserEntityRole role;
    private Long createdAt;
}
