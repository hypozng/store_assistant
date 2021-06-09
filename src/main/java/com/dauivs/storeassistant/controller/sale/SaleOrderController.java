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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
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
        SaleOrder saleOrder = dao.findById(id).orElse(null);
        if (saleOrder == null) {
            return ResponseData.fail("没有找到订单信息");
        }
        saleOrder.setCommodities(saleOrderCommodityDao.findAllByOrderId(id));
        String ids = saleOrder.getCommodities().stream()
                .map(orderCommodity -> orderCommodity == null ? null : orderCommodity.getCommodityId().toString())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));
        List<Commodity> commodities = commodityDao.findAllByIds(ids);
        saleOrder.getCommodities().forEach(orderCommodity -> {
            commodities.stream()
                    .filter(commodity -> commodity.getId().equals(orderCommodity.getCommodityId()))
                    .findFirst()
                    .ifPresent(orderCommodity::setCommodity);
        });
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
        saleOrder.setStatus(0);
        BigDecimal orderSalePrice = BigDecimal.ZERO;
        BigDecimal orderPurchasePrice = BigDecimal.ZERO;
        List<Commodity> commodities = new ArrayList<>();
        for (SaleOrderCommodity orderCommodity : saleOrder.getCommodities()) {
            Commodity commodity = commodityDao.findById(orderCommodity.getCommodityId()).orElse(null);
            if (commodity == null) {
                return ResponseData.fail("没有找到商品信息(id:" + orderCommodity.getCommodityId() + ")");
            }
            if (orderCommodity.getAmount() == null || orderCommodity.getAmount() <= 0) {
                return ResponseData.fail("订单商品数量必须大于0 (" + commodity.getName() + ")");
            }
            orderCommodity.setSalePrice(commodity.getSalePrice());
            orderCommodity.setPurchasePrice(commodity.getPurchasePrice());
            CommonUtil.prepareSave(orderCommodity, sysUser);
            BigDecimal amount = new BigDecimal(orderCommodity.getAmount());
            orderSalePrice = orderSalePrice.add(commodity.getSalePrice().multiply(amount));
            orderPurchasePrice = orderPurchasePrice.add(commodity.getPurchasePrice().multiply(amount));
            commodity.setAmount(commodity.getAmount() - amount.intValue());
            commodities.add(commodity);
        }

        saleOrder.setCode(CommonUtil.generateOrderCode());
        saleOrder.setSalePrice(orderSalePrice);
        saleOrder.setPurchasePrice(orderPurchasePrice);
        if (saleOrder.getFinalPrice() == null || saleOrder.getFinalPrice().doubleValue() == 0) {
            saleOrder.setFinalPrice(orderSalePrice);
        }
        if (saleOrder.getPaidAmount() == null || saleOrder.getPaidAmount().doubleValue() == 0) {
            saleOrder.setPaidAmount(orderSalePrice);
        }
        return ResponseData.success(CommonUtil.save(dao, saleOrder, order -> {
            for (SaleOrderCommodity orderCommodity : order.getCommodities()) {
                orderCommodity.setOrderId(order.getId());
            }
            saleOrderCommodityDao.saveAll(saleOrder.getCommodities());
            if (!commodities.isEmpty()) {
                commodityDao.saveAll(commodities);
            }
            return order;
        }));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseData page(@RequestBody SearchParameter searchParameter) {
        return ResponseData.success(dao.findPage(searchParameter));
    }
}
