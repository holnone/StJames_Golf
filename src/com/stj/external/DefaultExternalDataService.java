package com.stj.external;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stj.external.model.ExternalMatch;
import com.stj.external.model.ExternalPlayer;
import com.stj.external.model.ExternalRoster;
import com.stj.external.model.ExternalTeam;
import com.stj.external.model.Schedule;
import com.stj.external.model.Score;
import com.stj.external.model.Standings;
import com.stj.model.ApplicationProperty;
import com.stj.model.Player;
import com.stj.model.PlayerScore;
import com.stj.model.Round;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.model.Week;
import com.stj.services.ApplicationPropertyService;
import com.stj.services.LeagueService;
import com.stj.util.Constants;

@Component
public class DefaultExternalDataService {

	@Autowired
	private LeagueService leagueService;

	@SpringBean
	private ApplicationPropertyService applicationPropertyService;

	public ExternalRoster getPlayers() {
		ExternalRoster roster = new ExternalRoster();
		List<ExternalPlayer> externalPlayers = new ArrayList<ExternalPlayer>();
		List<Player> players = leagueService.getAllPlayers();
		for (Player player : players) {
			if (player.isActive()) {
				externalPlayers.add(buildPlayer(player));
			}
		}
		roster.setPlayers(externalPlayers);
		return roster;
	}

	public Schedule getSchedule(Integer year) {
		Schedule schedule = new Schedule();
		schedule.setFrontNineTeeTime1("4:56 & 5:04");
		schedule.setFrontNineTeeTime2("5:12 & 5:20");
		schedule.setFrontNineTeeTime3("5:28 & 5:36");
		schedule.setBackNineTeeTime1("4:56 & 5:04");
		schedule.setBackNineTeeTime2("5:12 & 5:20");
		schedule.setBackNineTeeTime3("5:28 & 5:36");

		List<ExternalMatch> matches = new ArrayList<ExternalMatch>();
		schedule.setMatches(matches);

		Season season = leagueService.getSeason(year);
		List<Round> rounds = new ArrayList<Round>(season.getRounds());
		Collections.sort(rounds);
		Round round1 = rounds.get(0);
		Round round2 = rounds.get(1);

		List<Week> weekList = new ArrayList<Week>(round1.getWeeks());
		weekList.addAll(round2.getWeeks());
		Collections.sort(weekList);
		for (Week week : weekList) {
			matches.add(buildExternalMatch(week));
		}

		return schedule;
	}

	public Standings getStandings(Integer year) {
		Standings standings = new Standings();
		List<ExternalTeam> round1Teams = new ArrayList<ExternalTeam>();
		List<ExternalTeam> round2Teams = new ArrayList<ExternalTeam>();
		standings.setRound1Teams(round1Teams);
		standings.setRound2Teams(round2Teams);
		Season season = leagueService.getSeason(year);
		List<Round> rounds = new ArrayList<Round>(season.getRounds());
		Collections.sort(rounds);
		List<Team> currentStandings = rounds.get(0).getStandings(season, null);
		for (Team team : currentStandings) {
			round1Teams.add(buildTeam(team));
		}
		currentStandings = rounds.get(1).getStandings(season, null);
		for (Team team : currentStandings) {
			round2Teams.add(buildTeam(team));
		}
		return standings;
	}

	public String getPublicMessage() {
		ApplicationProperty publicMessage = applicationPropertyService.find(Constants.PUBLIC_MESSAGE);
		return publicMessage != null ? publicMessage.getValue() : "";
	}

	private ExternalPlayer buildPlayer(Player player) {
		ExternalPlayer newPlayer = new ExternalPlayer();
		newPlayer.setFirstName(player.getName().getFirstName());
		newPlayer.setLastName(player.getName().getLastName());
		newPlayer.setTeam(player.getTeam() != null ? buildTeam(player.getTeam()) : null);
		newPlayer.setScores(convertScores(player.getLast5Rounds(new Date())));
		newPlayer.setCurrentHandicap(newPlayer.getCurrentHandicap(new Date()));

		return newPlayer;
	}

	private ExternalTeam buildTeam(Team team) {
		ExternalTeam newTeam = new ExternalTeam();
		newTeam.setName(team.getName());
		newTeam.setPoints(team.getPoints());
		newTeam.setTeamNumber(team.getTeamNumber());

		return newTeam;
	}

	private List<Score> convertScores(List<PlayerScore> playerScores) {
		List<Score> scores = new ArrayList<Score>();
		for (PlayerScore playerScore : playerScores) {
			Score newScore = new Score();
			newScore.setPar(playerScore.getSide().getPar());
			newScore.setScore(playerScore.getScore());
			newScore.setScoreDate(playerScore.getScoreDate());
			scores.add(newScore);
		}

		return scores;
	}

	private ExternalMatch buildExternalMatch(Week week) {
		ExternalMatch newMatch = new ExternalMatch();
		newMatch.setDate(week.getDate());

		newMatch.setFntt1TeamNumber1(week.getFrontNineTeeTime1().getTeam1().getTeam().getTeamNumber());
		newMatch.setFntt1TeamNumber2(week.getFrontNineTeeTime1().getTeam2().getTeam().getTeamNumber());
		newMatch.setFntt2TeamNumber1(week.getFrontNineTeeTime2().getTeam1().getTeam().getTeamNumber());
		newMatch.setFntt2TeamNumber2(week.getFrontNineTeeTime2().getTeam2().getTeam().getTeamNumber());
		newMatch.setFntt3TeamNumber1(week.getFrontNineTeeTime3().getTeam1().getTeam().getTeamNumber());
		newMatch.setFntt3TeamNumber2(week.getFrontNineTeeTime3().getTeam2().getTeam().getTeamNumber());

		newMatch.setBntt1TeamNumber1(week.getBackNineTeeTime1().getTeam1().getTeam().getTeamNumber());
		newMatch.setBntt1TeamNumber2(week.getBackNineTeeTime1().getTeam2().getTeam().getTeamNumber());
		newMatch.setBntt2TeamNumber1(week.getBackNineTeeTime2().getTeam1().getTeam().getTeamNumber());
		newMatch.setBntt2TeamNumber2(week.getBackNineTeeTime2().getTeam2().getTeam().getTeamNumber());
		newMatch.setBntt3TeamNumber1(week.getBackNineTeeTime3().getTeam1().getTeam().getTeamNumber());
		newMatch.setBntt3TeamNumber2(week.getBackNineTeeTime3().getTeam2().getTeam().getTeamNumber());

		return newMatch;
	}
}
