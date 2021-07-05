package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sys.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysDictionaryDao extends JpaRepository<SysDictionary, Integer>, SysDictionaryDaoCustom {

    @Query(value = "select * from sys_dictionary where deleted = 0 order by order_index", nativeQuery = true)
    List<SysDictionary> findAll();

    @Query(value = "select * from sys_dictionary where deleted = 0 and parent_id != 0 and group_key = ?1 order by order_index", nativeQuery = true)
    List<SysDictionary> findAllByGroupKey(String groupKey);

    @Query(value = "select * from sys_dictionary where deleted = 0 and parent_id = 0 and group_key = ?1 limit 1", nativeQuery = true)
    SysDictionary findRootByGroupKey(String groupKey);

    @Query(value = "select * from sys_dictionary where deleted = 0 and group_key = ?1 and name = ?2 limit 1", nativeQuery = true)
    SysDictionary findByGroupKeyAndName(String groupKey, String name);

    @Query(value = "select * from sys_dictionary where deleted = 0 and parent_id = ?1 order by order_index", nativeQuery = true)
    List<SysDictionary> findAllByParentId(int parentId);

}

interface SysDictionaryDaoCustom {

    PageData findPage(SearchParameter searchParameter);
}

class SysDictionaryDaoCustomImpl implements SysDictionaryDaoCustom {
    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, c.name create_user_name, u.name update_user_name from sys_dictionary a");
        sql.append(" left join sys_user c on c.id = a.create_user_id");
        sql.append(" left join sys_user u on u.id = a.update_user_id");
        sql.append(" where a.deleted = 0 and a.parent_id = 0");
        if (searchParameter.extractParam("groupKey", SearchParameter.LIKE)) {
            sql.append(" and a.group_key like ?");
        }
        if (searchParameter.extractParam("name", SearchParameter.LIKE)) {
            sql.append(" and a.name like ?");
        }
        if (searchParameter.extractParam("remark", SearchParameter.LIKE)) {
            sql.append(" and a.remark like ?");
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}