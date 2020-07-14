package com.stj.repo;

import com.stj.model.Week;

public interface WeekRepository {
	Week saveOrUpdate(Week week);
	
	Week findById(Integer id);
}
