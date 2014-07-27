package com.stj.repo;

import java.util.List;

import com.stj.model.Season;

public interface SeasonRepository {
	Season saveOrUpdate(Season season);
    
    Season findById(Integer id);

    List<Season> findAll();
    
    Season findByYear(Integer year);
}
