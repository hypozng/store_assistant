package com.dauivs.storeassistant.model.sale;

import com.dauivs.storeassistant.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "warehouse_order")
public class WarehouseOrder extends BaseModel {
    /**
     * 入库
     */
    public static final Integer TYPE_PUT = 1;

    /**
     * 出库
     */
    public static final Integer TYPE_TAKE = 2;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Column(name = "amount")
    private BigInteger amount;

    @Column(name = "type")
    private Integer type;

    @Column(name = "revoked")
    private Integer revoked;

    @Column(name = "remark")
    private String remark;

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRevoked() {
        return revoked;
    }

    public void setRevoked(Integer revoked) {
        this.revoked = revoked;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
