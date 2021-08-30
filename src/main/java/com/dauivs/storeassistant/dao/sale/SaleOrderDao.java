package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.SaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface SaleOrderDao extends JpaRepository<SaleOrder, Integer>, SaleOrderDaoCustom {
}

interface SaleOrderDaoCustom {
    /**
     * 查询分页数据
     *
     * @param searchParameter
     * @return
     */
    PageData findPage(SearchParameter searchParameter);
}

class SaleOrderDaoCustomImpl implements SaleOrderDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, c.name, c.gender");
        sql.append(" from sale_order a");
        sql.append(" left join customer c on c.deleted = 0 and c.id = a.customer_id");
        sql.append(" where a.deleted = 0");
        if (searchParameter.extractParam("code", SearchParameter.LIKE)) {
            sql.append(" and a.code like ?");
        }
        if (searchParameter.extractParam("remark", SearchParameter.LIKE)) {
            sql.append(" and a.remark like ?");
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}