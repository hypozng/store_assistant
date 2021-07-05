package com.dauivs.storeassistant.model.sale;

import com.dauivs.storeassistant.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "sale_order_commodity")
public class SaleOrderCommodity extends BaseModel {

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "amount")
    private BigInteger amount;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Transient
    private Commodity commodity;

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
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
