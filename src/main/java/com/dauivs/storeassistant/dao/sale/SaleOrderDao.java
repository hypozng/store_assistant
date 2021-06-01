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
        sql.append("select * from sale_order where deleted = 0");
        List<Object> values = new ArrayList<>();
        return dbDao.queryPage(sql, values, searchParameter);
    }
}