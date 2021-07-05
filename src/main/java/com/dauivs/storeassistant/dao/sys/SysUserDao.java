package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.dao.DBDao;
import com.dauivs.storeassistant.model.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Integer>, SysUserDaoCustom {

    @Query(value = "select * from sys_user where deleted = 0 and user = ?1 limit 1", nativeQuery = true)
    SysUser findByUser(String user);

}

interface SysUserDaoCustom {

    /**
     * 查询分页数据
     * @param searchParameter
     * @return
     */
    PageData findPage(SearchParameter searchParameter);

}

class SysUserDaoCustomImpl implements SysUserDaoCustom {
    @Autowired
    private DBDao dbDao;

    @Override
    public PageData findPage(SearchParameter searchParameter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from sys_user where deleted = 0");
        if (searchParameter.extractParam("user", SearchParameter.LIKE)) {
            sql.append(" and user like ?");
        }
        PageData pageData = dbDao.queryPage(sql, searchParameter);
        if (pageData.getContent() != null) {
            for (Object item : pageData.getContent()) {
                ((Map) item).remove("password");
            }
        }
        return pageData;
    }
}