package com.cdshop.modules.system.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cdshop.modules.model.User;
import com.cdshop.modules.security.config.SecurityProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GennrateToken {
    /**
     * 生成用户的token
     *
     * @param user
     * @return
     */
    public static String token (User user, SecurityProperties properties) {
        String token = "";
        try {
            // 过期时间
            Date date = new Date(System.currentTimeMillis() + properties.getTokenValidityInSeconds());
            // 秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(properties.getBase64Secret());
            // 设置头部信息
            Map<String, Object> header = new HashMap<String, Object>(2){{
                put("typ", "JWT");
                put("alg", "HS256");
            }};
            // 生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", user.getUsername())
                    .withClaim("password", user.getPassword())
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }
}
