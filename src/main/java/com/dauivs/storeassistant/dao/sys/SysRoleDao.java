package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sys.SysRole;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Integer>, SysRoleDaoCustom {

    @Query(value = "select * from sys_role where deleted = 0", nativeQuery = true)
    List<SysRole> findAll();

    @Query(value = "select * from sys_role where deleted = 0 and code = ?1 limit 1", nativeQuery = true)
    SysRole findByCode(String code);
}

interface SysRoleDaoCustom {
    PageData<SysRole> findPage(SearchParameter searchParameter);
}

class SysRoleDaoCustomImpl implements SysRoleDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData<SysRole> findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder("select * from sys_role where deleted = 0");
        List<Object> values = new ArrayList<>();
        if (searchParameter.getParams() != null) {
            if (!StringUtils.isEmpty(searchParameter.getParams().get("name"))) {
                sql.append(" and name like ?");
                values.add(searchParameter.getParams().get("name"));
            }
        }
        return new PageData(dbDao.runSql(sql.toString(), values.toArray()));
    }
}