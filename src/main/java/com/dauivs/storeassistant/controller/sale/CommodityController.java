package com.dauivs.storeassistant.controller.sale;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sale.CommodityDao;
import com.dauivs.storeassistant.dao.sale.CommodityPriceDao;
import com.dauivs.storeassistant.model.sale.Commodity;
import com.dauivs.storeassistant.model.sale.CommodityPrice;
import com.dauivs.storeassistant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Transactional
@RestController
@RequestMapping("/api/sale/commodity")
public class CommodityController {
    @Autowired
    private CommodityDao dao;

    @Autowired
    private CommodityPriceDao commodityPriceDao;

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
            commodity.setAmount(BigInteger.ZERO);
            if (commodity.getPurchasePrice() == null) {
                commodity.setPurchasePrice(BigDecimal.ZERO);
            }
            if (commodity.getSalePrice() == null) {
                commodity.setSalePrice(BigDecimal.ZERO);
            }
        } else {
            commodity.setAmount(null);
            commodity.setSalePrice(null);
            commodity.setPurchasePrice(null);
        }
        return ResponseData.success(CommonUtil.save(dao, commodity, m -> {
            CommodityPrice commodityPriceRecord = new CommodityPrice();
            commodityPriceRecord.setCommodityId(m.getId());
            commodityPriceRecord.setSalePrice(m.getSalePrice());
            commodityPriceRecord.setPurchasePrice(m.getPurchasePrice());
            CommonUtil.save(commodityPriceDao, commodityPriceRecord);
            return m;
        }));
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }

    @RequestMapping(value = "/price/modify", method = RequestMethod.POST)
    public ResponseData priceModify(@RequestBody CommodityPrice commodityPrice) {
        commodityPrice.setId(null);
        if (commodityPrice.getCommodityId() == null) {
            return ResponseData.fail("商品ID不能为空");
        }
        Commodity commodity = dao.findById(commodityPrice.getCommodityId()).orElse(null);
        if (commodity == null) {
            return ResponseData.fail("未找到商品信息");
        }
        commodity.setSalePrice(commodityPrice.getSalePrice());
        commodity.setPurchasePrice(commodityPrice.getPurchasePrice());
        CommonUtil.save(dao, commodity);
        return ResponseData.success(CommonUtil.save(commodityPriceDao, commodityPrice));
    }

    @RequestMapping(value = "/price/page", method = RequestMethod.POST)
    public ResponseData pricePage(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(commodityPriceDao.findPage(searchParameter));
    }
}
