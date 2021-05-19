package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.model.sale.CommodityCategory;
import com.dauivs.storeassistant.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CommodityCategoryDao extends JpaRepository<CommodityCategory, Integer>, CommodityCategoryDaoCustom {

    @Query(value = "select * from commodity_category where deleted = 0", nativeQuery = true)
    List<CommodityCategory> findAll();
}

interface CommodityCategoryDaoCustom {
    PageData queryPage(SearchParameter searchParameter);
}

class CommodityCategoryDaoCustomImpl implements CommodityCategoryDaoCustom {
    @Autowired
    private DBDao dbDao;

    @Override
    public PageData queryPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new ArrayList<>();
        sql.append("select * from commodity_category where deleted = 0");
        return dbDao.queryPage(sql, values, searchParameter);
    }
}