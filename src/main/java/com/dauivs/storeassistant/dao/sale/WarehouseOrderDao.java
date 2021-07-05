package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.WarehouseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseOrderDao extends JpaRepository<WarehouseOrder, Integer> {
}

interface WarehouseOrderDaoCustom {
    PageData findPage(SearchParameter searchParameter);
}

class WarehouseOrderDaoCustomImpl implements WarehouseOrderDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        return dbDao.queryPage(sql, searchParameter);
    }
}