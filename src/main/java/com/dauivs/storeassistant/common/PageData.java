package com.dauivs.storeassistant.common;

import com.dauivs.storeassistant.utils.ConvertUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageData<T> {
    /**
     * 页码
     */
    private Long page;

    /**
     * 页面数据量
     */
    private Long size;

    /**
     * 总数据量
     */
    private Long total;

    /**
     * 页面内容
     */
    private List<T> content;

    public PageData() {
    }

    public PageData(List<T> content, Long total, Long page, Long size) {
        this.content = content;
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public PageData(List<T> content) {
        this(content, content == null ? 0 : (long) content.size(), 1L, 10L);
    }

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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonProperty("maxPage")
    public Long getMaxPage() {
        return total / size + (total % size == 0 ? 0 : 1);
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
