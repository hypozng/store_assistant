package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.dao.sys.SysDictionaryDao;
import com.dauivs.storeassistant.model.sys.SysDictionary;
import com.dauivs.storeassistant.utils.CommonUtil;
import com.dauivs.storeassistant.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/sys/dictionary")
public class SysDictionaryController {

    @Autowired
    private SysDictionaryDao dao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
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
    public ResponseData save(@RequestBody SysDictionary sysDictionary) {
        if (sysDictionary.getParentId() == null) {
            sysDictionary.setParentId(0);
        }
        if (StringUtil.isEmpty(sysDictionary.getGroupKey())) {
            return ResponseData.fail("字典分组不能为空");
        }
        return ResponseData.success(CommonUtil.save(dao, sysDictionary, model -> {
            if (sysDictionary.getChildren() == null) {
                return model;
            }
            sysDictionary.getChildren().removeIf(Objects::isNull);
            if (sysDictionary.getChildren().isEmpty()) {
                return model;
            }
            int index = 0;
            for (SysDictionary child : sysDictionary.getChildren()) {
                child.setParentId(model.getId());
                child.setGroupKey(sysDictionary.getGroupKey());
                child.setOrderIndex(index + 1);
                child.setCode(String.format("%04d", index + 1));
                CommonUtil.prepareSave(child);
            }
            dao.saveAll(sysDictionary.getChildren());
            return model;
        }));
    }

    @RequestMapping(value = "/children/{id}", method = RequestMethod.GET)
    public ResponseData children(@PathVariable int id) {
        return ResponseData.success(dao.findAllByParentId(id));
    }

    @RequestMapping(value = "/group/{groupKey}", method = RequestMethod.GET)
    public ResponseData group(@PathVariable String groupKey) {
        return ResponseData.success(dao.findAllByGroupKey(groupKey));
    }
}
