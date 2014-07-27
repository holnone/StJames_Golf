package com.stj.repo;

import java.util.Date;
import java.util.List;

import com.stj.model.Player;
import com.stj.model.WeeklyScore;

public interface WeeklyScoreRepository {

	List<WeeklyScore> findAllScores(Integer year);

	List<WeeklyScore> findAllScores(Date weekDate, List<Player> player);
}
