package com.cdshop.service.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 在线用户信息模板
 *
 * @author wangjn
 * @date 2021-01-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {

    private String userName;

    private String nickName;

    private String job;

    private String browser;

    private String ip;

    private String address;

    private String token;

    private Date loginTime;

}