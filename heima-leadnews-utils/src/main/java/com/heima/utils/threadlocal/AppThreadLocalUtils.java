package com.heima.utils.threadlocal;


import com.heima.model.user.pojos.ApUser;

/**
 * 当前请求用户信息操作类
 */
public class AppThreadLocalUtils {

    private final  static ThreadLocal<ApUser> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(ApUser user){
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static ApUser getUser( ){
        return userThreadLocal.get();
    }

}
