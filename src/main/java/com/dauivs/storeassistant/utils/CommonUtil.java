package com.dauivs.storeassistant.utils;

import com.dauivs.storeassistant.model.BaseModel;
import com.dauivs.storeassistant.model.sys.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Optional;

public class CommonUtil {

    /**
     * 合并两个对象属性的值
     *
     * @param newObj 对象中属性值若为null，则使用oldObj中相应的属性的值代替
     * @param oldObj 用于提供给合并方法的储备值
     * @param <T>
     * @return
     */
    public static <T> T merge(T newObj, T oldObj) {
        if (newObj == null) {
            return oldObj;
        }
        if (oldObj == null) {
            return newObj;
        }
        Field[] fields = newObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (field.get(newObj) == null) {
                    field.set(newObj, field.get(oldObj));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newObj;
    }

    /**
     * 对要保存的实体数据做一些准备工作，
     * 其中主要包括数据操作人的ID和数据操作时间
     * 如果是新增的实体数据，操作人的ID和操作时间则分别是createUserId和createTime
     * 如果是更改的实体数据，操作人的ID和操作时间分别是updateUserId和updateTime
     * 如果实体ID为null 则视为新增的实体数据，否则视为更改的实体数据
     *
     * @param model   要操作的实体数据
     * @param sysUser 操作人员
     * @param <T>     实体类型
     * @return
     */
    public static <T extends BaseModel> T prepareSave(T model, SysUser sysUser) {
        if (model == null) {
            return null;
        }
        if (sysUser == null) {
            sysUser = ShiroUtil.getUser();
        }
        if (model.getId() == null) {
            model.setCreateUserId(sysUser.getId());
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
            model.setDeleted(BaseModel.OFF);
        } else {
            model.setUpdateUserId(sysUser.getId());
            model.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        return model;
    }

    /**
     * 对要保存的实体数据做一些准备工作，
     * 其中主要包括数据操作人的ID和数据操作时间
     * 如果是新增的实体数据，操作人的ID和操作时间则分别是createUserId和createTime
     * 如果是更改的实体数据，操作人的ID和操作时间分别是updateUserId和updateTime
     * 如果实体ID为null 则视为新增的实体数据，否则视为更改的实体数据
     *
     * @param model 要操作的实体数据
     * @param <T>   实体类型
     * @return
     */
    public static <T extends BaseModel> T prepareSave(T model) {
        return prepareSave(model, null);
    }

    /**
     * 根据提供的dao和model执行数据保存操作
     *
     * @param dao     实体数据数据存储接口
     * @param model   实体数据
     * @param sysUser 操作人员
     * @param <T>     实体数据类型
     * @param <ID>    实体ID数据类型
     * @return
     */
    public static <T extends BaseModel, ID> T save(JpaRepository<T, ID> dao, T model, SysUser sysUser) {
        if (dao == null || model == null) {
            return null;
        }
        prepareSave(model, sysUser);
        if (model.getId() != null) {
            dao.findById((ID) model.getId()).ifPresent(old -> merge(model, old));
        }
        return dao.saveAndFlush(model);
    }

    /**
     * 根据提供的dao和model执行数据保存操作
     *
     * @param dao   实体数据数据存储接口
     * @param model 实体数据
     * @param <T>   实体数据类型
     * @param <ID>  实体ID数据类型
     * @return
     */
    public static <T extends BaseModel, ID> T save(JpaRepository<T, ID> dao, T model) {
        return save(dao, model, null);
    }

    /**
     * 对要删除的实体数据做一些准备工作
     * 将实体数据的deleted字段设置为“已删除”状态
     *
     * @param dao  实体数据存储接口
     * @param id   实体数据ID
     * @param <T>  实体数据类型
     * @param <ID> 实体ID数据类型
     * @return
     */
    public static <T extends BaseModel, ID> Optional<T> prepareDelete(JpaRepository<T, ID> dao, ID id) {
        Optional<T> optional = dao.findById(id);
        optional.ifPresent(o -> o.setDeleted(BaseModel.ON));
        return optional;
    }

    /**
     * 根据提供的dao和id执行数据删除操作
     *
     * @param dao  实体数据存储接口
     * @param id   实体数据的ID
     * @param <T>  实体数据类型
     * @param <ID> 实体ID数据类型
     * @return
     */
    public static <T extends BaseModel, ID> T delete(JpaRepository<T, ID> dao, ID id) {
        Optional<T> optional = dao.findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        T model = optional.get();
        model.setDeleted(BaseModel.ON);
        return dao.saveAndFlush(model);
    }
}
