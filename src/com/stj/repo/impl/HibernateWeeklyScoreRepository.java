package com.stj.repo.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.stj.model.Player;
import com.stj.model.WeeklyScore;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.WeeklyScoreRepository;

public class HibernateWeeklyScoreRepository extends BaseHibernateRepository<WeeklyScore, Integer> implements WeeklyScoreRepository {

	@Override
	protected Class<WeeklyScore> getEntityClass() {
		return WeeklyScore.class;
	}

	public List<WeeklyScore> findAllScores(Integer year) {
		Calendar first = Calendar.getInstance();
		first.set(Calendar.MONTH, Calendar.JANUARY);
		first.set(Calendar.DAY_OF_MONTH, 1);
		first.set(Calendar.YEAR, year);
		Calendar last = Calendar.getInstance();
		last.set(Calendar.MONTH, Calendar.DECEMBER);
		last.set(Calendar.DAY_OF_MONTH, 31);
		last.set(Calendar.YEAR, year);
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		criteria.add(Restrictions.eq("ghostScore", false));
		criteria.add(Restrictions.between("scoreDate", first.getTime(), last.getTime()));

		return findByCriteria(criteria);
	}

	public List<WeeklyScore> findAllScores(Date weekDate, List<Player> players) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		criteria.add(Restrictions.eq("ghostScore", false));
		criteria.add(Restrictions.eq("scoreDate", weekDate));
		criteria.add(Restrictions.in("player", players));

		return findByCriteria(criteria);
	}

}
