package com.dauivs.storeassistant.common;

import com.dauivs.storeassistant.utils.ConvertUtil;
import com.dauivs.storeassistant.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class SearchParameter {
    private long page;

    private long size;

    private String sort;

    private String dir;

    private Map<String, Object> params;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 判断查询参数是否不为空
     * @param key
     * @return
     */
    public boolean isNotEmptyParam(String key) {
        return !StringUtil.isEmpty(getParam(key));
    }

    /**
     * 获取查询参数值
     * @param key
     * @return
     */
    public Object getParam(String key) {
        if (params == null || StringUtil.isEmpty(key)) {
            return null;
        }
        return params.get(key);
    }

    /**
     * 获取查询参数，并将其格式化
     * @param key
     * @param format
     * @return
     */
    public String getParam(String key, String format) {
        String value = ConvertUtil.toStr(getParam(key));
        return String.format(format, value);
    }

    /**
     * 设置查询参数值
     * @param key
     * @param value
     */
    public void setParam(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }
}
