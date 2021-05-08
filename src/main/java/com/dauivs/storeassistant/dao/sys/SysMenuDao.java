package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.model.sys.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysMenuDao extends JpaRepository<SysMenu, Integer> {

    @Query(value = "select * from sys_menu where deleted = 0", nativeQuery = true)
    List<SysMenu> findAll();

}