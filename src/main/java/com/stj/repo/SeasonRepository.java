package com.stj.repo;

import com.stj.model.Season;

import java.util.List;

public interface SeasonRepository {
	Season saveOrUpdate(Season season);
    
    Season findById(Integer id);

    List<Season> findAll();
    
    Season findByYear(Integer year);
}
