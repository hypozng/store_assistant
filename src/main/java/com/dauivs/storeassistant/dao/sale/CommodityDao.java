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

    @Query(value = "select * from commodity where deleted = 0 and find_in_set(id, ?1)", nativeQuery = true)
    List<Commodity> findAllByIds(String ids);

}

interface CommodityDaoCustom {

    PageData findPage(SearchParameter searchParameter);

}

class CommodityDaoCustomImpl implements CommodityDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, brand.name brand_name, category.name category_name from commodity a" +
                " left join commodity_brand brand on brand.id = a.brand_id" +
                " left join commodity_category category on category.id = a.category_id" +
                " where a.deleted = 0");
        if (searchParameter.extractParam("brandId")) {
            sql.append(" and a.brand_id = ?");
        }
        if (searchParameter.extractParam("categoryId")) {
            sql.append(" and a.category_id = ?");
        }
        if (searchParameter.extractParam("name", SearchParameter.LIKE)) {
            sql.append(" and a.name like ?");
        }
        if (searchParameter.extractParam("priceMin")) {
            sql.append(" and a.price >= ?");
        }
        if (searchParameter.extractParam("priceMax")) {
            sql.append(" and a.price <= ?");
        }
        if (searchParameter.extractParam("sku", SearchParameter.LIKE)) {
            sql.append(" and a.sku like ?");
        }
        if (searchParameter.extractParam("code", SearchParameter.LIKE)) {
            sql.append(" and a.code like ?");
        }
        if (searchParameter.extractParam("detail", searchParameter.LIKE)) {
            sql.append(" and a.detail like ?");
        }
        if (searchParameter.extractParam("keyword", SearchParameter.LIKE)) {
            sql.append(" and (a.name like ? or a.sku like ? or a.code like ? or a.detail like ?)");
            searchParameter.extractParam("keyword", SearchParameter.LIKE);
            searchParameter.extractParam("keyword", SearchParameter.LIKE);
            searchParameter.extractParam("keyword", SearchParameter.LIKE);
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}
