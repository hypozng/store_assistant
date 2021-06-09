package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sys.SysDictionaryDao;
import com.dauivs.storeassistant.model.BaseModel;
import com.dauivs.storeassistant.model.sys.SysDictionary;
import com.dauivs.storeassistant.utils.CommonUtil;
import com.dauivs.storeassistant.utils.ConvertUtil;
import com.dauivs.storeassistant.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
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
        if (sysDictionary.getId() == null) {
            SysDictionary root = dao.findRootByGroupKey(sysDictionary.getGroupKey());
            if (root != null) {
                return ResponseData.fail("字典分组已存在");
            }
        }
        if (sysDictionary.getChildren() != null) {
            sysDictionary.getChildren().removeIf(Objects::isNull);
            int index = 0;
            for (SysDictionary child : sysDictionary.getChildren()) {
                ++index;
                if (child.getId() == null) {
                    SysDictionary dic = dao.findByGroupKeyAndName(sysDictionary.getGroupKey(), child.getName());
                    if (dic != null) {
                        child.setId(dic.getId());
                    } else {
                        int maxCode = 0;
                        for (SysDictionary item : sysDictionary.getChildren()) {
                            int code = ConvertUtil.toInt(item.getCode());
                            if (code > maxCode) {
                                maxCode = code;
                            }
                        }
                        child.setCode(String.format("%04d", maxCode + 1));
                        child.setGroupKey(sysDictionary.getGroupKey());
                        child.setParentId(sysDictionary.getId());
                    }
                }
                child.setOrderIndex(index);
            }
            sysDictionary.getChildren().forEach(CommonUtil::prepareSave);
            dao.saveAll(sysDictionary.getChildren());
            List<SysDictionary> siblings = dao.findAllByGroupKey(sysDictionary.getGroupKey());
            siblings.removeAll(sysDictionary.getChildren());
            for (SysDictionary sibling : siblings) {
                sibling.setDeleted(BaseModel.ON);
            }
            dao.saveAll(siblings);
        }
        return ResponseData.success(CommonUtil.save(dao, sysDictionary, model -> {
            if (sysDictionary.getChildren() == null) {
                return model;
            }
            for (SysDictionary child : sysDictionary.getChildren()) {
                child.setParentId(model.getId());
                CommonUtil.prepareSave(child);
            }
            dao.saveAll(sysDictionary.getChildren());
            return model;
        }));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }

    @RequestMapping(value = "/group/{groupKey}", method = RequestMethod.GET)
    public ResponseData group(@PathVariable String groupKey) {
        return ResponseData.success(dao.findAllByGroupKey(groupKey));
    }

    @RequestMapping(value = "/children/{id}", method = RequestMethod.GET)
    public ResponseData children(@PathVariable int id) {
        return ResponseData.success(dao.findAllByParentId(id));
    }
}
