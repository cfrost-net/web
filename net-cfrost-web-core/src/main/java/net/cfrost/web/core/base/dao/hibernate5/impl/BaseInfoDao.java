package net.cfrost.web.core.base.dao.hibernate5.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.cfrost.web.core.base.dao.hibernate5.IBaseInfoDao;
import net.cfrost.web.core.base.entity.BaseInfoEntity;
import net.cfrost.web.core.util.SecurityContextTool;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public abstract class BaseInfoDao<T extends BaseInfoEntity<?>> extends BaseDao<T> implements IBaseInfoDao<T> {

    @Override
    public T load(Serializable id) {
        T entity = super.load(id);
        if(entity.getIfDel() != 0)
            return null;
        return entity;
    }

    @Override
    public Serializable save(T entity) {
        this.setBaseInfo(entity);
        return this.getSessionFactory().getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        this.setBaseInfo(entity);
        this.getSessionFactory().getCurrentSession().update(entity);

    }

    @Override
    public Serializable saveOrUpdate(T entity) {
        this.setBaseInfo(entity);
        if(entity.getId() != null){
            this.update(entity);
            return entity.getId();
        } else {
            return this.save(entity);
        }
    }

    @Override
    public void delete(T entity) {
        Serializable id = entity.getId();
        this.delete(id);
    }

    @Override
    public void delete(Serializable id) {
        this.getSessionFactory().getCurrentSession()
            .createQuery("update "+ this.entityClass.getSimpleName() + " en set en.ifDel = 1 where en.id = ?0")
            .setParameter(0, id)
            .executeUpdate();
    }

    @Override
    public List<T> findAll() {
        return this.findBy("select en from "+ this.entityClass.getSimpleName() + " en where en.ifDel = 0");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findBy(DetachedCriteria detachedCriteria) {
        detachedCriteria.add(Restrictions.eq("ifDel", 0));
        return detachedCriteria.getExecutableCriteria(this.getSessionFactory().getCurrentSession()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findPageBy(DetachedCriteria detachedCriteria, int pageIndex, int pageSize) {
        detachedCriteria.add(Restrictions.eq("ifDel", 0));
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSessionFactory().getCurrentSession());
        criteria.setFirstResult(pageIndex);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    public long findCount() {
        List<?> l = this.findBy("select count(*) from "+ this.entityClass.getSimpleName() + "en where en.ifDel = 0");

        if(l!=null && l.size() == 1)
            return (Long)l.get(0);
        return 0;
    }
    
    private void setBaseInfo(T entity){
        Long currentUserId = SecurityContextTool.getCurrentUser().getId();
        Date date = new Date();
        if(entity.getCreateDate() == null)
            entity.setCreateDate(date);
        if(entity.getCreateBy() == null)
            entity.setCreateBy(currentUserId);
        entity.setModifyDate(date);
        entity.setModifyBy(currentUserId);
        entity.setIfDel(0);
    }
}
