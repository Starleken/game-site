package com.leafall.accountsservice.dto.user;

import com.leafall.accountsservice.entity.UserEntityRole;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponseShortDto {

    private Long id;
    private String username;
    private UserEntityRole role;
}
