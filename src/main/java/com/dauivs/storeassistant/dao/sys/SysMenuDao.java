package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.model.sys.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuDao extends JpaRepository<SysMenu, Integer> {

    @Query(value = "select * from sys_menu where deleted = 0 order by order_index", nativeQuery = true)
    List<SysMenu> findAll();

}
