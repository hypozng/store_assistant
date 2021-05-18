package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sys.SysRoleDao;
import com.dauivs.storeassistant.model.BasisModel;
import com.dauivs.storeassistant.model.sys.SysRole;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.ShiroUtil;
import com.dauivs.storeassistant.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleDao dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData list() {
        return ResponseData.success(dao.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        SysRole sysRole = dao.findById(id).get();
        sysRole.setDeleted(BasisModel.ON);
        return ResponseData.success(dao.saveAndFlush(sysRole));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SysRole sysRole) {
        SysUser sysUser = ShiroUtil.getUser();
        if (sysRole.getId() != null) {
            sysRole.setUpdateUserId(sysUser.getId());
            sysRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            sysRole.setDeleted(BasisModel.OFF);
            sysRole.setCreateUserId(sysUser.getId());
            sysRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        return ResponseData.success(dao.saveAndFlush(sysRole));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }
}
