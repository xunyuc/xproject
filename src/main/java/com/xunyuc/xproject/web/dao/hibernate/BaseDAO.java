package com.xunyuc.xproject.web.dao.hibernate;

import com.xunyuc.xproject.web.bean.Page;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Xunyuc on 2017/7/8.
 */
@Repository
public class BaseDAO<T, PK extends Serializable> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> entityClass;

    @SuppressWarnings({ "unchecked" })
    public BaseDAO() {
        this.entityClass = null;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

//    public Criteria getCriteria(){
//        return getSession().createCriteria(entityClass);
//    }

    public T load(PK id) {
        return getSession().load(this.entityClass, id);
    }

    public T get(PK id) {
        return getSession().get(this.entityClass, id);
    }

//    @SuppressWarnings("unchecked")
//    public List<T> get(PK[] ids) {
//        return getSession().createQuery("from "+entityClass.getName()+" as model where model.id in (:ids)").setParameterList("ids", ids).list();
//    }

//    @SuppressWarnings("unchecked")
//    public T get(String propertyName, Object value) {
//        return (T) getSession().createQuery("from "+entityClass.getName()+" as model where model."+propertyName+" = ?").setParameter(0, value).uniqueResult();
//    }

//    public T get(DetachedCriteria criteria){
//        List<T> list = list(criteria);
//        return list.size()>0?list.get(0):null;
//    }

//    @SuppressWarnings("unchecked")
//    public List<T> getList(String propertyName, Object value) {
//        return getSession().createQuery("from "+entityClass.getName()+" as model where model."+propertyName+" = ?").setParameter(0, value).list();
//    }

//    public Integer getCount() {
//        return Integer.parseInt(getCriteria().setProjection(Projections.rowCount()).uniqueResult().toString());
//    }

//    public Integer getCount(DetachedCriteria criteria) {
//        return Integer.parseInt(criteria.setProjection(Projections.rowCount()).getExecutableCriteria(getSession()).uniqueResult().toString());
//    }

    @SuppressWarnings("unchecked")
    public PK save(T entity) {
        return (PK) getSession().save(entity);
    }

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

//    public void merge(T entity){
//        getSession().merge(entity);
//    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

//    public void delete(PK id) {
//        getSession().delete(load(id));
//    }

//    public void delete(PK[] ids) {
//        for (PK id : ids) {
//            delete(id);
//        }
//    }

//    public void delete(List<T> list){
//        if(list!=null&&!list.isEmpty()){
//            list.forEach(T -> {
//                getSession().delete(T);
//            });
//        }
//    }

//    @SuppressWarnings("unchecked")
//    public List<T> list() {
//        return getCriteria().list();
//    }

//    @SuppressWarnings("unchecked")
//    public List<T> list(DetachedCriteria criteria) {
//        return criteria.getExecutableCriteria(getSession()).list();
//    }

//    public Object findObject(DetachedCriteria criteria) {
//        return criteria.getExecutableCriteria(getSession()).uniqueResult();
//    }

//    @SuppressWarnings("unchecked")
//    public List<T> page(DetachedCriteria criteria,Integer pageSize,Integer pageNumber){
//        return criteria.getExecutableCriteria(getSession()).setFirstResult((pageNumber-1)*pageSize).setMaxResults(pageSize).list();
//    }
//
//    public Page<T> getPage(DetachedCriteria criteria, Page<T> page) {
//        if(page.getKeyWords()!=null&&page.getProperty()!=null&&"".equals(page.getKeyWords().trim())){
//            criteria.add(Restrictions.like(page.getProperty(), page.getKeyWords(),page.getMatchMode()));
//        }
//        page.setTotalCount(getCount(criteria));
//        criteria.setProjection(null);
//        if(page.getOrderBy()!=null){
//            if(page.getOrderType().equals(Page.OrderType.asc)){
//                criteria.addOrder(Order.asc(page.getOrderBy()));
//            }else{
//                criteria.addOrder(Order.desc(page.getOrderBy()));
//            }
//        }
//        page.setList(page(criteria, page.getPageSize(), page.getNowPage()));
//        return page;
//    }
}

