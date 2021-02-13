package com.cdshop.modules.system.util;

/**
 * 常用静态常量
 *
 * @author wangjn
 * @date 2021-01-05
 */
public class CdshopConstant {

    public static final String RESET_PASS = "重置密码";

    public static final String RESET_MAIL = "重置邮箱";

    /**
     * 常用接口
     */
    public static class Url{
        /**
         * 免费图床
         */
        public static final String SM_MS_URL = "https://sm.ms/api";

        /**
         * IP归属地查询
         */
        public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    }
}