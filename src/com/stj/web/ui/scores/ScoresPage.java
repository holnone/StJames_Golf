package com.stj.web.ui.scores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Course;
import com.stj.model.GhostPlayer;
import com.stj.model.MatchResults;
import com.stj.model.Player;
import com.stj.model.Round;
import com.stj.model.Season;
import com.stj.model.Side;
import com.stj.model.Team;
import com.stj.model.TeamMatch;
import com.stj.model.Week;
import com.stj.model.WeeklyScore;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.util.HoleUtils;
import com.stj.web.ui.base.BasePage;
import com.stj.web.wicket.form.input.LabelList;

public class ScoresPage extends BasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	private List<Week> weekChoices = new ArrayList<Week>();
	private List<TeamMatch> matchChoices = new ArrayList<TeamMatch>();

	private Week selectedWeek;
	private TeamMatch selectedTeamMatch;

	private Course matchCourse;

	private Team team1;
	private Team team2;

	private List<String> matchAHoleNumbers = new ArrayList<String>(HoleUtils.FRONT_HOLE_NUMBERS);
	private List<String> matchBHoleNumbers = new ArrayList<String>(HoleUtils.FRONT_HOLE_NUMBERS);
	private List<String> matchCHoleNumbers = new ArrayList<String>(HoleUtils.FRONT_HOLE_NUMBERS);
	private List<String> matchDHoleNumbers = new ArrayList<String>(HoleUtils.FRONT_HOLE_NUMBERS);

	private List<String> matchAHoleHandicaps = new ArrayList<String>(HoleUtils.getHandicaps(null));
	private List<String> matchBHoleHandicaps = new ArrayList<String>(HoleUtils.getHandicaps(null));
	private List<String> matchCHoleHandicaps = new ArrayList<String>(HoleUtils.getHandicaps(null));
	private List<String> matchDHoleHandicaps = new ArrayList<String>(HoleUtils.getHandicaps(null));

	private Integer team1Net = 0;
	private Double team1NetPoints = new Double(0);
	private Integer team2Net = 0;
	private Double team2NetPoints = new Double(0);
	private Double totalTeam1Points = new Double(0);
	private Double totalTeam2Points = new Double(0);

	private Side matchASide;
	private Player matchATeam1Player;
	private Integer matchATeam1Handicap;
	private Player matchATeam2Player;
	private Integer matchATeam2Handicap;
	private Side matchBSide;
	private Player matchBTeam1Player;
	private Integer matchBTeam1Handicap;
	private Player matchBTeam2Player;
	private Integer matchBTeam2Handicap;
	private Side matchCSide;
	private Player matchCTeam1Player;
	private Integer matchCTeam1Handicap;
	private Player matchCTeam2Player;
	private Integer matchCTeam2Handicap;
	private Side matchDSide;
	private Player matchDTeam1Player;
	private Integer matchDTeam1Handicap;
	private Player matchDTeam2Player;
	private Integer matchDTeam2Handicap;

	private List<String> matchATeam1Holes = new ArrayList<String>();
	private Integer matchATeam1PlayerGross = 0;
	private Integer matchATeam1PlayerNet = 0;
	private Double matchATeam1PlayerNetPoints = new Double(0);
	private Double matchATeam1PlayerHolePoints = new Double(0);

	private List<String> matchATeam2Holes = new ArrayList<String>();
	private Integer matchATeam2PlayerGross = 0;
	private Integer matchATeam2PlayerNet = 0;
	private Double matchATeam2PlayerNetPoints = new Double(0);
	private Double matchATeam2PlayerHolePoints = new Double(0);

	private List<String> matchBTeam1Holes = new ArrayList<String>();
	private Integer matchBTeam1PlayerGross = 0;
	private Integer matchBTeam1PlayerNet = 0;
	private Double matchBTeam1PlayerNetPoints = new Double(0);
	private Double matchBTeam1PlayerHolePoints = new Double(0);

	private List<String> matchBTeam2Holes = new ArrayList<String>();
	private Integer matchBTeam2PlayerGross = 0;
	private Integer matchBTeam2PlayerNet = 0;
	private Double matchBTeam2PlayerNetPoints = new Double(0);
	private Double matchBTeam2PlayerHolePoints = new Double(0);

	private List<String> matchCTeam1Holes = new ArrayList<String>();
	private Integer matchCTeam1PlayerGross = 0;
	private Integer matchCTeam1PlayerNet = 0;
	private Double matchCTeam1PlayerNetPoints = new Double(0);
	private Double matchCTeam1PlayerHolePoints = new Double(0);

	private List<String> matchCTeam2Holes = new ArrayList<String>();
	private Integer matchCTeam2PlayerGross = 0;
	private Integer matchCTeam2PlayerNet = 0;
	private Double matchCTeam2PlayerNetPoints = new Double(0);
	private Double matchCTeam2PlayerHolePoints = new Double(0);

	private List<String> matchDTeam1Holes = new ArrayList<String>();
	private Integer matchDTeam1PlayerGross = 0;
	private Integer matchDTeam1PlayerNet = 0;
	private Double matchDTeam1PlayerNetPoints = new Double(0);
	private Double matchDTeam1PlayerHolePoints = new Double(0);

	private List<String> matchDTeam2Holes = new ArrayList<String>();
	private Integer matchDTeam2PlayerGross = 0;
	private Integer matchDTeam2PlayerNet = 0;
	private Double matchDTeam2PlayerNetPoints = new Double(0);
	private Double matchDTeam2PlayerHolePoints = new Double(0);

	WebMarkupContainer matchContainer;
	DropDownChoice<TeamMatch> teamMatchDropDown;

	String[] matches = new String[] { "A", "B", "C", "D" };

	public ScoresPage() {
		initPage();
	}

	private void initPage() {
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);
		List<Round> list = new ArrayList<Round>(season.getRounds());
		for (Round round : list) {
			weekChoices.addAll(round.getWeeks());
		}
		Collections.sort(weekChoices);
		selectedWeek = weekChoices.get(0);
		for (Week week : weekChoices) {
			if (week.getDate().before(Calendar.getInstance().getTime())) {
				selectedWeek = week;
			}
		}

		renderContent();
		selectedTeamMatch = selectedWeek.getFrontNineTeeTime1();
		resetMatchups();
	}

	private void resetMatchups() {
		matchChoices.clear();
		if (selectedWeek.getFrontNineTeeTime1() != null) {
			matchChoices.add(selectedWeek.getFrontNineTeeTime1());
			matchChoices.add(selectedWeek.getFrontNineTeeTime2());
			matchChoices.add(selectedWeek.getFrontNineTeeTime3());
			matchChoices.add(selectedWeek.getBackNineTeeTime1());
			matchChoices.add(selectedWeek.getBackNineTeeTime2());
			matchChoices.add(selectedWeek.getBackNineTeeTime3());
		}
		resetMatches();
	}

	private void resetMatches() {
		matchCourse = selectedTeamMatch != null ? selectedTeamMatch.getCourse() : null;
		team1 = selectedTeamMatch != null ? selectedTeamMatch.getTeam1().getTeam() : null;
		team2 = selectedTeamMatch != null ? selectedTeamMatch.getTeam2().getTeam() : null;

		resetMatchA();
		resetMatchB();
		resetMatchC();
		resetMatchD();

		team1Net = selectedTeamMatch != null ? selectedTeamMatch.getTeam1().getNetScore() : 0;
		team2Net = selectedTeamMatch != null ? selectedTeamMatch.getTeam2().getNetScore() : 0;
		boolean team1HasGhost = selectedTeamMatch != null ? selectedTeamMatch.team1HasGhost() : false;
		boolean team2HasGhost = selectedTeamMatch != null ? selectedTeamMatch.team2HasGhost() : false;
		if (((team1Net < team2Net) || team2HasGhost) && !team1HasGhost) {
			team1NetPoints = new Double(2);
			team2NetPoints = new Double(0);
		} else if (((team1Net > team2Net) || team1HasGhost) && !team2HasGhost) {
			team1NetPoints = new Double(0);
			team2NetPoints = new Double(2);
		} else if (team1HasGhost && team2HasGhost) {
			team1NetPoints = new Double(0);
			team2NetPoints = new Double(0);
		} else {
			team1NetPoints = new Double(1);
			team2NetPoints = new Double(1);
		}
		totalTeam1Points = new Double(0);
		totalTeam1Points += team1NetPoints;
		totalTeam1Points += matchATeam1PlayerHolePoints + matchATeam1PlayerNetPoints;
		totalTeam1Points += matchBTeam1PlayerHolePoints + matchBTeam1PlayerNetPoints;
		totalTeam1Points += matchCTeam1PlayerHolePoints + matchCTeam1PlayerNetPoints;
		totalTeam1Points += matchDTeam1PlayerHolePoints + matchDTeam1PlayerNetPoints;
		totalTeam2Points = new Double(0);
		totalTeam2Points += team2NetPoints;
		totalTeam2Points += matchATeam2PlayerHolePoints + matchATeam2PlayerNetPoints;
		totalTeam2Points += matchBTeam2PlayerHolePoints + matchBTeam2PlayerNetPoints;
		totalTeam2Points += matchCTeam2PlayerHolePoints + matchCTeam2PlayerNetPoints;
		totalTeam2Points += matchDTeam2PlayerHolePoints + matchDTeam2PlayerNetPoints;
	}

	private void resetMatchA() {
		setMatchASide(null);
		setMatchATeam1Player(null);
		getMatchATeam1Holes().clear();
		setMatchATeam1Handicap(null);
		setMatchATeam2Handicap(null);
		setMatchATeam2Player(null);
		getMatchATeam2Holes().clear();

		matchATeam1PlayerGross = 0;
		matchATeam1PlayerNet = 0;
		matchATeam1PlayerNetPoints = new Double(0);
		matchATeam1PlayerHolePoints = new Double(0);
		matchATeam2PlayerGross = 0;
		matchATeam2PlayerNet = 0;
		matchATeam2PlayerNetPoints = new Double(0);
		matchATeam2PlayerHolePoints = new Double(0);

		if (team1 != null) {
			MatchResults matchResults = getSelectedTeamMatch().getMatchAResults();
			WeeklyScore team1MatchA = getSelectedTeamMatch().getTeam1().getMatchA();
			WeeklyScore team2MatchA = getSelectedTeamMatch().getTeam2().getMatchA();
			if (getSelectedTeamMatch() != null && team1MatchA != null) {
				setMatchASide(team1MatchA.getSide());

				getMatchAHoleNumbers().clear();
				getMatchAHoleHandicaps().clear();
				getMatchAHoleNumbers()
						.addAll("FT".equals(getMatchASide().getSideType()) ? HoleUtils.FRONT_HOLE_NUMBERS : HoleUtils.BACK_HOLE_NUMBERS);
				getMatchAHoleHandicaps().addAll(HoleUtils.getHandicaps(getMatchASide()));

				// Player 1
				setMatchATeam1Player(team1MatchA.isGhostScore() ? new GhostPlayer() : team1MatchA.getPlayer());
				setMatchATeam1Handicap(team1MatchA.getHandicap());

				getMatchATeam1Holes().add(team1MatchA.getHole1Score() != null ? team1MatchA.getHole1Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole2Score() != null ? team1MatchA.getHole2Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole3Score() != null ? team1MatchA.getHole3Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole4Score() != null ? team1MatchA.getHole4Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole5Score() != null ? team1MatchA.getHole5Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole6Score() != null ? team1MatchA.getHole6Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole7Score() != null ? team1MatchA.getHole7Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole8Score() != null ? team1MatchA.getHole8Score().toString() : "");
				getMatchATeam1Holes().add(team1MatchA.getHole9Score() != null ? team1MatchA.getHole9Score().toString() : "");

				setMatchATeam1PlayerGross(team1MatchA.getScore());
				setMatchATeam1PlayerNet(team1MatchA.getNet());
				setMatchATeam1PlayerHolePoints(matchResults.getPlayer1HolePoints());
				setMatchATeam1PlayerNetPoints(matchResults.getPlayer1NetPoints());

				// Player 2
				setMatchATeam2Player(team2MatchA.isGhostScore() ? new GhostPlayer() : team2MatchA.getPlayer());
				setMatchATeam2Handicap(team2MatchA.getHandicap());
				getMatchATeam2Holes().add(team2MatchA.getHole1Score() != null ? team2MatchA.getHole1Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole2Score() != null ? team2MatchA.getHole2Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole3Score() != null ? team2MatchA.getHole3Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole4Score() != null ? team2MatchA.getHole4Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole5Score() != null ? team2MatchA.getHole5Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole6Score() != null ? team2MatchA.getHole6Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole7Score() != null ? team2MatchA.getHole7Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole8Score() != null ? team2MatchA.getHole8Score().toString() : "");
				getMatchATeam2Holes().add(team2MatchA.getHole9Score() != null ? team2MatchA.getHole9Score().toString() : "");
				setMatchATeam2PlayerGross(team2MatchA.getScore());
				setMatchATeam2PlayerNet(team2MatchA.getNet());
				setMatchATeam2PlayerHolePoints(matchResults.getPlayer2HolePoints());
				setMatchATeam2PlayerNetPoints(matchResults.getPlayer2NetPoints());

				for (int i = 0; i < 9; i++) {
					Component component1 = getMatchContainer().get("matchATeam1PlayerHole");
					if (matchResults.getPlayer1HolesWon().contains(i + 1)) {
						((LabelList) component1).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component1).add(i, AttributeModifier.replace("class", ""));
					}
					Component component2 = getMatchContainer().get("matchATeam2PlayerHole");
					if (matchResults.getPlayer2HolesWon().contains(i + 1)) {
						((LabelList) component2).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component2).add(i, AttributeModifier.replace("class", ""));
					}
				}
			}
		}
	}

	private void resetMatchB() {
		setMatchBSide(null);
		setMatchBTeam1Player(null);
		getMatchBTeam1Holes().clear();
		setMatchBTeam1Handicap(null);
		setMatchBTeam2Handicap(null);
		setMatchBTeam2Player(null);
		getMatchBTeam2Holes().clear();

		matchBTeam1PlayerGross = 0;
		matchBTeam1PlayerNet = 0;
		matchBTeam1PlayerNetPoints = new Double(0);
		matchBTeam1PlayerHolePoints = new Double(0);
		matchBTeam2PlayerGross = 0;
		matchBTeam2PlayerNet = 0;
		matchBTeam2PlayerNetPoints = new Double(0);
		matchBTeam2PlayerHolePoints = new Double(0);

		if (team1 != null) {
			MatchResults matchResults = getSelectedTeamMatch().getMatchBResults();
			WeeklyScore team1MatchB = getSelectedTeamMatch().getTeam1().getMatchB();
			WeeklyScore team2MatchB = getSelectedTeamMatch().getTeam2().getMatchB();
			if (getSelectedTeamMatch() != null && team1MatchB != null) {
				setMatchBSide(team1MatchB.getSide());

				getMatchBHoleNumbers().clear();
				getMatchBHoleHandicaps().clear();
				getMatchBHoleNumbers()
						.addAll("FT".equals(getMatchBSide().getSideType()) ? HoleUtils.FRONT_HOLE_NUMBERS : HoleUtils.BACK_HOLE_NUMBERS);
				getMatchBHoleHandicaps().addAll(HoleUtils.getHandicaps(getMatchBSide()));

				// Player 1
				setMatchBTeam1Player(team1MatchB.isGhostScore() ? new GhostPlayer() : team1MatchB.getPlayer());
				setMatchBTeam1Handicap(team1MatchB.getHandicap());

				getMatchBTeam1Holes().add(team1MatchB.getHole1Score() != null ? team1MatchB.getHole1Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole2Score() != null ? team1MatchB.getHole2Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole3Score() != null ? team1MatchB.getHole3Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole4Score() != null ? team1MatchB.getHole4Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole5Score() != null ? team1MatchB.getHole5Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole6Score() != null ? team1MatchB.getHole6Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole7Score() != null ? team1MatchB.getHole7Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole8Score() != null ? team1MatchB.getHole8Score().toString() : "");
				getMatchBTeam1Holes().add(team1MatchB.getHole9Score() != null ? team1MatchB.getHole9Score().toString() : "");

				setMatchBTeam1PlayerGross(team1MatchB.getScore());
				setMatchBTeam1PlayerNet(team1MatchB.getNet());
				setMatchBTeam1PlayerHolePoints(matchResults.getPlayer1HolePoints());
				setMatchBTeam1PlayerNetPoints(matchResults.getPlayer1NetPoints());

				// Player 2
				setMatchBTeam2Player(team2MatchB.isGhostScore() ? new GhostPlayer() : team2MatchB.getPlayer());
				setMatchBTeam2Handicap(team2MatchB.getHandicap());

				getMatchBTeam2Holes().add(team2MatchB.getHole1Score() != null ? team2MatchB.getHole1Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole2Score() != null ? team2MatchB.getHole2Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole3Score() != null ? team2MatchB.getHole3Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole4Score() != null ? team2MatchB.getHole4Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole5Score() != null ? team2MatchB.getHole5Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole6Score() != null ? team2MatchB.getHole6Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole7Score() != null ? team2MatchB.getHole7Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole8Score() != null ? team2MatchB.getHole8Score().toString() : "");
				getMatchBTeam2Holes().add(team2MatchB.getHole9Score() != null ? team2MatchB.getHole9Score().toString() : "");

				setMatchBTeam2PlayerGross(team2MatchB.getScore());
				setMatchBTeam2PlayerNet(team2MatchB.getNet());
				setMatchBTeam2PlayerHolePoints(matchResults.getPlayer2HolePoints());
				setMatchBTeam2PlayerNetPoints(matchResults.getPlayer2NetPoints());

				for (int i = 0; i < 9; i++) {
					Component component1 = getMatchContainer().get("matchBTeam1PlayerHole");
					if (matchResults.getPlayer1HolesWon().contains(i + 1)) {
						((LabelList) component1).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component1).add(i, AttributeModifier.replace("class", ""));
					}
					Component component2 = getMatchContainer().get("matchBTeam2PlayerHole");
					if (matchResults.getPlayer2HolesWon().contains(i + 1)) {
						((LabelList) component2).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component2).add(i, AttributeModifier.replace("class", ""));
					}
				}
			}
		}
	}

	private void resetMatchC() {
		setMatchCSide(null);
		setMatchCTeam1Player(null);
		getMatchCTeam1Holes().clear();
		setMatchCTeam1Handicap(null);
		setMatchCTeam2Handicap(null);
		setMatchCTeam2Player(null);
		getMatchCTeam2Holes().clear();

		matchCTeam1PlayerGross = 0;
		matchCTeam1PlayerNet = 0;
		matchCTeam1PlayerNetPoints = new Double(0);
		matchCTeam1PlayerHolePoints = new Double(0);
		matchCTeam2PlayerGross = 0;
		matchCTeam2PlayerNet = 0;
		matchCTeam2PlayerNetPoints = new Double(0);
		matchCTeam2PlayerHolePoints = new Double(0);

		if (team1 != null) {
			MatchResults matchResults = getSelectedTeamMatch().getMatchCResults();
			WeeklyScore team1MatchC = getSelectedTeamMatch().getTeam1().getMatchC();
			WeeklyScore team2MatchC = getSelectedTeamMatch().getTeam2().getMatchC();
			if (getSelectedTeamMatch() != null && team1MatchC != null) {
				setMatchCSide(team1MatchC.getSide());

				getMatchCHoleNumbers().clear();
				getMatchCHoleHandicaps().clear();
				getMatchCHoleNumbers()
						.addAll("FT".equals(getMatchCSide().getSideType()) ? HoleUtils.FRONT_HOLE_NUMBERS : HoleUtils.BACK_HOLE_NUMBERS);
				getMatchCHoleHandicaps().addAll(HoleUtils.getHandicaps(getMatchCSide()));

				// Player 1
				setMatchCTeam1Player(team1MatchC.isGhostScore() ? new GhostPlayer() : team1MatchC.getPlayer());
				setMatchCTeam1Handicap(team1MatchC.getHandicap());

				getMatchCTeam1Holes().add(team1MatchC.getHole1Score() != null ? team1MatchC.getHole1Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole2Score() != null ? team1MatchC.getHole2Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole3Score() != null ? team1MatchC.getHole3Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole4Score() != null ? team1MatchC.getHole4Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole5Score() != null ? team1MatchC.getHole5Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole6Score() != null ? team1MatchC.getHole6Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole7Score() != null ? team1MatchC.getHole7Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole8Score() != null ? team1MatchC.getHole8Score().toString() : "");
				getMatchCTeam1Holes().add(team1MatchC.getHole9Score() != null ? team1MatchC.getHole9Score().toString() : "");

				setMatchCTeam1PlayerGross(team1MatchC.getScore());
				setMatchCTeam1PlayerNet(team1MatchC.getNet());
				setMatchCTeam1PlayerHolePoints(matchResults.getPlayer1HolePoints());
				setMatchCTeam1PlayerNetPoints(matchResults.getPlayer1NetPoints());

				// Player 2
				setMatchCTeam2Player(team2MatchC.isGhostScore() ? new GhostPlayer() : team2MatchC.getPlayer());
				setMatchCTeam2Handicap(team2MatchC.getHandicap());

				getMatchCTeam2Holes().add(team2MatchC.getHole1Score() != null ? team2MatchC.getHole1Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole2Score() != null ? team2MatchC.getHole2Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole3Score() != null ? team2MatchC.getHole3Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole4Score() != null ? team2MatchC.getHole4Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole5Score() != null ? team2MatchC.getHole5Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole6Score() != null ? team2MatchC.getHole6Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole7Score() != null ? team2MatchC.getHole7Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole8Score() != null ? team2MatchC.getHole8Score().toString() : "");
				getMatchCTeam2Holes().add(team2MatchC.getHole9Score() != null ? team2MatchC.getHole9Score().toString() : "");

				setMatchCTeam2PlayerGross(team2MatchC.getScore());
				setMatchCTeam2PlayerNet(team2MatchC.getNet());
				setMatchCTeam2PlayerHolePoints(matchResults.getPlayer2HolePoints());
				setMatchCTeam2PlayerNetPoints(matchResults.getPlayer2NetPoints());

				for (int i = 0; i < 9; i++) {
					Component component1 = getMatchContainer().get("matchCTeam1PlayerHole");
					if (matchResults.getPlayer1HolesWon().contains(i + 1)) {
						((LabelList) component1).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component1).add(i, AttributeModifier.replace("class", ""));
					}
					Component component2 = getMatchContainer().get("matchCTeam2PlayerHole");
					if (matchResults.getPlayer2HolesWon().contains(i + 1)) {
						((LabelList) component2).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component2).add(i, AttributeModifier.replace("class", ""));
					}
				}
			}
		}
	}

	private void resetMatchD() {
		setMatchDSide(null);
		setMatchDTeam1Player(null);
		getMatchDTeam1Holes().clear();
		setMatchDTeam1Handicap(null);
		setMatchDTeam2Handicap(null);
		setMatchDTeam2Player(null);
		getMatchDTeam2Holes().clear();

		matchDTeam1PlayerGross = 0;
		matchDTeam1PlayerNet = 0;
		matchDTeam1PlayerNetPoints = new Double(0);
		matchDTeam1PlayerHolePoints = new Double(0);
		matchDTeam2PlayerGross = 0;
		matchDTeam2PlayerNet = 0;
		matchDTeam2PlayerNetPoints = new Double(0);
		matchDTeam2PlayerHolePoints = new Double(0);

		if (team1 != null) {
			MatchResults matchResults = getSelectedTeamMatch().getMatchDResults();
			WeeklyScore team1MatchD = getSelectedTeamMatch().getTeam1().getMatchD();
			WeeklyScore team2MatchD = getSelectedTeamMatch().getTeam2().getMatchD();
			if (getSelectedTeamMatch() != null && team1MatchD != null) {
				setMatchDSide(team1MatchD.getSide());

				getMatchDHoleNumbers().clear();
				getMatchDHoleHandicaps().clear();
				getMatchDHoleNumbers()
						.addAll("FT".equals(getMatchDSide().getSideType()) ? HoleUtils.FRONT_HOLE_NUMBERS : HoleUtils.BACK_HOLE_NUMBERS);
				getMatchDHoleHandicaps().addAll(HoleUtils.getHandicaps(getMatchDSide()));

				// Player 1
				setMatchDTeam1Player(team1MatchD.isGhostScore() ? new GhostPlayer() : team1MatchD.getPlayer());
				setMatchDTeam1Handicap(team1MatchD.getHandicap());

				getMatchDTeam1Holes().add(team1MatchD.getHole1Score() != null ? team1MatchD.getHole1Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole2Score() != null ? team1MatchD.getHole2Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole3Score() != null ? team1MatchD.getHole3Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole4Score() != null ? team1MatchD.getHole4Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole5Score() != null ? team1MatchD.getHole5Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole6Score() != null ? team1MatchD.getHole6Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole7Score() != null ? team1MatchD.getHole7Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole8Score() != null ? team1MatchD.getHole8Score().toString() : "");
				getMatchDTeam1Holes().add(team1MatchD.getHole9Score() != null ? team1MatchD.getHole9Score().toString() : "");

				setMatchDTeam1PlayerGross(team1MatchD.getScore());
				setMatchDTeam1PlayerNet(team1MatchD.getNet());
				setMatchDTeam1PlayerHolePoints(matchResults.getPlayer1HolePoints());
				setMatchDTeam1PlayerNetPoints(matchResults.getPlayer1NetPoints());

				// Player 2
				setMatchDTeam2Player(team2MatchD.isGhostScore() ? new GhostPlayer() : team2MatchD.getPlayer());
				setMatchDTeam2Handicap(team2MatchD.getHandicap());

				getMatchDTeam2Holes().add(team2MatchD.getHole1Score() != null ? team2MatchD.getHole1Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole2Score() != null ? team2MatchD.getHole2Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole3Score() != null ? team2MatchD.getHole3Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole4Score() != null ? team2MatchD.getHole4Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole5Score() != null ? team2MatchD.getHole5Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole6Score() != null ? team2MatchD.getHole6Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole7Score() != null ? team2MatchD.getHole7Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole8Score() != null ? team2MatchD.getHole8Score().toString() : "");
				getMatchDTeam2Holes().add(team2MatchD.getHole9Score() != null ? team2MatchD.getHole9Score().toString() : "");

				setMatchDTeam2PlayerGross(team2MatchD.getScore());
				setMatchDTeam2PlayerNet(team2MatchD.getNet());
				setMatchDTeam2PlayerHolePoints(matchResults.getPlayer2HolePoints());
				setMatchDTeam2PlayerNetPoints(matchResults.getPlayer2NetPoints());

				for (int i = 0; i < 9; i++) {
					Component component1 = getMatchContainer().get("matchDTeam1PlayerHole");
					if (matchResults.getPlayer1HolesWon().contains(i + 1)) {
						((LabelList) component1).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component1).add(i, AttributeModifier.replace("class", ""));
					}
					Component component2 = getMatchContainer().get("matchDTeam2PlayerHole");
					if (matchResults.getPlayer2HolesWon().contains(i + 1)) {
						((LabelList) component2).add(i, AttributeModifier.replace("class", "holeWon"));
					} else {
						((LabelList) component2).add(i, AttributeModifier.replace("class", ""));
					}
				}
			}
		}
	}

	private void renderContent() {
		add(buildWeekDropDown());
		add(getTeamMatchDropDown());
		add(getMatchContainer());

		for (int i = 0; i < matches.length; i++) {
			getMatchContainer().add(
					new Label("match" + matches[i] + "Side", new PropertyModel<String>(this, "match" + matches[i] + "Side")).setOutputMarkupId(true));
			getMatchContainer().add(getLabelList("match" + matches[i] + "HoleLabel", "match" + matches[i] + "HoleNumbers", 9));
			getMatchContainer().add(getLabelList("match" + matches[i] + "HoleHdcp", "match" + matches[i] + "HoleHandicaps", 9));
			// Team 1
			getMatchContainer().add(new Label("match" + matches[i] + "Team1", new PropertyModel<String>(this, "team1")).setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team1Player", new PropertyModel<Player>(this, "match" + matches[i] + "Team1Player"))
							.setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team1PlayerHdcp", new PropertyModel<Integer>(this, "match" + matches[i] + "Team1Handicap"))
							.setOutputMarkupId(true).add(AttributeModifier.replace("style", "color: red; font-style: bold;")));
			getMatchContainer().add(getLabelList("match" + matches[i] + "Team1PlayerHole", "match" + matches[i] + "Team1Holes", 9));

			getMatchContainer().add(
					new Label("match" + matches[i] + "Team1PlayerGross", new PropertyModel<Integer>(this, "match" + matches[i] + "Team1PlayerGross"))
							.setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team1PlayerNet", new PropertyModel<Integer>(this, "match" + matches[i] + "Team1PlayerNet"))
							.setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team1PlayerNetPoints", new PropertyModel<Integer>(this, "match" + matches[i]
							+ "Team1PlayerNetPoints")).setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team1PlayerHolePoints", new PropertyModel<Integer>(this, "match" + matches[i]
							+ "Team1PlayerHolePoints")).setOutputMarkupId(true));

			// Team 2
			getMatchContainer().add(new Label("match" + matches[i] + "Team2", new PropertyModel<String>(this, "team2")).setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team2Player", new PropertyModel<Player>(this, "match" + matches[i] + "Team2Player"))
							.setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team2PlayerHdcp", new PropertyModel<Integer>(this, "match" + matches[i] + "Team2Handicap"))
							.setOutputMarkupId(true).add(AttributeModifier.replace("style", "color: red; font-style: bold;")));
			getMatchContainer().add(getLabelList("match" + matches[i] + "Team2PlayerHole", "match" + matches[i] + "Team2Holes", 9));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team2PlayerGross", new PropertyModel<Integer>(this, "match" + matches[i] + "Team2PlayerGross"))
							.setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team2PlayerNet", new PropertyModel<Integer>(this, "match" + matches[i] + "Team2PlayerNet"))
							.setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team2PlayerNetPoints", new PropertyModel<Integer>(this, "match" + matches[i]
							+ "Team2PlayerNetPoints")).setOutputMarkupId(true));
			getMatchContainer().add(
					new Label("match" + matches[i] + "Team2PlayerHolePoints", new PropertyModel<Integer>(this, "match" + matches[i]
							+ "Team2PlayerHolePoints")).setOutputMarkupId(true));
		}

		getMatchContainer().add(new Label("team1", new PropertyModel<String>(this, "team1")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("team1Net", new PropertyModel<String>(this, "team1Net")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("team1NetPoints", new PropertyModel<String>(this, "team1NetPoints")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("totalTeam1Points", new PropertyModel<String>(this, "totalTeam1Points")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("matchCourse", new PropertyModel<Course>(this, "matchCourse")).setOutputMarkupId(true));

		getMatchContainer().add(new Label("team2", new PropertyModel<String>(this, "team2")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("team2Net", new PropertyModel<String>(this, "team2Net")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("team2NetPoints", new PropertyModel<String>(this, "team2NetPoints")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("totalTeam2Points", new PropertyModel<String>(this, "totalTeam2Points")).setOutputMarkupId(true));
	}

	protected LabelList getLabelList(String id, String propertyName, int noOfComps) {
		LabelList labelList = new LabelList(id, new PropertyModel<List<String>>(this, propertyName), noOfComps);
		labelList.setOutputMarkupId(true);
		labelList.add(AttributeModifier.replace("style", "display: block; float: left; width: 45px"));
		return labelList;
	}

	private WebMarkupContainer getMatchContainer() {
		if (matchContainer == null) {
			matchContainer = new WebMarkupContainer("matchContainer");
			matchContainer.setOutputMarkupId(true);
			matchContainer.add(AttributeModifier.replace("class", "stj-field"));
		}
		return matchContainer;
	}

	private DropDownChoice<Week> buildWeekDropDown() {
		DropDownChoice<Week> component = new DropDownChoice<Week>("weekChoice", new PropertyModel<Week>(this, "selectedWeek"), weekChoices,
				getWeekRenderer());
		component.setOutputMarkupId(true);
		component.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				setSelectedTeamMatch(null);
				resetMatchups();
				target.add(getTeamMatchDropDown());
				target.add(getMatchContainer());
			}
		});
		return component;
	}

	private DropDownChoice<TeamMatch> getTeamMatchDropDown() {
		if (teamMatchDropDown == null) {
			teamMatchDropDown = new DropDownChoice<TeamMatch>("teamMatchChoice", new PropertyModel<TeamMatch>(this, "selectedTeamMatch"),
					matchChoices, getTeamMatchRenderer());
			teamMatchDropDown.setOutputMarkupId(true);
			teamMatchDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					resetMatches();

					target.add(getMatchContainer());
				}
			});
		}
		return teamMatchDropDown;
	}

	private IChoiceRenderer<Week> getWeekRenderer() {
		return new IChoiceRenderer<Week>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Week model) {
				return model.toString();
			}

			public String getIdValue(Week model, int index) {
				return model.getId().toString();
			}
		};
	}

	private IChoiceRenderer<TeamMatch> getTeamMatchRenderer() {
		return new IChoiceRenderer<TeamMatch>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(TeamMatch model) {
				return model.toString();
			}

			public String getIdValue(TeamMatch model, int index) {
				return model.getId().toString();
			}
		};
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	public List<Week> getWeekChoices() {
		return weekChoices;
	}

	public void setWeekChoices(List<Week> weekChoices) {
		this.weekChoices = weekChoices;
	}

	public List<TeamMatch> getMatchChoices() {
		return matchChoices;
	}

	public void setMatchChoices(List<TeamMatch> matchChoices) {
		this.matchChoices = matchChoices;
	}

	public Week getSelectedWeek() {
		return selectedWeek;
	}

	public void setSelectedWeek(Week selectedWeek) {
		this.selectedWeek = selectedWeek;
	}

	public TeamMatch getSelectedTeamMatch() {
		return selectedTeamMatch;
	}

	public void setSelectedTeamMatch(TeamMatch selectedTeamMatch) {
		this.selectedTeamMatch = selectedTeamMatch;
	}

	public Side getMatchASide() {
		return matchASide;
	}

	public void setMatchASide(Side matchASide) {
		this.matchASide = matchASide;
	}

	public Player getMatchATeam1Player() {
		return matchATeam1Player;
	}

	public void setMatchATeam1Player(Player matchATeam1Player) {
		this.matchATeam1Player = matchATeam1Player;
	}

	public Player getMatchATeam2Player() {
		return matchATeam2Player;
	}

	public void setMatchATeam2Player(Player matchATeam2Player) {
		this.matchATeam2Player = matchATeam2Player;
	}

	public Side getMatchBSide() {
		return matchBSide;
	}

	public void setMatchBSide(Side matchBSide) {
		this.matchBSide = matchBSide;
	}

	public Player getMatchBTeam1Player() {
		return matchBTeam1Player;
	}

	public void setMatchBTeam1Player(Player matchBTeam1Player) {
		this.matchBTeam1Player = matchBTeam1Player;
	}

	public Player getMatchBTeam2Player() {
		return matchBTeam2Player;
	}

	public void setMatchBTeam2Player(Player matchBTeam2Player) {
		this.matchBTeam2Player = matchBTeam2Player;
	}

	public Side getMatchCSide() {
		return matchCSide;
	}

	public void setMatchCSide(Side matchCSide) {
		this.matchCSide = matchCSide;
	}

	public Player getMatchCTeam1Player() {
		return matchCTeam1Player;
	}

	public void setMatchCTeam1Player(Player matchCTeam1Player) {
		this.matchCTeam1Player = matchCTeam1Player;
	}

	public Player getMatchCTeam2Player() {
		return matchCTeam2Player;
	}

	public void setMatchCTeam2Player(Player matchCTeam2Player) {
		this.matchCTeam2Player = matchCTeam2Player;
	}

	public Side getMatchDSide() {
		return matchDSide;
	}

	public void setMatchDSide(Side matchDSide) {
		this.matchDSide = matchDSide;
	}

	public Player getMatchDTeam1Player() {
		return matchDTeam1Player;
	}

	public void setMatchDTeam1Player(Player matchDTeam1Player) {
		this.matchDTeam1Player = matchDTeam1Player;
	}

	public Player getMatchDTeam2Player() {
		return matchDTeam2Player;
	}

	public void setMatchDTeam2Player(Player matchDTeam2Player) {
		this.matchDTeam2Player = matchDTeam2Player;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Integer getMatchATeam1PlayerGross() {
		return matchATeam1PlayerGross;
	}

	public void setMatchATeam1PlayerGross(Integer matchATeam1PlayerGross) {
		this.matchATeam1PlayerGross = matchATeam1PlayerGross;
	}

	public Integer getMatchATeam1PlayerNet() {
		return matchATeam1PlayerNet;
	}

	public void setMatchATeam1PlayerNet(Integer matchATeam1PlayerNet) {
		this.matchATeam1PlayerNet = matchATeam1PlayerNet;
	}

	public Double getMatchATeam1PlayerNetPoints() {
		return matchATeam1PlayerNetPoints;
	}

	public void setMatchATeam1PlayerNetPoints(Double matchATeam1PlayerNetPoints) {
		this.matchATeam1PlayerNetPoints = matchATeam1PlayerNetPoints;
	}

	public Double getMatchATeam1PlayerHolePoints() {
		return matchATeam1PlayerHolePoints;
	}

	public void setMatchATeam1PlayerHolePoints(Double matchATeam1PlayerHolePoints) {
		this.matchATeam1PlayerHolePoints = matchATeam1PlayerHolePoints;
	}

	public Integer getMatchATeam2PlayerGross() {
		return matchATeam2PlayerGross;
	}

	public void setMatchATeam2PlayerGross(Integer matchATeam2PlayerGross) {
		this.matchATeam2PlayerGross = matchATeam2PlayerGross;
	}

	public Integer getMatchATeam2PlayerNet() {
		return matchATeam2PlayerNet;
	}

	public void setMatchATeam2PlayerNet(Integer matchATeam2PlayerNet) {
		this.matchATeam2PlayerNet = matchATeam2PlayerNet;
	}

	public Double getMatchATeam2PlayerNetPoints() {
		return matchATeam2PlayerNetPoints;
	}

	public void setMatchATeam2PlayerNetPoints(Double matchATeam2PlayerNetPoints) {
		this.matchATeam2PlayerNetPoints = matchATeam2PlayerNetPoints;
	}

	public Double getMatchATeam2PlayerHolePoints() {
		return matchATeam2PlayerHolePoints;
	}

	public void setMatchATeam2PlayerHolePoints(Double matchATeam2PlayerHolePoints) {
		this.matchATeam2PlayerHolePoints = matchATeam2PlayerHolePoints;
	}

	public Integer getMatchBTeam1PlayerGross() {
		return matchBTeam1PlayerGross;
	}

	public void setMatchBTeam1PlayerGross(Integer matchBTeam1PlayerGross) {
		this.matchBTeam1PlayerGross = matchBTeam1PlayerGross;
	}

	public Integer getMatchBTeam1PlayerNet() {
		return matchBTeam1PlayerNet;
	}

	public void setMatchBTeam1PlayerNet(Integer matchBTeam1PlayerNet) {
		this.matchBTeam1PlayerNet = matchBTeam1PlayerNet;
	}

	public Double getMatchBTeam1PlayerNetPoints() {
		return matchBTeam1PlayerNetPoints;
	}

	public void setMatchBTeam1PlayerNetPoints(Double matchBTeam1PlayerNetPoints) {
		this.matchBTeam1PlayerNetPoints = matchBTeam1PlayerNetPoints;
	}

	public Double getMatchBTeam1PlayerHolePoints() {
		return matchBTeam1PlayerHolePoints;
	}

	public void setMatchBTeam1PlayerHolePoints(Double matchBTeam1PlayerHolePoints) {
		this.matchBTeam1PlayerHolePoints = matchBTeam1PlayerHolePoints;
	}

	public Integer getMatchBTeam2PlayerGross() {
		return matchBTeam2PlayerGross;
	}

	public void setMatchBTeam2PlayerGross(Integer matchBTeam2PlayerGross) {
		this.matchBTeam2PlayerGross = matchBTeam2PlayerGross;
	}

	public Integer getMatchBTeam2PlayerNet() {
		return matchBTeam2PlayerNet;
	}

	public void setMatchBTeam2PlayerNet(Integer matchBTeam2PlayerNet) {
		this.matchBTeam2PlayerNet = matchBTeam2PlayerNet;
	}

	public Double getMatchBTeam2PlayerNetPoints() {
		return matchBTeam2PlayerNetPoints;
	}

	public void setMatchBTeam2PlayerNetPoints(Double matchBTeam2PlayerNetPoints) {
		this.matchBTeam2PlayerNetPoints = matchBTeam2PlayerNetPoints;
	}

	public Double getMatchBTeam2PlayerHolePoints() {
		return matchBTeam2PlayerHolePoints;
	}

	public void setMatchBTeam2PlayerHolePoints(Double matchBTeam2PlayerHolePoints) {
		this.matchBTeam2PlayerHolePoints = matchBTeam2PlayerHolePoints;
	}

	public Integer getMatchCTeam1PlayerGross() {
		return matchCTeam1PlayerGross;
	}

	public void setMatchCTeam1PlayerGross(Integer matchCTeam1PlayerGross) {
		this.matchCTeam1PlayerGross = matchCTeam1PlayerGross;
	}

	public Integer getMatchCTeam1PlayerNet() {
		return matchCTeam1PlayerNet;
	}

	public void setMatchCTeam1PlayerNet(Integer matchCTeam1PlayerNet) {
		this.matchCTeam1PlayerNet = matchCTeam1PlayerNet;
	}

	public Double getMatchCTeam1PlayerNetPoints() {
		return matchCTeam1PlayerNetPoints;
	}

	public void setMatchCTeam1PlayerNetPoints(Double matchCTeam1PlayerNetPoints) {
		this.matchCTeam1PlayerNetPoints = matchCTeam1PlayerNetPoints;
	}

	public Double getMatchCTeam1PlayerHolePoints() {
		return matchCTeam1PlayerHolePoints;
	}

	public void setMatchCTeam1PlayerHolePoints(Double matchCTeam1PlayerHolePoints) {
		this.matchCTeam1PlayerHolePoints = matchCTeam1PlayerHolePoints;
	}

	public Integer getMatchCTeam2PlayerGross() {
		return matchCTeam2PlayerGross;
	}

	public void setMatchCTeam2PlayerGross(Integer matchCTeam2PlayerGross) {
		this.matchCTeam2PlayerGross = matchCTeam2PlayerGross;
	}

	public Integer getMatchCTeam2PlayerNet() {
		return matchCTeam2PlayerNet;
	}

	public void setMatchCTeam2PlayerNet(Integer matchCTeam2PlayerNet) {
		this.matchCTeam2PlayerNet = matchCTeam2PlayerNet;
	}

	public Double getMatchCTeam2PlayerNetPoints() {
		return matchCTeam2PlayerNetPoints;
	}

	public void setMatchCTeam2PlayerNetPoints(Double matchCTeam2PlayerNetPoints) {
		this.matchCTeam2PlayerNetPoints = matchCTeam2PlayerNetPoints;
	}

	public Double getMatchCTeam2PlayerHolePoints() {
		return matchCTeam2PlayerHolePoints;
	}

	public void setMatchCTeam2PlayerHolePoints(Double matchCTeam2PlayerHolePoints) {
		this.matchCTeam2PlayerHolePoints = matchCTeam2PlayerHolePoints;
	}

	public Integer getMatchDTeam1PlayerGross() {
		return matchDTeam1PlayerGross;
	}

	public void setMatchDTeam1PlayerGross(Integer matchDTeam1PlayerGross) {
		this.matchDTeam1PlayerGross = matchDTeam1PlayerGross;
	}

	public Integer getMatchDTeam1PlayerNet() {
		return matchDTeam1PlayerNet;
	}

	public void setMatchDTeam1PlayerNet(Integer matchDTeam1PlayerNet) {
		this.matchDTeam1PlayerNet = matchDTeam1PlayerNet;
	}

	public Double getMatchDTeam1PlayerNetPoints() {
		return matchDTeam1PlayerNetPoints;
	}

	public void setMatchDTeam1PlayerNetPoints(Double matchDTeam1PlayerNetPoints) {
		this.matchDTeam1PlayerNetPoints = matchDTeam1PlayerNetPoints;
	}

	public Double getMatchDTeam1PlayerHolePoints() {
		return matchDTeam1PlayerHolePoints;
	}

	public void setMatchDTeam1PlayerHolePoints(Double matchDTeam1PlayerHolePoints) {
		this.matchDTeam1PlayerHolePoints = matchDTeam1PlayerHolePoints;
	}

	public Integer getMatchDTeam2PlayerGross() {
		return matchDTeam2PlayerGross;
	}

	public void setMatchDTeam2PlayerGross(Integer matchDTeam2PlayerGross) {
		this.matchDTeam2PlayerGross = matchDTeam2PlayerGross;
	}

	public Integer getMatchDTeam2PlayerNet() {
		return matchDTeam2PlayerNet;
	}

	public void setMatchDTeam2PlayerNet(Integer matchDTeam2PlayerNet) {
		this.matchDTeam2PlayerNet = matchDTeam2PlayerNet;
	}

	public Double getMatchDTeam2PlayerNetPoints() {
		return matchDTeam2PlayerNetPoints;
	}

	public void setMatchDTeam2PlayerNetPoints(Double matchDTeam2PlayerNetPoints) {
		this.matchDTeam2PlayerNetPoints = matchDTeam2PlayerNetPoints;
	}

	public Double getMatchDTeam2PlayerHolePoints() {
		return matchDTeam2PlayerHolePoints;
	}

	public void setMatchDTeam2PlayerHolePoints(Double matchDTeam2PlayerHolePoints) {
		this.matchDTeam2PlayerHolePoints = matchDTeam2PlayerHolePoints;
	}

	public String[] getMatches() {
		return matches;
	}

	public void setMatches(String[] matches) {
		this.matches = matches;
	}

	public Integer getTeam1Net() {
		return team1Net;
	}

	public void setTeam1Net(Integer team1Net) {
		this.team1Net = team1Net;
	}

	public Double getTeam1NetPoints() {
		return team1NetPoints;
	}

	public void setTeam1NetPoints(Double team1NetPoints) {
		this.team1NetPoints = team1NetPoints;
	}

	public Integer getTeam2Net() {
		return team2Net;
	}

	public void setTeam2Net(Integer team2Net) {
		this.team2Net = team2Net;
	}

	public Double getTeam2NetPoints() {
		return team2NetPoints;
	}

	public void setTeam2NetPoints(Double team2NetPoints) {
		this.team2NetPoints = team2NetPoints;
	}

	public List<String> getMatchAHoleNumbers() {
		return matchAHoleNumbers;
	}

	public void setMatchAHoleNumbers(List<String> matchAHoleNumbers) {
		this.matchAHoleNumbers = matchAHoleNumbers;
	}

	public List<String> getMatchBHoleNumbers() {
		return matchBHoleNumbers;
	}

	public void setMatchBHoleNumbers(List<String> matchBHoleNumbers) {
		this.matchBHoleNumbers = matchBHoleNumbers;
	}

	public List<String> getMatchCHoleNumbers() {
		return matchCHoleNumbers;
	}

	public void setMatchCHoleNumbers(List<String> matchCHoleNumbers) {
		this.matchCHoleNumbers = matchCHoleNumbers;
	}

	public List<String> getMatchDHoleNumbers() {
		return matchDHoleNumbers;
	}

	public void setMatchDHoleNumbers(List<String> matchDHoleNumbers) {
		this.matchDHoleNumbers = matchDHoleNumbers;
	}

	public List<String> getMatchATeam1Holes() {
		return matchATeam1Holes;
	}

	public void setMatchATeam1Holes(List<String> matchATeam1Holes) {
		this.matchATeam1Holes = matchATeam1Holes;
	}

	public List<String> getMatchATeam2Holes() {
		return matchATeam2Holes;
	}

	public void setMatchATeam2Holes(List<String> matchATeam2Holes) {
		this.matchATeam2Holes = matchATeam2Holes;
	}

	public List<String> getMatchBTeam1Holes() {
		return matchBTeam1Holes;
	}

	public void setMatchBTeam1Holes(List<String> matchBTeam1Holes) {
		this.matchBTeam1Holes = matchBTeam1Holes;
	}

	public List<String> getMatchBTeam2Holes() {
		return matchBTeam2Holes;
	}

	public void setMatchBTeam2Holes(List<String> matchBTeam2Holes) {
		this.matchBTeam2Holes = matchBTeam2Holes;
	}

	public List<String> getMatchCTeam1Holes() {
		return matchCTeam1Holes;
	}

	public void setMatchCTeam1Holes(List<String> matchCTeam1Holes) {
		this.matchCTeam1Holes = matchCTeam1Holes;
	}

	public List<String> getMatchCTeam2Holes() {
		return matchCTeam2Holes;
	}

	public void setMatchCTeam2Holes(List<String> matchCTeam2Holes) {
		this.matchCTeam2Holes = matchCTeam2Holes;
	}

	public List<String> getMatchDTeam1Holes() {
		return matchDTeam1Holes;
	}

	public void setMatchDTeam1Holes(List<String> matchDTeam1Holes) {
		this.matchDTeam1Holes = matchDTeam1Holes;
	}

	public List<String> getMatchDTeam2Holes() {
		return matchDTeam2Holes;
	}

	public void setMatchDTeam2Holes(List<String> matchDTeam2Holes) {
		this.matchDTeam2Holes = matchDTeam2Holes;
	}

	public List<String> getMatchAHoleHandicaps() {
		return matchAHoleHandicaps;
	}

	public void setMatchAHoleHandicaps(List<String> matchAHoleHandicaps) {
		this.matchAHoleHandicaps = matchAHoleHandicaps;
	}

	public List<String> getMatchBHoleHandicaps() {
		return matchBHoleHandicaps;
	}

	public void setMatchBHoleHandicaps(List<String> matchBHoleHandicaps) {
		this.matchBHoleHandicaps = matchBHoleHandicaps;
	}

	public List<String> getMatchCHoleHandicaps() {
		return matchCHoleHandicaps;
	}

	public void setMatchCHoleHandicaps(List<String> matchCHoleHandicaps) {
		this.matchCHoleHandicaps = matchCHoleHandicaps;
	}

	public List<String> getMatchDHoleHandicaps() {
		return matchDHoleHandicaps;
	}

	public void setMatchDHoleHandicaps(List<String> matchDHoleHandicaps) {
		this.matchDHoleHandicaps = matchDHoleHandicaps;
	}

	public Double getTotalTeam1Points() {
		return totalTeam1Points;
	}

	public void setTotalTeam1Points(Double totalTeam1Points) {
		this.totalTeam1Points = totalTeam1Points;
	}

	public Double getTotalTeam2Points() {
		return totalTeam2Points;
	}

	public void setTotalTeam2Points(Double totalTeam2Points) {
		this.totalTeam2Points = totalTeam2Points;
	}

	public Integer getMatchATeam1Handicap() {
		return matchATeam1Handicap;
	}

	public void setMatchATeam1Handicap(Integer matchATeam1Handicap) {
		this.matchATeam1Handicap = matchATeam1Handicap;
	}

	public Integer getMatchATeam2Handicap() {
		return matchATeam2Handicap;
	}

	public void setMatchATeam2Handicap(Integer matchATeam2Handicap) {
		this.matchATeam2Handicap = matchATeam2Handicap;
	}

	public Integer getMatchBTeam1Handicap() {
		return matchBTeam1Handicap;
	}

	public void setMatchBTeam1Handicap(Integer matchBTeam1Handicap) {
		this.matchBTeam1Handicap = matchBTeam1Handicap;
	}

	public Integer getMatchBTeam2Handicap() {
		return matchBTeam2Handicap;
	}

	public void setMatchBTeam2Handicap(Integer matchBTeam2Handicap) {
		this.matchBTeam2Handicap = matchBTeam2Handicap;
	}

	public Integer getMatchCTeam1Handicap() {
		return matchCTeam1Handicap;
	}

	public void setMatchCTeam1Handicap(Integer matchCTeam1Handicap) {
		this.matchCTeam1Handicap = matchCTeam1Handicap;
	}

	public Integer getMatchCTeam2Handicap() {
		return matchCTeam2Handicap;
	}

	public void setMatchCTeam2Handicap(Integer matchCTeam2Handicap) {
		this.matchCTeam2Handicap = matchCTeam2Handicap;
	}

	public Integer getMatchDTeam1Handicap() {
		return matchDTeam1Handicap;
	}

	public void setMatchDTeam1Handicap(Integer matchDTeam1Handicap) {
		this.matchDTeam1Handicap = matchDTeam1Handicap;
	}

	public Integer getMatchDTeam2Handicap() {
		return matchDTeam2Handicap;
	}

	public void setMatchDTeam2Handicap(Integer matchDTeam2Handicap) {
		this.matchDTeam2Handicap = matchDTeam2Handicap;
	}

	public Course getMatchCourse() {
		return matchCourse;
	}

	public void setMatchCourse(Course matchCourse) {
		this.matchCourse = matchCourse;
	}

}
