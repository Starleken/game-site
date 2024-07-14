package com.leafall.accountsservice.dto.user;

import com.leafall.accountsservice.entity.UserEntityRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserHistoryDto {

    private Long id;
    private String email;
    private String username;
    private UserEntityRole role;
}
