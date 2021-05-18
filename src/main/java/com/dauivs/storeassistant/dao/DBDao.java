package com.dauivs.storeassistant.dao;

import com.dauivs.storeassistant.utils.StringUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DBDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 执行sql语句
     *
     * @param sql
     * @param params
     * @return
     */
    public List runSql(String sql, Object... params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; ++i) {
                query.setParameter(i + 1, params[i]);
            }
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return StringUtil.toHumpKeys(query.getResultList());
    }
}
