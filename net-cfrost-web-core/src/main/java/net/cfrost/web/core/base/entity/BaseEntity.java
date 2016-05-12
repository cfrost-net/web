package net.cfrost.web.core.base.entity;

import java.io.Serializable;

/**
 * 
 * @author cFrost
 *
 * @param <T>
 * @see com.sfa.web.core.base.entity.NonIDEntity
 * @see com.sfa.web.core.base.entity.IDEntity
 * @see com.sfa.web.core.base.entity.UUIDEntity
 */
public interface BaseEntity<T extends BaseEntity<?>> extends Serializable, Comparable<T> {
    
    /**
     * 获取主键<br>
     * 一般采用自增ID或UUID，由实现类指定，亦可自定义主键
     * 
     * @return
     */
    public Serializable getId();
}
