package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sys.SysRole;
import com.dauivs.storeassistant.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Integer>, SysRoleDaoCustom {

    @Query(value = "select * from sys_role where deleted = 0", nativeQuery = true)
    List<SysRole> findAll();

    @Query(value = "select * from sys_role where deleted = 0 and code = ?1 limit 1", nativeQuery = true)
    SysRole findByCode(String code);
}

interface SysRoleDaoCustom {
    PageData findPage(SearchParameter searchParameter);
}

class SysRoleDaoCustomImpl implements SysRoleDaoCustom {

    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder("select * from sys_role where deleted = 0");
        if (searchParameter.getParams() != null) {
            if (searchParameter.extractParam("name", SearchParameter.LIKE)) {
                sql.append(" and name like ?");
            }
            if (searchParameter.extractParam("code", SearchParameter.LIKE)) {
                sql.append(" and code like ?");
            }
            if (searchParameter.extractParam("remark", SearchParameter.LIKE)) {
                sql.append(" and remark like ?");
            }
            if (searchParameter.extractParam("disabled")) {
                sql.append(" and disabled = ?");
            }
        }
        return dbDao.queryPage(sql, searchParameter);
    }
}