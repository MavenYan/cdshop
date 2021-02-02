package com.cdshop.modules.security.rest;

import com.cdshop.modules.security.config.SecurityProperties;
import com.cdshop.modules.system.util.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统:系统授权接口")
public class AuthController {

    @Value("${loginCode.effective-time}")
    private long effectiveTime;
    private final SecurityProperties properties;
    private final RedisUtil redisUtil;

    public AuthController(SecurityProperties properties, RedisUtil redisUtil) {
        this.properties = properties;
        this.redisUtil = redisUtil;
    }

    @ApiOperation("获取验证码图片")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 验证码生成算法
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 设置运算的位数
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        // 生成UUID
        String uuid = properties.getCodeKey() + UUID.randomUUID();
        // 保存
        redisUtil.set(uuid, result, effectiveTime);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

//    @ApiOperation("用户登录")
//    @AnonymousAccess
//    @PostMapping(value = "/login")
//    public ResponseEntity<Object> login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request) {
//        return ResponseEntity.ok();
//    }
}
