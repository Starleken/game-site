package com.leafall.accountsservice.dto.user;

import com.leafall.accountsservice.entity.UserEntityRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {

    private Long id;
    private String username;
}
