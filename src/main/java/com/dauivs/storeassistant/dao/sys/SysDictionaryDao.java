package com.dauivs.storeassistant.dao.sys;

import com.dauivs.storeassistant.model.sys.SysDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysDictionaryDao extends JpaRepository<SysDictionary, Integer>, SysDictionaryDaoCustom {

    @Query(value = "select * from sys_dictionary where deleted = 0 order by order_index", nativeQuery = true)
    List<SysDictionary> findAll();

    @Query(value = "select * from sys_dictionary where deleted = 0 and group_key = ?1 order by order_index", nativeQuery = true)
    List<SysDictionary> findAllByGroupKey(String groupKey);

    @Query(value = "select * from sys_dictionary where deleted = 0 and parent_id = ?1 order by order_index", nativeQuery = true)
    List<SysDictionary> findAllByParentId(int parentId);
}

interface SysDictionaryDaoCustom {

}

class SysDictionaryDaoCustomImpl implements SysDictionaryDaoCustom {

}