package com.stj.repo;

import com.stj.model.Ironwood;
import com.stj.model.TheKnolls;
import com.stj.model.TheKnolls2013;
import com.stj.model.TheKnolls2019;

public interface CourseRepository {
	TheKnolls getTheKnolls();

	Ironwood getIronwood();
	
	TheKnolls2013 getTheKnolls2013();

	TheKnolls2019 getTheKnolls2019();
}
