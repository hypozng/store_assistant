package com.dauivs.storeassistant.dao;

import com.dauivs.storeassistant.common.PageData;
import com.dauivs.storeassistant.common.SearchParameter;
import com.dauivs.storeassistant.utils.ConvertUtil;
import com.dauivs.storeassistant.utils.StringUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DBDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建Query实例
     *
     * @param sql
     * @param params
     */
    public Query createQuery(CharSequence sql, Collection<?> params) {
        Query query = entityManager.createNativeQuery(ConvertUtil.toStr(sql));
        int index = 0;
        if (params != null) {
            for (Object parameter : params) {
                query.setParameter(++index, parameter);
            }
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query;
    }

    /**
     * 执行sql语句
     *
     * @param sql
     * @param params
     * @return
     */
    public List query(CharSequence sql, Collection<?> params) {
        Query query = createQuery(sql, params);
        return StringUtil.lineToHump(query.getResultList());
    }

    /**
     * 执行sql查询语句，并返回单个值
     * @param sql
     * @param params
     * @return
     */
    public Map<String, Object> querySingle(CharSequence sql, Collection<?> params) {
        Query query = createQuery(sql, params);
        return StringUtil.lineToHump((Map<String, Object>) query.getSingleResult());
    }

    /**
     * 查询分页数据
     *
     * @return
     */
    public PageData queryPage(CharSequence sql, Collection<?> params, SearchParameter searchParameter) {
        return new PageData(query(sql, params));
    }
}
