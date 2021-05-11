package com.dauivs.storeassistant.common;

import java.util.List;

public class PageData<T> {
    /** 页码 */
    private long page;

    /** 页面数据量 */
    private long size;

    /** 总数据量 */
    private long total;

    /** 页面内容 */
    private List<T> content;

    public PageData() {}

    public PageData(List<T> content) {
        page = 1;
        size = 10;
        total = content.size();
        this.content = content;
    }

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getMaxPage() {
        return total / size + (total % size == 0 ? 0 : 1);
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
