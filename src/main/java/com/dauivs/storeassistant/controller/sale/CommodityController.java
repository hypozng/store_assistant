package com.dauivs.storeassistant.controller.sale;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sale.CommodityDao;
import com.dauivs.storeassistant.model.sale.Commodity;
import com.dauivs.storeassistant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sale/commodity")
public class CommodityController {
    @Autowired
    private CommodityDao dao;

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
    public ResponseData save(@RequestBody Commodity commodity) {
        if (commodity.getId() == null) {
            commodity.setAmount(0);
        }
        return ResponseData.success(CommonUtil.save(dao, commodity));
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.queryPage(searchParameter));
    }
}
