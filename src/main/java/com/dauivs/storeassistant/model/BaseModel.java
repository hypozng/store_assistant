package com.dauivs.storeassistant.model;

import com.dauivs.storeassistant.model.sys.SysUser;
import com.dauivs.storeassistant.utils.ShiroUtil;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 实体类的基础类
 */
@MappedSuperclass
public class BaseModel {
    /**
     * 打开
     */
    public static final int ON = 1;

    /**
     * 关闭
     */
    public static final int OFF = 0;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_user_id")
    private Integer updateUserId;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "deleted")
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public void init() {
        if (createTime == null) {
            createTime = new Timestamp(System.currentTimeMillis());
        }
        this.deleted = OFF;
    }

    public void delete() {
        this.deleted = ON;
    }

    public void undelete() {
        this.deleted = OFF;
    }

    public boolean equals(Object o) {
        if (o instanceof BaseModel) {
            return ((BaseModel) o).id.equals(id);
        }
        return super.equals(o);
    }
}
