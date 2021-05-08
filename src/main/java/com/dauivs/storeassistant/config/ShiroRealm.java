package com.dauivs.storeassistant.config;

import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.model.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", sysUser);
        return new SimpleAuthenticationInfo(sysUser.getUser(), sysUser.getPassword(), getName());
    }

}
