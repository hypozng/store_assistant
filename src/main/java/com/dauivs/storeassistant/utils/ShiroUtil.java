package com.dauivs.storeassistant.utils;

import com.dauivs.storeassistant.config.ShiroConfig;
import com.dauivs.storeassistant.model.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;

public class ShiroUtil {
    public static final String USER_KEY = "user";

    /**
     * 使用md5加密密码
     * @param password
     * @return
     */
    public static String md5(String password) {
        return new SimpleHash(ShiroConfig.HASH_ALGORITHM_NAME, password, ShiroConfig.HASH_SALT, ShiroConfig.HASH_ITERATIONS).toString();
    }

    /**
     * 获取shiro会话属性
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getAttribute(Object key, Class<T> clazz) {
        Session session = SecurityUtils.getSubject().getSession();
        Object value = session.getAttribute(key);
        if (value != null && clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    /**
     * 获取shiro会话属性
     * @param key
     * @return
     */
    public static Object getAttribute(Object key) {
        return getAttribute(key, Object.class);
    }

    /**
     * 设置shiro会话属性
     * @param key
     * @param value
     */
    public static void setAttribute(Object key, Object value) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(key, value);
    }

    /**
     * 删除shiro会话中设置的属性
     * @param key
     */
    public static void removeAttribute(Object key) {
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute(key);
    }

    /**
     * 获取shiro会话中的用户属性
     * @return
     */
    public static SysUser getUser() {
        return getAttribute(USER_KEY, SysUser.class);
    }

    /**
     * 设置shiro会话中的用户属性
     * @param sysUser
     */
    public static void setUser(SysUser sysUser) {
        setAttribute(USER_KEY, sysUser);
    }

    /**
     * 登录
     * @param token
     */
    public static SysUser login(AuthenticationToken token) {
        SecurityUtils.getSubject().login(token);
        return getUser();
    }

    /**
     * 退出登录
     */
    public static SysUser logout() {
        SysUser sysUser = getUser();
        SecurityUtils.getSubject().logout();
        removeAttribute(USER_KEY);
        return sysUser;
    }
}
