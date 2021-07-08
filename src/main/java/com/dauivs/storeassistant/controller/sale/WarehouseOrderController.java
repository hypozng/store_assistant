package com.dauivs.storeassistant.controller.sale;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sale.CommodityDao;
import com.dauivs.storeassistant.dao.sale.WarehouseOrderDao;
import com.dauivs.storeassistant.model.BaseModel;
import com.dauivs.storeassistant.model.sale.Commodity;
import com.dauivs.storeassistant.model.sale.WarehouseOrder;
import com.dauivs.storeassistant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sale/warehouseOrder")
public class WarehouseOrderController {

    @Autowired
    private WarehouseOrderDao dao;

    @Autowired
    private CommodityDao commodityDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        return ResponseData.success(CommonUtil.delete(dao, id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody WarehouseOrder warehouseOrder) {
        if (warehouseOrder.getAmount() == null || warehouseOrder.getAmount().intValue() <= 0) {
            return ResponseData.fail("商品数量不能小于零");
        }
        if (warehouseOrder.getType() == null) {
            return ResponseData.fail("仓库订单类型不能为空");
        }
        Commodity commodity = commodityDao.findById(warehouseOrder.getCommodityId()).orElse(null);
        if (commodity == null) {
            return ResponseData.fail("未找到商品信息");
        }
        switch (warehouseOrder.getType()) {
            case WarehouseOrder.TYPE_PUT:
                commodity.setAmount(commodity.getAmount().add(warehouseOrder.getAmount()));
                break;
            case WarehouseOrder.TYPE_TAKE:
                commodity.setAmount(commodity.getAmount().subtract(warehouseOrder.getAmount()));
                break;
            default:
                return ResponseData.fail("不支持的仓库订单类型");
        }
        commodityDao.saveAndFlush(commodity);
        warehouseOrder.setRevoked(BaseModel.OFF);
        return ResponseData.success(CommonUtil.save(dao, warehouseOrder));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }

    @RequestMapping(value = "/revoke", method = RequestMethod.GET)
    public ResponseData revoke(int id) {
        WarehouseOrder warehouseOrder = dao.findById(id).orElse(null);
        if (warehouseOrder == null) {
            return ResponseData.fail("未找到仓库订单信息");
        }
        if (warehouseOrder.getRevoked() == BaseModel.ON) {
            return ResponseData.fail("该仓库订单已执行撤销操作，不能再次执行撤销操作");
        }
        Commodity commodity = commodityDao.findById(warehouseOrder.getCommodityId()).orElse(null);
        if (commodity == null) {
            return ResponseData.fail("未找到商品信息");
        }
        switch(warehouseOrder.getType()) {
            case WarehouseOrder.TYPE_PUT:
                commodity.setAmount(commodity.getAmount().subtract(warehouseOrder.getAmount()));
                break;
            case WarehouseOrder.TYPE_TAKE:
                commodity.setAmount(commodity.getAmount().add(warehouseOrder.getAmount()));
                break;
        }
        commodityDao.saveAndFlush(commodity);
        warehouseOrder.setRevoked(BaseModel.ON);
        return ResponseData.success(dao.saveAndFlush(warehouseOrder));
    }
}
