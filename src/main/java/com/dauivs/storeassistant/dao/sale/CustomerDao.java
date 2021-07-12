package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sale.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Integer>, CustomerDaoCustom {
}

interface CustomerDaoCustom {
    PageData findPage(SearchParameter searchParameter);
}

class CustomerDaoCustomImpl implements CustomerDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.* from customer a where a.deleted = 0");
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
        if (searchParameter.extractParam("keyword", SearchParameter.LIKE)) {
            sql.append(" and (a.name like ? or a.phone like ? or a.idcard like ? or a.address like ?)");
            searchParameter.extractParam("keyword", SearchParameter.LIKE);
            searchParameter.extractParam("keyword", SearchParameter.LIKE);
            searchParameter.extractParam("keyword", SearchParameter.LIKE);
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}
