package com.cdshop.modules.security.rest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author wangjn
 * @date 2020-12-30
 */
@Getter
@Setter
public class AuthUser {
    @NotBlank
    private String userName;

    private String password;

    private String code;

    private String uuid;
}
