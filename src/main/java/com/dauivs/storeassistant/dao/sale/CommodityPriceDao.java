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
        List<Object> values = new ArrayList<>();
        sql.append("select * from commodity_price where deleted = 0");
        if (searchParameter.isNotEmptyParam("commodityId")) {
            sql.append(" and commodity_id = ?");
            values.add(searchParameter.getParam("commodityId"));
        }
        if (searchParameter.isNotEmptyParam("salePriceMin")) {
            sql.append(" and sale_price >= ?");
            values.add(searchParameter.getParam("salePriceMin"));
        }
        if (searchParameter.isNotEmptyParam("salePriceMax")) {
            sql.append(" and sale_price <= ?");
            values.add(searchParameter.getParam("salePriceMax"));
        }
        if (searchParameter.isNotEmptyParam("purchasePriceMin")) {
            sql.append(" and purchase_price >= ?");
            values.add(searchParameter.getParam("purchasePriceMin"));
        }
        if (searchParameter.isNotEmptyParam("purchasePriceMax")) {
            sql.append(" and purchase_price <= ?");
            values.add(searchParameter.getParam("purchasePriceMax"));
        }
        return dbDao.queryPage(sql, values, searchParameter);
    }
}
