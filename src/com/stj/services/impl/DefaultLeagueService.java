package com.stj.services.impl;

import java.util.Collections;
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
import com.stj.repo.CourseRepository;
import com.stj.repo.MessageBoardRepository;
import com.stj.repo.PlayerRepository;
import com.stj.repo.SeasonRepository;
import com.stj.repo.TeamMatchRepository;
import com.stj.repo.TeamRepository;
import com.stj.repo.WeekRepository;
import com.stj.repo.WeeklyScoreRepository;
import com.stj.services.LeagueService;

public class DefaultLeagueService implements LeagueService {

	private SeasonRepository seasonRepository;
	private CourseRepository courseRepository;
	private TeamRepository teamRepository;
	private PlayerRepository playerRepository;
	private WeekRepository weekRepository;
	private TeamMatchRepository teamMatchRepository;
	private WeeklyScoreRepository weeklyScoreRepository;
	private MessageBoardRepository messageBoardRepository;

	public List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	public List<Player> getNonTeamPlayers() {
		return playerRepository.findNonTeamPlayers();
	}

	public List<Player> getAllInactivePlayers() {
		return playerRepository.findAllInactive();
	}

	public List<Player> getSkinsPlayers(Date date) {
		return playerRepository.findSkinsPlayers(date);
	}

	public Player getPlayer(Integer playerId) {
		return playerRepository.findById(playerId);
	}

	public Player savePlayer(Player player) {
		return playerRepository.saveOrUpdate(player);
	}

	public Season getSeason(Integer year) {
		return seasonRepository.findByYear(year);
	}

	public Season saveSeason(Season season) {
		return seasonRepository.saveOrUpdate(season);
	}

	public TeamMatch saveTeamMatch(TeamMatch match) {
		return teamMatchRepository.saveOrUpdate(match);
	}

	public TeamMatch getTeamMatch(Integer id) {
		return teamMatchRepository.findById(id);
	}
	public List<Team> getAllTeams() {
		List<Team> list = teamRepository.findAll();
		Collections.sort(list);
		return list;
	}

	public Team getTeam(Integer teamId) {
		return teamRepository.findById(teamId);
	}

	public Team saveTeam(Team team) {
		return teamRepository.saveOrUpdate(team);
	}

	public Team mergeTeam(Team team) {
		return teamRepository.merge(team);
	}

	public TheKnolls getTheKnolls() {
		return courseRepository.getTheKnolls();
	}

	public TheKnolls2013 getTheKnolls2013() {
		return courseRepository.getTheKnolls2013();
	}

	public Ironwood getIronwood() {
		return courseRepository.getIronwood();
	}

	public Week saveWeek(Week week) {
		return weekRepository.saveOrUpdate(week);
	}

	public Week getWeek(Integer id) {
		return weekRepository.findById(id);
	}

	public void setSeasonRepository(SeasonRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	public void setCourseRepository(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public void setTeamRepository(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public void setPlayerRepository(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public void setWeekRepository(WeekRepository weekRepository) {
		this.weekRepository = weekRepository;
	}

	public void setTeamMatchRepository(TeamMatchRepository teamMatchRepository) {
		this.teamMatchRepository = teamMatchRepository;
	}

	public void setWeeklyScoreRepository(WeeklyScoreRepository weeklyScoreRepository) {
		this.weeklyScoreRepository = weeklyScoreRepository;
	}

	public List<WeeklyScore> getScores(Integer year) {
		return weeklyScoreRepository.findAllScores(year);
	}

	public List<WeeklyScore> getScores(Date weekDate, List<Player> players) {
		return weeklyScoreRepository.findAllScores(weekDate, players);
	}

	public void setMessageBoardRepository(MessageBoardRepository messageBoardRepository) {
		this.messageBoardRepository = messageBoardRepository;
	}

	public List<MessageBoard> getMessageBoard(Season season) {
		return messageBoardRepository.findAll(season);
	}

	public MessageBoard saveMessageBoard(MessageBoard messageBoard) {
		return messageBoardRepository.saveOrUpdate(messageBoard);
	}

}
