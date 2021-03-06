package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.model.sale.CommodityBrand;
import com.dauivs.storeassistant.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CommodityBrandDao extends JpaRepository<CommodityBrand, Integer>, CommodityBrandDaoCustom {

    @Query(value = "select * from commodity_brand where deleted = 0 order by order_index", nativeQuery = true)
    List<CommodityBrand> findAll();

}

interface CommodityBrandDaoCustom {
    PageData queryPage(SearchParameter searchParameter);
}

class CommodityBrandDaoCustomImpl implements CommodityBrandDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData queryPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from commodity_brand where deleted = 0");
        if (searchParameter.extractParam("name", SearchParameter.LIKE)) {
            sql.append(" and name like ?");
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}