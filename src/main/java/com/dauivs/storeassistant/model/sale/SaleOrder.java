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

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @Column(name = "customerId")
    private Integer customerId;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "remark")
    private String remark;

    @Transient
    private List<SaleOrderCommodity> commodities;

    @Transient
    private Customer customer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
