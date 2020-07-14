package com.stj.repo;

import com.stj.model.Player;
import com.stj.model.WeeklyScore;

import java.util.Date;
import java.util.List;

public interface WeeklyScoreRepository {

	List<WeeklyScore> findAllScores(Integer year);

	List<WeeklyScore> findAllScores(Date weekDate, List<Player> player);
}
