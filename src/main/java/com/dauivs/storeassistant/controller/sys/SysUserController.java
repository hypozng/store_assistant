package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.common.ResponseData;
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
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        SysUser sysUser = dao.findById(id).get();
        sysUser.setDeleted(BasisModel.ON);
        return ResponseData.success(dao.saveAndFlush(sysUser));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SysUser sysUser) {
        SysUser loginUser = ShiroUtil.getUser();
        if (sysUser.getId() != null) {
            sysUser.setUpdateUserId(loginUser.getId());
            sysUser.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            sysUser.setCreateUserId(loginUser.getId());
            sysUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        return ResponseData.success(dao.saveAndFlush(sysUser));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestBody UsernamePasswordToken token) {
        try {
            return ResponseData.success(ShiroUtil.login(token));
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return ResponseData.fail("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseData logout() {
        return ResponseData.success(ShiroUtil.logout());
    }

}
