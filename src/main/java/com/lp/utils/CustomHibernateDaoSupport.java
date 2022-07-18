package com.lp.utils;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;


public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport {
	@Autowired
    public void getSessionFactory(SessionFactory sessionFactory)
    {
        setSessionFactory(sessionFactory);
    }
	protected void saveOrUpdate(Object obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }
	protected void Update(Object obj) {
        getHibernateTemplate().update(obj); 
    }

    protected void delete(Object obj) {
        getHibernateTemplate().delete(obj);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object find(Class clazz, Long id) {
        return getHibernateTemplate().get(clazz, id);
    }
    
	@SuppressWarnings("rawtypes")
	protected List findAll(Class clazz) {
        return  getHibernateTemplate().find("from " + clazz.getName());
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List loadAll(Class clazz) {
        return  getHibernateTemplate().loadAll(clazz);//("from " + clazz.getName());
    }
	@SuppressWarnings("rawtypes")
	protected Object load(Class clazz, long id) {
        return  getHibernateTemplate().load(clazz.getName(), id);//(clazz);//("from " + clazz.getName());
    }	
}
