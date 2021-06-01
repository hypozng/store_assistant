package com.dauivs.storeassistant.model.sale;

import com.dauivs.storeassistant.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Table(name = "sale_order_commodity")
public class SaleOrderCommodity extends BaseModel {

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Transient
    private Commodity commodity;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }
}
