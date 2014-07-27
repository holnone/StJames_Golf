package com.stj.repo.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.stj.model.Week;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.WeekRepository;

public class HibernateWeekRepository extends BaseHibernateRepository<Week, Integer> implements WeekRepository {

	@Override
	protected Class<Week> getEntityClass() {
		return Week.class;
	}

	public Week findByDate(Date date) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		criteria.add(Restrictions.eq("date", date));
		Week week = get(criteria);
		return week;
	}
}
