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
        long page = 1;
        if (searchParameter.getPage() != null && searchParameter.getPage() > 0) {
            page = searchParameter.getPage();
        }
        long size = 10;
        if (searchParameter.getSize() != null && searchParameter.getSize() > 0) {
            size = searchParameter.getSize();
        }
        StringBuilder s = new StringBuilder(sql);
        if (!StringUtil.isEmpty(searchParameter.getSort())) {
            s.append(" order by ");
            s.append(searchParameter.getSort());
            s.append(" ");
            s.append("desc".equals(searchParameter.getDir()) ? "desc" : "asc");
        }
        s.append(" limit ");
        s.append((page - 1) * size);
        s.append(", ");
        s.append(size);
        List list = query(s, params);

        String countSql = "select count(*) num from (" + sql + ") t";
        Long total = ConvertUtil.toLong(querySingle(countSql, params).get("num"));

        return new PageData(list, total, page, size);
    }
}
