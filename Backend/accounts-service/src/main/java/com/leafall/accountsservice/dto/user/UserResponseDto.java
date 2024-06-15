package com.leafall.accountsservice.dto.user;

import com.leafall.accountsservice.entity.UserEntityRole;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String email;
    private String username;
    private UserEntityRole role;
    private Date createdAt;
}
