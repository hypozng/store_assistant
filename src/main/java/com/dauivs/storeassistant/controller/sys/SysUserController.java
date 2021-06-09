package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.CommonUtil;
import com.dauivs.storeassistant.utils.ShiroUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/api/sys/user")
public class SysUserController {

    @Autowired
    private SysUserDao dao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        return ResponseData.success(CommonUtil.delete(dao, id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SysUser sysUser) {
        if (sysUser.getId() == null) {
            sysUser.setPassword(ShiroUtil.md5("123456"));
        }
        return ResponseData.success(CommonUtil.save(dao, sysUser));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestBody UsernamePasswordToken token) {
        try {
            System.out.println(ShiroUtil.md5(new String(token.getPassword())));
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
