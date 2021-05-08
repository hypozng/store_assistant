package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.model.sys.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Integer> {

    @Query(value = "select * from sys_user where deleted = 0 and user = ?1 limit 1", nativeQuery = true)
    SysUser findByUser(String user);

}
