package com.cdshop.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户Dto
 *
 * @author wangjn
 * @date 2020-12-23
 */
@Data
public class User {

    @ApiModelProperty(hidden = true)
    private String id;

    private String avatar_id;

    private String email;

    private String status;

    private String username;

    @JsonIgnore
    private String password;

    private String nick_name;

    private String sex;

    private String phone;

    private String job;

    private Timestamp create_time;

    private Timestamp last_password_reset_time;
}
