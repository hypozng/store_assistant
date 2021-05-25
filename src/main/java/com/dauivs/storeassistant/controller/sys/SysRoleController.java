package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sys.SysRoleDao;
import com.dauivs.storeassistant.model.sys.SysRole;
import com.dauivs.storeassistant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sys/role")
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
        return ResponseData.success(CommonUtil.delete(dao, id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SysRole sysRole) {
        return ResponseData.success(CommonUtil.save(dao, sysRole));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }
}
