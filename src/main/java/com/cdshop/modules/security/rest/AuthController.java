package com.cdshop.modules.security.rest;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.cdshop.exception.BadRequestException;
import com.cdshop.modules.model.User;
import com.cdshop.modules.security.config.SecurityProperties;
import com.cdshop.modules.service.UserService;
import com.cdshop.modules.service.security.OnlineUserService;
import com.cdshop.modules.system.util.GennrateToken;
import com.cdshop.modules.system.util.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Value("${rsa.private-key}")
    private String privateKey;
    @Value("${single.login}")
    private boolean singleLogin;
    @Resource(name = "userServiceImpl")
    private UserService userService;
    private final SecurityProperties properties;
    private final RedisUtil redisUtil;
    private final OnlineUserService onlineUserService;

    public AuthController(SecurityProperties properties, RedisUtil redisUtil, OnlineUserService onlineUserService) {
        this.properties = properties;
        this.redisUtil = redisUtil;
        this.onlineUserService = onlineUserService;
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
        // 保存到Redis中
        redisUtil.set(uuid, result, effectiveTime);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @ApiOperation("用户登录")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request) {
        // 获取用户的UUID
        String uuid = authUser.getUuid();
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
        // 获取验证码
        String code = redisUtil.get(uuid).toString();
        // 清除验证码
        redisUtil.del(uuid);
        // 判断验证码是否存在
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        // 权限验证
        User user = userService.findByName(authUser.getUsername());
        // 生成token
        String token = GennrateToken.token(user, properties);
        // 保存在线信息
        onlineUserService.save(user, token, request);
         //返回token与用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2){{
            put("token", properties.getTokenStartWith() + token);
            put("user", user);
        }};
        if (singleLogin) {
            // 踢掉之前已经登录的token
            onlineUserService.clickUserOut(user.getUsername(), token);
        }
        return ResponseEntity.ok(authInfo);
    }
}
