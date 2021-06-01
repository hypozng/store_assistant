package com.dauivs.storeassistant.controller.sale;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.sale.CommodityDao;
import com.dauivs.storeassistant.dao.sale.SaleOrderCommodityDao;
import com.dauivs.storeassistant.dao.sale.SaleOrderDao;
import com.dauivs.storeassistant.model.sale.Commodity;
import com.dauivs.storeassistant.model.sale.SaleOrder;
import com.dauivs.storeassistant.model.sale.SaleOrderCommodity;
import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.CommonUtil;
import com.dauivs.storeassistant.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/sale/order")
public class SaleOrderController {

    @Autowired
    private SaleOrderDao dao;

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private SaleOrderCommodityDao saleOrderCommodityDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        return ResponseData.success(CommonUtil.delete(dao, id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(@RequestBody SaleOrder saleOrder) {
        SysUser sysUser = ShiroUtil.getUser();
        if (saleOrder.getCommodities() != null) {
            saleOrder.getCommodities().removeIf(e -> e == null || e.getCommodityId() == null);
        }
        if (saleOrder.getCommodities() == null || saleOrder.getCommodities().isEmpty()) {
            return ResponseData.fail("该订单无商品信息");
        }
        BigDecimal totalPrice = new BigDecimal(0);
        for (SaleOrderCommodity orderCommodity : saleOrder.getCommodities()) {
            Commodity commodity = commodityDao.findById(orderCommodity.getCommodityId()).orElse(null);
            if (commodity == null) {
                return ResponseData.fail("没有找到商品信息(id:" + orderCommodity.getCommodityId() + ")");
            }
            if (orderCommodity.getAmount() == null || orderCommodity.getAmount() <= 0) {
                return ResponseData.fail("订单商品数量必须大于0 (" + commodity.getName() + ")");
            }
            orderCommodity.setPrice(commodity.getSalePrice());
            CommonUtil.prepareSave(orderCommodity, sysUser);
            totalPrice = totalPrice.add(commodity.getSalePrice().multiply(new BigDecimal(orderCommodity.getAmount())));
        }

        saleOrder.setPrice(totalPrice);
        return ResponseData.success(CommonUtil.save(dao, saleOrder, order -> {
            for(SaleOrderCommodity orderCommodity : order.getCommodities()) {
                orderCommodity.setOrderId(order.getId());
            }
            saleOrderCommodityDao.saveAll(saleOrder.getCommodities());
            return order;
        }));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }
}
