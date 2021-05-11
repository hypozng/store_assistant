package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.ResponseResult;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sys.SysRoleDao;
import com.dauivs.storeassistant.model.BasisModel;
import com.dauivs.storeassistant.model.sys.SysRole;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleDao dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult list() {
        try {
            return ResponseResult.success(dao.findAll());
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

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
            SysRole sysRole = dao.findById(id).get();
            sysRole.setDeleted(BasisModel.ON);
            return ResponseResult.success(dao.saveAndFlush(sysRole));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody SysRole sysRole) {
        try {
            SysUser sysUser = ShiroUtil.getUser();
            if (sysRole.getId() != null) {
                sysRole.setUpdateUserId(sysUser.getId());
                sysRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            } else {
                sysRole.setCreateUserId(sysUser.getId());
                sysRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
            }
            return ResponseResult.success(dao.saveAndFlush(sysRole));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseResult page(@RequestBody SearchParameter searchParameter) {
        try {
            return ResponseResult.success(new PageData(dao.findAll()));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e.getMessage());
        }
    }
}
