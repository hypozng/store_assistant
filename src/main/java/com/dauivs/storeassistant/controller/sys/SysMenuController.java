package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysMenuDao;
import com.dauivs.storeassistant.model.BasisModel;
import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.model.sys.SysMenu;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuDao dao;

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
        SysMenu sysMenu = dao.findById(id).get();
        sysMenu.setDeleted(BasisModel.ON);
        return ResponseData.success(dao.saveAndFlush(sysMenu));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SysMenu sysMenu) {
        SysUser sysUser = ShiroUtil.getUser();
        if (sysMenu.getId() != null) {
            sysMenu.setUpdateUserId(sysUser.getId());
            sysMenu.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            sysMenu.setCreateUserId(sysUser.getId());
            sysMenu.setCreateTime(new Timestamp(System.currentTimeMillis()));
            sysMenu.setDeleted(BasisModel.OFF);
            dao.saveAndFlush(sysMenu);
        }
        if (sysMenu.getParentId() != null) {
            dao.findById(sysMenu.getParentId()).ifPresent(menu -> {
                sysMenu.setPath(menu.getPath() + "," + sysMenu.getId());
            });
        }
        return ResponseData.success(dao.saveAndFlush(sysMenu));
    }
}
