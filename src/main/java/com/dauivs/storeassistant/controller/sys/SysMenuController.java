package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysMenuDao;
import com.dauivs.storeassistant.model.common.BasisModel;
import com.dauivs.storeassistant.model.common.ResponseResult;
import com.dauivs.storeassistant.model.sys.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuDao dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseResult list() {
        try {
            return ResponseResult.success(dao.findAll());
        } catch(Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult info(@PathVariable Integer id) {
        try {
            return ResponseResult.success(dao.findById(id));
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id) {
        try {
            SysMenu sysMenu = dao.findById(id).get();
            sysMenu.setDeleted(BasisModel.ON);
            return ResponseResult.success(sysMenu);
        } catch(Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody SysMenu sysMenu) {
        try {
            if (sysMenu.getId() != null) {
                sysMenu.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            } else {
                sysMenu.setCreateTime(new Timestamp(System.currentTimeMillis()));
                sysMenu.setDeleted(BasisModel.OFF);
            }
            dao.saveAndFlush(sysMenu);
            return ResponseResult.success(sysMenu);
        } catch (Exception e) {
            return ResponseResult.fail(ResponseResult.MESSAGE_FAIL01, e);
        }
    }
}
