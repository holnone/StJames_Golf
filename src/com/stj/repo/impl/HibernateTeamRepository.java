package com.stj.repo.impl;

import com.stj.model.Team;
import com.stj.repo.BaseHibernateRepository;
import com.stj.repo.TeamRepository;

public class HibernateTeamRepository extends BaseHibernateRepository<Team, Integer> implements TeamRepository {

	@Override
	protected Class<Team> getEntityClass() {
		return Team.class;
	}

}
