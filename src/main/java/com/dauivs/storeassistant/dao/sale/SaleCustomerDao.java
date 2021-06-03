package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.SaleCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface SaleCustomerDao extends JpaRepository<SaleCustomer, Integer>, SaleCustomerDaoCustom {
}

interface SaleCustomerDaoCustom {
    PageData findPage(SearchParameter searchParameter);
}

class SaleCustomerDaoCustomImpl implements SaleCustomerDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new ArrayList<>();
        sql.append("select a.* from sale_customer a where a.deleted = 0");
        if (searchParameter.isNotEmptyParam("name")) {
            sql.append(" and a.name like ?");
            values.add(searchParameter.getParam("name", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("gender")) {
            sql.append(" and a.gender = ?");
            values.add(searchParameter.getParam("gender"));
        }
        if (searchParameter.isNotEmptyParam("phone")) {
            sql.append(" and a.phone like ?");
            values.add(searchParameter.getParam("phone", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("idcard")) {
            sql.append(" and a.idcard like ?");
            values.add(searchParameter.getParam("idcard", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("address")) {
            sql.append(" and a.address like ?");
            values.add(searchParameter.getParam("address", "%%%s%%"));
        }
        return dbDao.queryPage(sql, values, searchParameter);
    }
}
