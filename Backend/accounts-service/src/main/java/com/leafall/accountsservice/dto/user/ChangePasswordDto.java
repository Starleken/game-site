package com.leafall.accountsservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDto {

    private Long id;
    private String oldPassword;
    private String newPassword;
}
