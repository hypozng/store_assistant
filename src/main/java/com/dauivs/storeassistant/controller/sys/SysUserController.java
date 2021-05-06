package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysUserDao;
import com.dauivs.storeassistant.model.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserDao dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult hello() {
        return ResponseResult.success(dao.findAll());
    }
}
