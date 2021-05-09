package com.dauivs.storeassistant.config;

import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.ShiroUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class ShiroRealm extends AuthenticatingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (Objects.isNull(authenticationToken.getPrincipal())) {
            return null;
        }
        SysUser sysUser = sysUserDao.findByUser(authenticationToken.getPrincipal().toString());
        if (Objects.isNull(sysUser)) {
            return null;
        }
        ShiroUtil.setUser(sysUser);
        return new SimpleAuthenticationInfo(sysUser.getUser(), sysUser.getPassword(), ByteSource.Util.bytes(ShiroConfig.HASH_SALT), getName());
    }

}
