package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sys.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        List<Object> values = new ArrayList<>();
        sql.append("select a.*, c.name create_user_name, u.name update_user_name from sys_dictionary a");
        sql.append(" left join sys_user c on c.id = a.create_user_id");
        sql.append(" left join sys_user u on u.id = a.update_user_id");
        sql.append(" where a.deleted = 0 and a.parent_id = 0");
        if (searchParameter.isNotEmptyParam("groupKey")) {
            sql.append(" and a.group_key like ?");
            values.add(searchParameter.getParam("groupKey", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("name")) {
            sql.append(" and a.name like ?");
            values.add(searchParameter.getParam("name", "%%%s%%"));
        }
        if (searchParameter.isNotEmptyParam("备注")) {
            sql.append(" and a.remark like ?");
            values.add(searchParameter.getParam("remark", "%%%s%%"));
        }
        return dbDao.queryPage(sql, values, searchParameter);
    }
}