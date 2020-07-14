package com.stj.repo;

import com.stj.model.Team;

import java.util.List;

public interface TeamRepository {
	Team saveOrUpdate(Team team);
	
	Team merge(Team team);
    
	Team findById(Integer id);

    List<Team> findAll();
}
