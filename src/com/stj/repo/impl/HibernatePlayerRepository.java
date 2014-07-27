package com.stj.repo.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.stj.model.Player;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.PlayerRepository;

public class HibernatePlayerRepository extends BaseHibernateRepository<Player, Integer> implements PlayerRepository {

	@Override
	protected Class<Player> getEntityClass() {
		return Player.class;
	}

	public List<Player> findNonTeamPlayers() {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		criteria.add(Restrictions.isNull("team"));
		criteria.add(Restrictions.eq("active", Boolean.TRUE));

		return findByCriteria(criteria);
	}

	public List<Player> findAllActive() {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.getEntityClass());
		criteria.add(Restrictions.eq("active", Boolean.TRUE));

		return this.findByCriteria(criteria);

	}

	public List<Player> findAllInactive() {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.getEntityClass());
		criteria.add(Restrictions.eq("active", Boolean.FALSE));

		return this.findByCriteria(criteria);

	}

	public List<Player> findSkinsPlayers(Date date) {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.getEntityClass());
		criteria.add(Restrictions.le("skinsStartDate", date));

		return findByCriteria(criteria);
	}
}
