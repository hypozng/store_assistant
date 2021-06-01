package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.model.sale.SaleOrderCommodity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleOrderCommodityDao extends JpaRepository<SaleOrderCommodity, Integer>, SaleOrderCommodityDaoCustom {
}

interface SaleOrderCommodityDaoCustom {

}

class SaleOrderCommodityDaoCustomImpl implements SaleOrderCommodityDaoCustom {

}