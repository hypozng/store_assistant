package com.dauivs.storeassistant.model.sale;

import com.dauivs.storeassistant.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sale_order")
public class SaleOrder extends BaseModel {

    @Column(name = "status")
    private Integer status;

    @Column(name = "customerId")
    private Integer customerId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @Column(name = "paid_price")
    private BigDecimal paidPrice;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "remark")
    private String remark;

    @Transient
    private List<SaleOrderCommodity> commodities;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(BigDecimal paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SaleOrderCommodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<SaleOrderCommodity> commodities) {
        this.commodities = commodities;
    }
}