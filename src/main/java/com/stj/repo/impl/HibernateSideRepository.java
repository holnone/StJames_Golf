package com.stj.repo.impl;

import com.stj.model.BackNine;
import com.stj.model.FrontNine;
import com.stj.model.Side;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.SideRepository;
import org.hibernate.criterion.DetachedCriteria;

public class HibernateSideRepository extends BaseHibernateRepository<Side, Integer> implements SideRepository {

	@Override
	protected Class<Side> getEntityClass() {
		return Side.class;
	}

	public BackNine getBackNine() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BackNine.class);
		return (BackNine) get(criteria);
	}

	public FrontNine getFrontNine() {
		DetachedCriteria criteria = DetachedCriteria.forClass(FrontNine.class);
		return (FrontNine) get(criteria);
	}

}
