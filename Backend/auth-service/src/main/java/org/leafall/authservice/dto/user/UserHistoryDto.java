package org.leafall.authservice.dto.user;

import lombok.Builder;
import lombok.Data;
import org.leafall.authservice.entity.UserEntityRole;

@Data
@Builder
public class UserHistoryDto {

    private Long id;
    private String email;
    private String username;
    private UserEntityRole role;
}
