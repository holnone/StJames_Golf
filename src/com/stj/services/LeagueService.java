package com.stj.services;

import java.util.Date;
import java.util.List;

import com.stj.model.Ironwood;
import com.stj.model.MessageBoard;
import com.stj.model.Player;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.model.TeamMatch;
import com.stj.model.TheKnolls;
import com.stj.model.TheKnolls2013;
import com.stj.model.Week;
import com.stj.model.WeeklyScore;

public interface LeagueService {

	public Season getSeason(Integer year);

	public Season saveSeason(Season season);

	public List<Team> getAllTeams();

	public Team getTeam(Integer teamId);

	public Team saveTeam(Team team);

	public Team mergeTeam(Team team);

	public List<Player> getAllPlayers();

	public List<Player> getNonTeamPlayers();

	public List<Player> getAllInactivePlayers();

	public List<Player> getSkinsPlayers(Date date);

	public Player getPlayer(Integer playerId);

	public Player savePlayer(Player player);

	public TheKnolls getTheKnolls();

	public TheKnolls2013 getTheKnolls2013();

	public Ironwood getIronwood();

	public Week saveWeek(Week week);

	public Week getWeek(Integer id);

	public TeamMatch saveTeamMatch(TeamMatch match);
	
	public TeamMatch getTeamMatch(Integer id);

	public List<WeeklyScore> getScores(Integer year);

	public List<WeeklyScore> getScores(Date weekDate, List<Player> players);

	public List<MessageBoard> getMessageBoard(Season season);

	public MessageBoard saveMessageBoard(MessageBoard messageBoard);

}
