package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.WarehouseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseOrderDao extends JpaRepository<WarehouseOrder, Integer>, WarehouseOrderDaoCustom {
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
        sql.append("select a.*, com.name commodity_name");
        sql.append(" from warehouse_order a");
        sql.append(" left join commodity com on com.id = a.commodity_id");
        sql.append(" where a.deleted = 0");
        if (searchParameter.extractParam("commodityId")) {
            sql.append(" and a.commodity_id = ?");
        }
        if (searchParameter.extractParam("type")) {
            sql.append(" and a.type = ?");
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}