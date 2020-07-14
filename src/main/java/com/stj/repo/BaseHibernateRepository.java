package com.stj.repo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

public abstract class BaseHibernateRepository<E, S extends Serializable> extends HibernateDaoSupport {

	public BaseHibernateRepository() {
	}

	@SuppressWarnings("unchecked")
	protected <T> T get(final DetachedCriteria criteria, Class<T> clazz) {
		final HibernateTemplate template = getHibernateTemplate();

		T result = (T)

		template.executeWithNativeSession(new HibernateCallback() {

			public T doInHibernate(Session session) throws HibernateException {

				T uniqueResult = (T) criteria.getExecutableCriteria(session).uniqueResult();

				return uniqueResult;
			}

		});

		return result;

	}

	protected E get(final DetachedCriteria criteria) {
		return get(criteria, getEntityClass());
	}

	@SuppressWarnings("unchecked")
	protected List findByCriteria(final DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	protected <T> List findByCriteria(final DetachedCriteria criteria, Class<T> type) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	protected abstract Class<E> getEntityClass();

	protected <T> T get(Class<T> type, Serializable id) {
		return (T) getHibernateTemplate().get(type, id);
	}

	public E findById(S id) {
		return get(getEntityClass(), id);
	}

	public E saveOrUpdate(E object) {
		getHibernateTemplate().saveOrUpdate(object);
		return object;
	}

	@SuppressWarnings("unchecked")
	public S save(E object) {
		return (S) getHibernateTemplate().save(object);
	}

	public void update(E object) {
		getHibernateTemplate().update(object);
	}

	public E merge(E object) {
		getHibernateTemplate().merge(object);
		return object;
	}

	public void delete(E object) {
		getHibernateTemplate().delete(object);
	}

	public List<E> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		return findByCriteria(criteria);
	}

	public int findSize() {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).setProjection(Projections.rowCount());
		return this.get(criteria, Integer.class);
	}
}
