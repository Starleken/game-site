package com.leafall.accountsservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeEmailDto {

    private Long id;
    private String newEmail;
}
