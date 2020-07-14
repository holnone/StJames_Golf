package com.stj.repo.impl;

import com.stj.model.*;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.CourseRepository;
import org.hibernate.criterion.DetachedCriteria;

public class HibernateCourseRepository extends BaseHibernateRepository<Course, Integer> implements CourseRepository {

	@Override
	protected Class<Course> getEntityClass() {
		return Course.class;
	}

	public TheKnolls getTheKnolls() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TheKnolls.class);
		return (TheKnolls) get(criteria);
	}

	public TheKnolls2013 getTheKnolls2013() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TheKnolls2013.class);
		return (TheKnolls2013) get(criteria);
	}

	public TheKnolls2019 getTheKnolls2019() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TheKnolls2019.class);
		return (TheKnolls2019) get(criteria);
	}

	public Ironwood getIronwood() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ironwood.class);
		return (Ironwood) get(criteria);
	}

}
