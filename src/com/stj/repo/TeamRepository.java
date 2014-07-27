package com.stj.repo;

import java.util.List;

import com.stj.model.Team;

public interface TeamRepository {
	Team saveOrUpdate(Team team);
	
	Team merge(Team team);
    
	Team findById(Integer id);

    List<Team> findAll();
}
