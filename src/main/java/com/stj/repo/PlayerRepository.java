package com.stj.repo;

import com.stj.model.Player;

import java.util.Date;
import java.util.List;

public interface PlayerRepository {
	Player saveOrUpdate(Player player);

	Player findById(Integer id);

	List<Player> findAll();

	List<Player> findNonTeamPlayers();

	List<Player> findAllActive();

	List<Player> findAllInactive();

}
