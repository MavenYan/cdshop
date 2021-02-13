package com.cdshop.modules.service.security;

import com.cdshop.modules.model.User;
import com.cdshop.modules.security.config.SecurityProperties;
import com.cdshop.modules.system.util.EncryptUtils;
import com.cdshop.modules.system.util.RedisUtil;
import com.cdshop.modules.system.util.StringUtils;
import com.cdshop.service.security.OnlineUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 在线用户设置
 * @author wangjn
 * @date 2021-01-05
 */
@Service
@Slf4j
public class OnlineUserService {
    private final SecurityProperties properties;
    private RedisUtil redisUtil;

    public OnlineUserService(SecurityProperties properties, RedisUtil redisUtil) {
        this.properties = properties;
        this.redisUtil = redisUtil;
    }

    /**
     * 保存在线用户信息
     *
     * @param user
     * @param token
     * @param request
     */
    public void save(User user, String token, HttpServletRequest request) {
        String job = user.getJob();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);
        OnlineUser onlineUser = null;
        try {
            onlineUser = new OnlineUser(user.getUsername(), user.getNick_name(), job, browser, ip, address, EncryptUtils.desDecrypt(token), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisUtil.set(properties.getOnlineKey() + token, onlineUser, properties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 获取全部在线用户
     *
     * @return
     */
    public List<OnlineUser> getAllOnlineUser(String username, int type) {
        List<String> keyList = null;
        if (type == 1) {
            keyList = redisUtil.scan("m-online-token");
        } else {
            keyList = redisUtil.scan(properties.getOnlineKey() + "*");
        }

        Collections.reverse(keyList);
        List<OnlineUser> onlineUserList = new ArrayList<OnlineUser>();
        for (String key : keyList) {
            OnlineUser onlineUser = (OnlineUser) redisUtil.get(key);
            if (StringUtils.isNoneBlank(username)) {
                if (onlineUser.toString().contains(username)) {
                    onlineUserList.add(onlineUser);
                }
            } else {
                onlineUserList.add(onlineUser);
            }
        }
        onlineUserList.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserList;
    }

    /**
     * 踢出用户
     * @param key
     * @throws Exception
     */
    public void kickOut(String key) throws Exception {
        key = properties.getOnlineKey() + EncryptUtils.desDecrypt(key);
        redisUtil.del(key);
    }

    /**
     * 检查用户是否已经登录，若已经登录，则踢下线
     *
     * @param userName
     * @param igoreToken
     */
    public void clickUserOut(String userName, String igoreToken) {
        List<OnlineUser> onlineUsers = getAllOnlineUser(userName, 0);
        if (onlineUsers == null || onlineUsers.isEmpty()) {
            return;
        }
        for (OnlineUser onlineUser: onlineUsers) {
            if (onlineUser.getUserName().equals(userName)) {
                try {
                    String token = EncryptUtils.desDecrypt(onlineUser.getToken());
                    if (StringUtils.isNoneBlank(igoreToken) && !igoreToken.equals(token)) {
                        this.kickOut(onlineUser.getToken());
                    } else if(StringUtils.isBlank(igoreToken)){
                        this.kickOut(onlineUser.getToken());
                    }
                } catch (Exception e) {
                    log.error("check out user is error!", e);
                }
            }
        }
    }
}
