package com.stj.repo;

import com.stj.model.BackNine;
import com.stj.model.FrontNine;

public interface SideRepository {
	FrontNine getFrontNine();

	BackNine getBackNine();
}
