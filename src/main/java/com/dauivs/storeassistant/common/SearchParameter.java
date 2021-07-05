package com.dauivs.storeassistant.common;

import com.dauivs.storeassistant.utils.ConvertUtil;
import com.dauivs.storeassistant.utils.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 查询参数
 */
public class SearchParameter {
    public static final String LIKE = "%%%s%%";

    public static final String LIKE_START = "%s%%";

    public static final String LIKE_END = "%%%s";

    private Long page;

    private Long size;

    private String sort;

    private String dir;

    private Map<String, Object> params;

    private List<Object> values;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
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
     * 获取查询参数
     *
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
     * 获取查询参数，并使用String.format()方法对其进行格式化操作
     *
     * @param key
     * @param format
     * @return
     */
    public String getParam(String key, String format) {
        String value = ConvertUtil.toStr(getParam(key));
        if (format == null || StringUtil.isEmpty(format)) {
            return value;
        }
        return String.format(format, value);
    }

    /**
     * 设置查询参数
     *
     * @param key
     * @param value
     */
    public void setParam(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }

    /**
     * 判断查询参数是否非空
     *
     * @param key
     * @return
     */
    public boolean isNotEmptyParam(String key) {
        return !StringUtil.isEmpty(getParam(key));
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public List<Object> getValues() {
        if (values == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(values);
    }

    /**
     * 在值列表末尾添加一个值
     *
     * @param value
     */
    public void addValue(Object value) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

    /**
     * 在值列表的指定位置添加一个值
     *
     * @param index
     * @param value
     */
    public void addValue(int index, Object value) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(index, value);
    }

    /**
     * 清空值列表
     */
    public void clearValues() {
        if (values == null) {
            return;
        }
        values.clear();
    }

    /**
     * 提取查询参数，并将其添加到值列表中
     * 若参数为空提取失败，返回false
     * @param key
     * @return
     */
    public boolean extractParam(String key) {
        boolean flag = isNotEmptyParam(key);
        if (flag) {
            addValue(getParam(key));
        }
        return flag;
    }

    /**
     * 提取查询参数，并使用String.format()将其格式化后添加到值列表中
     * 若参数为空会提取失败，返回false
     * @param key
     * @param format
     * @return
     */
    public boolean extractParam(String key, String format) {
       boolean flag = isNotEmptyParam(key);
       if (flag) {
           addValue(getParam(key, format));
       }
       return flag;
    }
}
