package com.zyp.consts;


public class CacheKeyConst {

    /**
     * 用户Token cache key
     */
    public static final String USER_TOKEN = "user:token:";

    /**
     * 用户信息 cache key
     */
    public static final String USER_INFO = "user:info";

    public static final String ROLE_RESOURCE = "role:resource";

    /**
     * 登录次数 cache key
     */
    public static final String USER_LOGIN_RETRY = "user:login:retry:";

    public static final String USER_LOGIN_STATUS = "user:login:status:";

    /**
     * 重放攻击随机数cache
     */
    public static final String SECURITY_REPLAY_ATTACK = "scurity:replay:attack:";

    /**
     * 重复请求 cache key
     */
    public static final String SECURITY_REPEAT_REQ = "scurity:repeat:req:";
}
