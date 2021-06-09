package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.dao.sys.SysMenuDao;
import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.model.sys.SysMenu;
import com.dauivs.storeassistant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/api/sys/menu")
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
        return ResponseData.success(CommonUtil.delete(dao, id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SysMenu sysMenu) {
        CommonUtil.prepareSave(sysMenu);
        if (sysMenu.getId() == null) {
            dao.saveAndFlush(sysMenu);
        }
        if (sysMenu.getParentId() != null) {
            dao.findById(sysMenu.getParentId()).ifPresent(menu -> {
                sysMenu.setPath(menu.getPath() + "," + sysMenu.getId());
            });
        }
        return ResponseData.success(CommonUtil.save(dao, sysMenu));
    }
}
