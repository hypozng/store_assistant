package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.SaleCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

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
        sql.append("select a.* from sale_customer a where a.deleted = 0");
        if (searchParameter.extractParam("name", SearchParameter.LIKE)) {
            sql.append(" and a.name like ?");
        }
        if (searchParameter.extractParam("gender")) {
            sql.append(" and a.gender = ?");
        }
        if (searchParameter.extractParam("phone", SearchParameter.LIKE)) {
            sql.append(" and a.phone like ?");
        }
        if (searchParameter.extractParam("idcard", SearchParameter.LIKE)) {
            sql.append(" and a.idcard like ?");
        }
        if (searchParameter.extractParam("address", SearchParameter.LIKE)) {
            sql.append(" and a.address like ?");
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}
