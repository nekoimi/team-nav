package com.tuituidan.openhub.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Consts.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/10/9
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Consts {

    /**
     * API 版本1的前缀.
     */
    public static final String API_V1 = "/api/v1";

    /**
     * 与jar同级目录，jar如果放在系统根目录，比如docker环境
     * System.getProperty("user.dir")获取到的是"/"，直接去掉.
     */
    public static final String ROOT_DIR = "/".equals(System.getProperty("user.dir")) ? ""
            : System.getProperty("user.dir");

    /**
     * 默认的logo路径
     */
    public static final String DEFAULT_LOGO_PATH = "/assets/images/logo.png";

    /**
     * 默认ID
     */
    public static final String DEFAULT_ID = "1";

    /**
     * 管理员
     */
    public static final String AUTHORITY_ADMIN = "admin";

    /**
     * 管理员角色名称
     */
    public static final String ADMIN_ROLE_NAME = "管理员";
}
