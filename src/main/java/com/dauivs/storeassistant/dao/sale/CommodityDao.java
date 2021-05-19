package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.model.sale.Commodity;
import com.dauivs.storeassistant.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CommodityDao extends JpaRepository<Commodity, Integer>, CommodityDaoCustom {
}

interface CommodityDaoCustom {
    PageData queryPage(SearchParameter searchParameter);
}

class CommodityDaoCustomImpl implements CommodityDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData queryPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from commodity where deleted = 0");
        List<Object> values = new ArrayList<>();
        return dbDao.queryPage(sql, values, searchParameter);
    }
}
