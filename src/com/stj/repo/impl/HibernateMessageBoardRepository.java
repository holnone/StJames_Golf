package com.stj.repo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.stj.model.MessageBoard;
import com.stj.model.Season;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.MessageBoardRepository;

public class HibernateMessageBoardRepository extends BaseHibernateRepository<MessageBoard, Integer> implements MessageBoardRepository {

	@Override
	protected Class<MessageBoard> getEntityClass() {
		return MessageBoard.class;
	}

	@Override
	public List<MessageBoard> findAll(Season season) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		criteria.add(Restrictions.eq("season", season));
		criteria.addOrder(Order.desc("createdDate"));
		return findByCriteria(criteria);
	}

}
