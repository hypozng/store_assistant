package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.model.sale.SaleOrderCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleOrderCommodityDao extends JpaRepository<SaleOrderCommodity, Integer>, SaleOrderCommodityDaoCustom {

    @Query(value = "select * from sale_order_commodity where deleted = 0 and order_id = ?1", nativeQuery = true)
    List<SaleOrderCommodity> findAllByOrderId(int orderId);
}

interface SaleOrderCommodityDaoCustom {

}

class SaleOrderCommodityDaoCustomImpl implements SaleOrderCommodityDaoCustom {

}