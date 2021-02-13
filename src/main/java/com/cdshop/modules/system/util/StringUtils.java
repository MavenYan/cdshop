package com.cdshop.modules.system.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';

    private static final String UNKNOWN = "unknown";

    /**
     * 获取访问者IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localIp = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(comma)[0];
        }
        if (localIp.equals(ip)) {
            // 获取本机外网IP
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 获取访问者使用的浏览器名称
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 根据IP地址获取所在位置
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        String api = String.format(CdshopConstant.Url.IP_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }
}

