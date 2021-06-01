package com.dauivs.storeassistant.dao.sale;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.model.sale.Commodity;
import com.dauivs.storeassistant.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommodityDao extends JpaRepository<Commodity, Integer>, CommodityDaoCustom {

    @Query(value = "select * from commodity where deleted = 0", nativeQuery = true)
    List<Commodity> findAll();

}

interface CommodityDaoCustom {
    PageData queryPage(SearchParameter searchParameter);

    List<Map> queryList(SearchParameter searchParameter);
}

class CommodityDaoCustomImpl implements CommodityDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData queryPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, brand.name brand_name, category.name category_name from commodity a" +
                " left join commodity_brand brand on brand.id = a.brand_id" +
                " left join commodity_category category on category.id = a.category_id" +
                " where a.deleted = 0");
        List<Object> values = new ArrayList<>();
        if (searchParameter.isNotEmptyParam("brandId")) {
            sql.append(" and a.brand_id = ?");
            values.add(searchParameter.getParam("brandId"));
        }
        if (searchParameter.isNotEmptyParam("categoryId")) {
            sql.append(" and a.category_id = ?");
            values.add(searchParameter.getParam("categoryId"));
        }
        if (searchParameter.isNotEmptyParam("name")) {
            sql.append(" and a.name like ?");
            values.add(searchParameter.getParam("name", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("priceMin")) {
            sql.append(" and a.price >= ?");
            values.add(searchParameter.getParam("priceMin"));
        }
        if (searchParameter.isNotEmptyParam("priceMax")) {
            sql.append(" and a.price <= ?");
            values.add(searchParameter.getParam("priceMax"));
        }
        if (searchParameter.isNotEmptyParam("sku")) {
            sql.append(" and a.sku like ?");
            values.add(searchParameter.getParam("sku", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("code")) {
            sql.append(" and a.code like ?");
            values.add(searchParameter.getParam("code", "%%%s%%"));
        }
        return dbDao.queryPage(sql, values, searchParameter);
    }

    @Override
    public List<Map> queryList(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        List<Object> values = new ArrayList<>();
        sql.append("select a.*, brand.name brand_name, category.name category_name from commodity a" +
                " left join commodity_brand brand on brand.id = a.brand_id" +
                " left join commodity_category category on category.id = a.category_id" +
                " where a.deleted = 0");
        if (searchParameter.isNotEmptyParam("categoryId")) {
            sql.append(" and a.category_id in (select id from commodity_category where find_in_set(?, path))");
            values.add(searchParameter.getParam("categoryId"));
        }
        if (searchParameter.isNotEmptyParam("brandId")) {
            sql.append(" and a.brand_id = ?");
            values.add(searchParameter.getParam("brandId"));
        }
        if (searchParameter.isNotEmptyParam("keyword")) {
            sql.append(" and (a.name like ?)");
            values.add(searchParameter.getParam("keyword", "%%%s%%"));
        }
        return dbDao.query(sql, values);
    }
}
