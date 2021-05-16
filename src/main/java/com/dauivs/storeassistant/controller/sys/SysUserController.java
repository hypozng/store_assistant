package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.common.ResponseResult;
import com.dauivs.storeassistant.model.BasisModel;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.ShiroUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserDao dao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult info(@PathVariable int id) {
        try {
            return ResponseResult.success(dao.findById(id));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable int id) {
        try {
            SysUser sysUser = dao.findById(id).get();
            sysUser.setDeleted(BasisModel.ON);
            return ResponseResult.success(dao.saveAndFlush(sysUser));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody SysUser sysUser) {
        try {
            SysUser loginUser = ShiroUtil.getUser();
            if (sysUser.getId() != null) {
                sysUser.setUpdateUserId(loginUser.getId());
                sysUser.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            } else {
                sysUser.setCreateUserId(loginUser.getId());
                sysUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
            }
            return ResponseResult.success(dao.saveAndFlush(sysUser));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody UsernamePasswordToken token) {
        try {
            return ResponseResult.success(ShiroUtil.login(token));
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return ResponseResult.fail("用户名或密码错误");
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseResult logout() {
        try {
            return ResponseResult.success(ShiroUtil.logout());
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/loginExpiredInfo", method = RequestMethod.GET)
    public ResponseResult loginExpiredInfo() {
        return ResponseResult.loginExpired();
    }
}
