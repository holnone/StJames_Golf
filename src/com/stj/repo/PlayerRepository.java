package com.stj.repo;

import java.util.Date;
import java.util.List;

import com.stj.model.Player;

public interface PlayerRepository {
	Player saveOrUpdate(Player player);

	Player findById(Integer id);

	List<Player> findAll();

	List<Player> findNonTeamPlayers();

	List<Player> findAllActive();

	List<Player> findAllInactive();

	List<Player> findSkinsPlayers(Date date);
}
