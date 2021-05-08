package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.common.ResponseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserDao dao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody UsernamePasswordToken token) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return ResponseResult.success(subject.getSession().getAttribute("user"));
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return ResponseResult.fail("用户名或密码错误");
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }
}
