package com.dauivs.storeassistant.model.sale;

import com.dauivs.storeassistant.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "commodity_price_record")
public class CommodityPriceRecord extends BaseModel {

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "remark")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
