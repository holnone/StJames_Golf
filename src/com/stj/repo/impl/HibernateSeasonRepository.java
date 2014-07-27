package com.stj.repo.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.stj.model.Season;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.SeasonRepository;

public class HibernateSeasonRepository extends BaseHibernateRepository<Season, Integer> implements SeasonRepository {

	@Override
	protected Class<Season> getEntityClass() {
		return Season.class;
	}

	public Season findByYear(Integer year) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		criteria.add(Restrictions.eq("year", year));
		Season season = get(criteria);
		return season;
	}

}
