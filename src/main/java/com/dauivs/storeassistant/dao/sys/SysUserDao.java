package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.model.sys.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Integer> {

    @Query(value = "select * from sys_user where deleted = 0", nativeQuery = true)
    List<SysUser> findAll();
}
