package com.stj.repo.impl;

import com.stj.model.TeamMatch;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.TeamMatchRepository;

public class HibernateTeamMatchRepository extends BaseHibernateRepository<TeamMatch, Integer> implements TeamMatchRepository {

	@Override
	protected Class<TeamMatch> getEntityClass() {
		return TeamMatch.class;
	}

}
