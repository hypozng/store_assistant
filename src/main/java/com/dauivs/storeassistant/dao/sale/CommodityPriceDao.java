package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.CommodityPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CommodityPriceDao extends JpaRepository<CommodityPrice, Integer>, CommodityPriceDaoCustom {
}

interface CommodityPriceDaoCustom {

    PageData findPage(SearchParameter searchParameter);
}

class CommodityPriceDaoCustomImpl implements CommodityPriceDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from commodity_price where deleted = 0");
        if (searchParameter.extractParam("commodityId")) {
            sql.append(" and commodity_id = ?");
        }
        if (searchParameter.extractParam("salePriceMin")) {
            sql.append(" and sale_price >= ?");
        }
        if (searchParameter.extractParam("salePriceMax")) {
            sql.append(" and sale_price <= ?");
        }
        if (searchParameter.extractParam("purchasePriceMin")) {
            sql.append(" and purchase_price >= ?");
        }
        if (searchParameter.extractParam("purchasePriceMax")) {
            sql.append(" and purchase_price <= ?");
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}
