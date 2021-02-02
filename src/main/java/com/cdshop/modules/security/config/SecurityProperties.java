package com.cdshop.modules.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Jwt参数配置
 * @Auth wangjiani
 * @date 2020年11月7日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class SecurityProperties {
    /** Request Headers ： Authorization */
    private String header;

    /** 令牌前缀,最后要留个空格  */
    private String tokenStartWith;

    /** Base64形式的令牌 */
    private String base64Secret;

    /** 令牌过期时间,单位:毫秒 */
    private Long tokenValidityInSeconds;

    /** 在线用户的key,根据key查询redis中在线用户的数据 */
    private String onlineKey;

    /** 验证码 */
    String codeKey;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
