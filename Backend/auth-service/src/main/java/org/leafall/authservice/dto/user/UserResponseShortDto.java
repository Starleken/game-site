package org.leafall.authservice.dto.user;

import lombok.Builder;
import lombok.Data;
import org.leafall.authservice.entity.UserEntityRole;

import java.util.Date;

@Data
@Builder
public class UserResponseShortDto {

    private Long id;
    private String username;
    private UserEntityRole role;
}
