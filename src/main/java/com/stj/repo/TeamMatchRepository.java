package com.stj.repo;

import com.stj.model.TeamMatch;

public interface TeamMatchRepository {
	TeamMatch saveOrUpdate(TeamMatch match);
	
	TeamMatch findById(Integer id);
}
