package com.stj.web.ui.admin.scores;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.ReflectionUtils;

import com.stj.model.BackNine;
import com.stj.model.Course;
import com.stj.model.GhostPlayer;
import com.stj.model.Player;
import com.stj.model.Side;
import com.stj.model.Team;
import com.stj.model.TeamMatch;
import com.stj.model.TeamScore;
import com.stj.model.Week;
import com.stj.model.WeeklyScore;
import com.stj.services.LeagueService;
import com.stj.util.HandicapUtils;
import com.stj.util.HoleUtils;
import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.wicket.form.input.LabelList;
import com.stj.web.wicket.form.input.TextFieldList;

public class EditScoresPage extends AdminBasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	private List<Player> allPlayers = new ArrayList<Player>();
	private List<Side> sideChoices = new ArrayList<Side>();

	private Week selectedWeek;
	private TeamMatch selectedTeamMatch;
	private String matchPropertyName;
	WeeklyScore team1Match = new WeeklyScore();
	WeeklyScore team2Match = new WeeklyScore();

	private Team team1;
	private Team team2;

	private List<Player> team1Players = new ArrayList<Player>();
	private List<Player> team2Players = new ArrayList<Player>();

	private List<String> matchHoleNumbers = new ArrayList<String>(HoleUtils.FRONT_HOLE_NUMBERS);
	private List<String> matchHoleHandicaps = new ArrayList<String>(HoleUtils.getHandicaps(null));

	private Course matchCourse;
	private Side matchSide;
	private Player matchTeam1Player;
	private Player matchTeam2Player;

	private List<String> matchTeam1Holes = new ArrayList<String>();
	private Integer matchTeam1PlayerGross = 0;
	private Integer matchTeam1PlayerNet = 0;
	private Double matchTeam1PlayerNetPoints = new Double(0);
	private Double matchTeam1PlayerHolePoints = new Double(0);

	private List<String> matchTeam2Holes = new ArrayList<String>();
	private Integer matchTeam2PlayerGross = 0;
	private Integer matchTeam2PlayerNet = 0;
	private Double matchTeam2PlayerNetPoints = new Double(0);
	private Double matchTeam2PlayerHolePoints = new Double(0);

	WebMarkupContainer matchContainer;

	public EditScoresPage(PageParameters params) {
		try {
			Integer weekId = Integer.parseInt(params.get("selectedWeek").toString());
			Week weekParam = leagueService.getWeek(weekId);
			Integer matchId = Integer.parseInt(params.get("selectedTeamMatch").toString());
			TeamMatch matchParam = null;
			if (weekParam.getFrontNineTeeTime1().getId().equals(matchId)) {
				matchParam = weekParam.getFrontNineTeeTime1();
			} else if (weekParam.getFrontNineTeeTime2().getId().equals(matchId)) {
				matchParam = weekParam.getFrontNineTeeTime2();
			} else if (weekParam.getFrontNineTeeTime3().getId().equals(matchId)) {
				matchParam = weekParam.getFrontNineTeeTime3();
			} else if (weekParam.getBackNineTeeTime1().getId().equals(matchId)) {
				matchParam = weekParam.getBackNineTeeTime1();
			} else if (weekParam.getBackNineTeeTime2().getId().equals(matchId)) {
				matchParam = weekParam.getBackNineTeeTime2();
			} else if (weekParam.getBackNineTeeTime3().getId().equals(matchId)) {
				matchParam = weekParam.getBackNineTeeTime3();
			}
			String matchPropertyNameParam = params.get("matchPropertyName").toString();
			initPage(weekParam, matchParam, matchPropertyNameParam);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public EditScoresPage(Week week, TeamMatch match, String matchPropertyName) {
		initPage(week, match, matchPropertyName);
	}

	void initPage(Week week, TeamMatch match, String matchPropertyName) {
		if (match.getCourse() == null) {
			match.setCourse(leagueService.getTheKnolls2019());
		}
		this.selectedTeamMatch = match;
		this.selectedWeek = week;
		this.matchPropertyName = matchPropertyName;
		allPlayers = leagueService.getAllPlayers();
		allPlayers.add(new GhostPlayer());
		matchCourse = selectedTeamMatch.getCourse();
		sideChoices.add(matchCourse.getFrontNine());
		sideChoices.add(matchCourse.getBackNine());

		this.matchPropertyName = matchPropertyName.substring(0, 1).toUpperCase() + matchPropertyName.substring(1);
		team1 = selectedTeamMatch.getTeam1().getTeam();
		team2 = selectedTeamMatch.getTeam2().getTeam();

		// load players
		List<Player> players = new ArrayList<Player>(allPlayers);
		team1Players.clear();
		team2Players.clear();
		if (team1 != null) {
			team1Players.addAll(team1.getSortedPlayers());
			players.removeAll(team1Players);
			Collections.sort(players);
			team1Players.addAll(players);
		}
		if (team2 != null) {
			team2Players.addAll(team2.getSortedPlayers());
			players = new ArrayList<Player>(allPlayers);
			players.remove(team2Players);
			Collections.sort(players);
			team2Players.addAll(players);
		}

		populateFromModel();

		renderContent();
		if (matchSide != null) {
			calculateResults();
		}
	}

	private void populateFromModel() {
		Method getMethod = ReflectionUtils.findMethod(TeamScore.class, "get" + this.matchPropertyName);
		try {
			team1Match = (WeeklyScore) ReflectionUtils.invokeMethod(getMethod, this.selectedTeamMatch.getTeam1());
			if (team1Match != null) {
				matchTeam1Player = team1Match.isGhostScore() ? new GhostPlayer() : team1Match.getPlayer();
				matchSide = team1Match.getSide();
				matchTeam1Holes.add(team1Match.getHole1Score() != null ? team1Match.getHole1Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole2Score() != null ? team1Match.getHole2Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole3Score() != null ? team1Match.getHole3Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole4Score() != null ? team1Match.getHole4Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole5Score() != null ? team1Match.getHole5Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole6Score() != null ? team1Match.getHole6Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole7Score() != null ? team1Match.getHole7Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole8Score() != null ? team1Match.getHole8Score().toString() : "");
				matchTeam1Holes.add(team1Match.getHole9Score() != null ? team1Match.getHole9Score().toString() : "");
			}
			team2Match = (WeeklyScore) ReflectionUtils.invokeMethod(getMethod, this.selectedTeamMatch.getTeam2());
			if (team2Match != null) {
				matchTeam2Player = team2Match.isGhostScore() ? new GhostPlayer() : team2Match.getPlayer();
				matchTeam2Holes.add(team2Match.getHole1Score() != null ? team2Match.getHole1Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole2Score() != null ? team2Match.getHole2Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole3Score() != null ? team2Match.getHole3Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole4Score() != null ? team2Match.getHole4Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole5Score() != null ? team2Match.getHole5Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole6Score() != null ? team2Match.getHole6Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole7Score() != null ? team2Match.getHole7Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole8Score() != null ? team2Match.getHole8Score().toString() : "");
				matchTeam2Holes.add(team2Match.getHole9Score() != null ? team2Match.getHole9Score().toString() : "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			getSession().error(matchPropertyName + " property not found.");
			e.printStackTrace();
		}
		if (team1Match == null) {
			team1Match = new WeeklyScore();
		}
		if (team2Match == null) {
			team2Match = new WeeklyScore();
		}
	}

	private void resetHandicaps() {
		team1Match.setHandicap(null);
		team2Match.setHandicap(null);
		if (matchTeam1Player != null) {
			team1Match.setHandicap(matchTeam1Player.getCurrentHandicap(selectedWeek.getDate()));
		}
		if (matchTeam2Player != null) {
			team2Match.setHandicap(matchTeam2Player.getCurrentHandicap(selectedWeek.getDate()));
		}
	}

	private void renderContent() {
		Form<Object> form = new Form<Object>("form");
		form.add(new Label("selectedWeek", this.selectedWeek.toString()));
		form.add(new Label("teamMatch", this.selectedTeamMatch.toString()));
		form.add(getMatchContainer());

		getMatchContainer().add(new Label("matchLabel", matchPropertyName));

		getMatchContainer().add(new Label("matchCourse", new PropertyModel<Course>(this, "matchCourse")).setOutputMarkupId(true));

		getMatchContainer().add(buildSideDropDown("matchSide", "matchSide"));
		getMatchContainer().add(getLabelList("matchHoleLabel", "matchHoleNumbers", 9));
		getMatchContainer().add(getLabelList("matchHoleHdcp", "matchHoleHandicaps", 9));
		// Team 1
		getMatchContainer().add(new Label("matchTeam1", new PropertyModel<String>(this, "team1")).setOutputMarkupId(true));
		getMatchContainer().add(buildPlayerDropDown("matchTeam1Player", "matchTeam1Player", team1Players));
		getMatchContainer().add(
				new Label("matchTeam1PlayerHdcp", new PropertyModel<Integer>(this, "team1Match.handicap")).setOutputMarkupId(true).add(
						AttributeModifier.replace("class", "handicap")));

		getMatchContainer().add(getTextFieldList("matchTeam1PlayerHole", "matchTeam1Holes", 9, "2"));

		getMatchContainer()
				.add(new Label("matchTeam1PlayerGross", new PropertyModel<Integer>(this, "matchTeam1PlayerGross")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("matchTeam1PlayerNet", new PropertyModel<Integer>(this, "matchTeam1PlayerNet")).setOutputMarkupId(true));
		getMatchContainer().add(
				new Label("matchTeam1PlayerNetPoints", new PropertyModel<Integer>(this, "matchTeam1PlayerNetPoints")).setOutputMarkupId(true));
		getMatchContainer().add(
				new Label("matchTeam1PlayerHolePoints", new PropertyModel<Integer>(this, "matchTeam1PlayerHolePoints")).setOutputMarkupId(true));

		// Team 2
		getMatchContainer().add(new Label("matchTeam2", new PropertyModel<String>(this, "team2")).setOutputMarkupId(true));
		getMatchContainer().add(buildPlayerDropDown("matchTeam2Player", "matchTeam2Player", team2Players));
		getMatchContainer().add(
				new Label("matchTeam2PlayerHdcp", new PropertyModel<Integer>(this, "team2Match.handicap")).setOutputMarkupId(true).add(
						AttributeModifier.replace("class", "handicap")));
		getMatchContainer().add(getTextFieldList("matchTeam2PlayerHole", "matchTeam2Holes", 9, "2"));
		getMatchContainer()
				.add(new Label("matchTeam2PlayerGross", new PropertyModel<Integer>(this, "matchTeam2PlayerGross")).setOutputMarkupId(true));
		getMatchContainer().add(new Label("matchTeam2PlayerNet", new PropertyModel<Integer>(this, "matchTeam2PlayerNet")).setOutputMarkupId(true));
		getMatchContainer().add(
				new Label("matchTeam2PlayerNetPoints", new PropertyModel<Integer>(this, "matchTeam2PlayerNetPoints")).setOutputMarkupId(true));
		getMatchContainer().add(
				new Label("matchTeam2PlayerHolePoints", new PropertyModel<Integer>(this, "matchTeam2PlayerHolePoints")).setOutputMarkupId(true));

		getMatchContainer().add(getSaveButton());
		getMatchContainer().add(getCancelButton());
		getMatchContainer().add(getCalculateButton());
		add(form);
	}

	protected LabelList getLabelList(String id, String propertyName, int noOfComps) {
		LabelList labelList = new LabelList(id, new PropertyModel<List<String>>(this, propertyName), noOfComps);
		labelList.add(AttributeModifier.replace("style", "display: block; float: left; width: 40px"));
		return labelList;
	}

	protected TextFieldList<String> getTextFieldList(String componentId, String propertyName, int noOfComps, String size) {
		TextFieldList<String> textFieldList = new TextFieldList<String>(componentId, new PropertyModel<List<String>>(this, propertyName), noOfComps,
				String.class, true);
		textFieldList.add(AttributeModifier.replace("class", "holeInput"));
		textFieldList.add(AttributeModifier.replace("maxlength", size));
		textFieldList.setOutputMarkupId(true);
		return textFieldList;
	}

	private WebMarkupContainer getMatchContainer() {
		if (matchContainer == null) {
			matchContainer = new WebMarkupContainer("matchContainer");
			matchContainer.setOutputMarkupId(true);
			matchContainer.add(AttributeModifier.replace("class", "stj-field"));
		}
		return matchContainer;
	}

	private DropDownChoice<Player> buildPlayerDropDown(String id, String property, List<Player> choices) {
		final DropDownChoice<Player> dropDown = new DropDownChoice<Player>(id, new PropertyModel<Player>(this, property), choices,
				getPlayerRenderer());
		dropDown.setOutputMarkupId(true);
		dropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				resetHandicaps();
				target.add(getMatchContainer());
			}
		});
		return dropDown;
	}

	private DropDownChoice<Side> buildSideDropDown(String id, String property) {
		DropDownChoice<Side> sideDropDown = new DropDownChoice<Side>(id, new PropertyModel<Side>(this, property), sideChoices, getSideRenderer());
		sideDropDown.setOutputMarkupId(true);
		sideDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (getMatchSide() != null) {
					getMatchHoleNumbers().clear();
					getMatchHoleHandicaps().clear();
					getMatchHoleNumbers().addAll(
							"FT".equals(getMatchSide().getSideType()) ? HoleUtils.FRONT_HOLE_NUMBERS : HoleUtils.BACK_HOLE_NUMBERS);
					getMatchHoleHandicaps().addAll(HoleUtils.getHandicaps(getMatchSide()));
				}

				target.add(getMatchContainer());
			}
		});
		return sideDropDown;
	}

	private Component getSaveButton() {
		return new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				showFeedback(target);
				if (saveMatchup(target)) {
					setResponsePage(new ScoresPage(selectedWeek, selectedTeamMatch));
				}
			}
		}.setMarkupId("saveButton");
	}

	private Component getCancelButton() {
		AjaxButton cancelButton = new AjaxButton("cancelButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				setResponsePage(new ScoresPage(selectedWeek, selectedTeamMatch));

			}
		};
		cancelButton.setDefaultFormProcessing(false);
		return cancelButton;
	}

	private Component getCalculateButton() {
		AjaxButton calculateButton = new AjaxButton("calculateButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				showFeedback(target);
				if (validateMatchup()) {
					calculateResults();
				}
				target.add(getMatchContainer());
			}
		};
		return calculateButton;
	}

	private IChoiceRenderer<Player> getPlayerRenderer() {
		return new IChoiceRenderer<Player>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Player model) {
				return model.toString();
			}

			public String getIdValue(Player model, int index) {
				return model.getId().toString();
			}
		};
	}

	private IChoiceRenderer<Side> getSideRenderer() {
		return new IChoiceRenderer<Side>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Side model) {
				return model.toString();
			}

			public String getIdValue(Side model, int index) {
				return model.getId().toString();
			}
		};
	}

	@SuppressWarnings("unchecked")
	private void calculateResults() {
		int[] player1Scores = new int[9];
		int[] player2Scores = new int[9];
		Integer player1Handicap = 0;
		Integer player2Handicap = 0;

		int gross = 0;
		int idx = 0;
		for (String holeScore : matchTeam1Holes) {
			try {
				Integer score = new Integer(holeScore);
				gross += score;
				player1Scores[idx] = score;
				idx++;
			} catch (NumberFormatException e) {
			}
		}
		setMatchTeam1PlayerGross(gross);

		gross = 0;
		idx = 0;
		for (String holeScore : matchTeam2Holes) {
			try {
				Integer score = new Integer(holeScore);
				gross += score;
				player2Scores[idx] = score;
				idx++;
			} catch (NumberFormatException e) {
			}
		}
		setMatchTeam2PlayerGross(gross);
		player1Handicap = getMatchTeam1Player().getCurrentHandicap(getSelectedWeek().getDate());
		if (player1Handicap == null && !(getMatchTeam1Player() instanceof GhostPlayer)) { // Use
																							// current
																							// week's
																							// score
			player1Handicap = HandicapUtils.calculateHandicap(getMatchTeam1PlayerGross(), getMatchSide().getPar(), 1);
		}
		this.team1Match.setHandicap(player1Handicap);
		player2Handicap = getMatchTeam2Player().getCurrentHandicap(getSelectedWeek().getDate());
		if (player2Handicap == null && !(getMatchTeam2Player() instanceof GhostPlayer)) { // Use
																							// current
																							// week's
																							// score
			player2Handicap = HandicapUtils.calculateHandicap(getMatchTeam2PlayerGross(), getMatchSide().getPar(), 1);
		}
		this.team2Match.setHandicap(player2Handicap);

		Map<Integer, Integer> holeStrokeMap = new HashMap<Integer, Integer>();
		boolean addStrokesToPlayer1 = HandicapUtils.calculateHoleStrokes(holeStrokeMap, getMatchSide(), player1Handicap, player2Handicap);
		boolean addStrokesToPlayer2 = !addStrokesToPlayer1;

		int player1HolesWon = 0;
		int player2HolesWon = 0;
		boolean isPlayer1Ghost = matchTeam1Player != null ? matchTeam1Player instanceof GhostPlayer : false;
		boolean isPlayer2Ghost = matchTeam2Player != null ? matchTeam2Player instanceof GhostPlayer : false;
		for (int i = 0; i < 9; i++) {
			idx = i + 1;
			if (matchSide instanceof BackNine) {
				idx = idx + 9;
			}
			int player1Strokes = player1Scores[i] + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap.get(idx) : 0);
			int player2Strokes = player2Scores[i] + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap.get(idx) : 0);
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				player1HolesWon++;
				Component component1 = getMatchContainer().get("matchTeam1PlayerHole");
				((TextFieldList<String>) component1).add(i, AttributeModifier.replace("style", "background-color: yellow;"));
				Component component2 = getMatchContainer().get("matchTeam2PlayerHole");
				((TextFieldList<String>) component2).add(i, AttributeModifier.replace("style", "background-color: white;"));
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				player2HolesWon++;
				Component component2 = getMatchContainer().get("matchTeam2PlayerHole");
				((TextFieldList<String>) component2).add(i, AttributeModifier.replace("style", "background-color: yellow;"));
				Component component1 = getMatchContainer().get("matchTeam1PlayerHole");
				((TextFieldList<String>) component1).add(i, AttributeModifier.replace("style", "background-color: white;"));
			} else {
				Component component1 = getMatchContainer().get("matchTeam1PlayerHole");
				((TextFieldList<String>) component1).add(i, AttributeModifier.replace("style", "background-color: white;"));
				Component component2 = getMatchContainer().get("matchTeam2PlayerHole");
				((TextFieldList<String>) component2).add(i, AttributeModifier.replace("style", "background-color: white;"));
			}
		}

		setMatchTeam1PlayerNet(getMatchTeam1PlayerGross() - (player1Handicap != null ? player1Handicap : 0));
		setMatchTeam2PlayerNet(getMatchTeam2PlayerGross() - (player2Handicap != null ? player2Handicap : 0));

		if ((getMatchTeam1PlayerNet() < getMatchTeam2PlayerNet() || isPlayer2Ghost) && !isPlayer1Ghost) {
			setMatchTeam1PlayerNetPoints(new Double(1));
			setMatchTeam2PlayerNetPoints(new Double(0));
		} else if ((getMatchTeam1PlayerNet() > getMatchTeam2PlayerNet() || isPlayer1Ghost) && !isPlayer2Ghost) {
			setMatchTeam1PlayerNetPoints(new Double(0));
			setMatchTeam2PlayerNetPoints(new Double(1));
		} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
			setMatchTeam1PlayerNetPoints(new Double(.5));
			setMatchTeam2PlayerNetPoints(new Double(.5));
		} else {
			setMatchTeam1PlayerNetPoints(new Double(0));
			setMatchTeam2PlayerNetPoints(new Double(0));
		}

		if (player1HolesWon > player2HolesWon) {
			setMatchTeam1PlayerHolePoints(new Double(1));
			setMatchTeam2PlayerHolePoints(new Double(0));
		} else if (player1HolesWon < player2HolesWon) {
			setMatchTeam1PlayerHolePoints(new Double(0));
			setMatchTeam2PlayerHolePoints(new Double(1));
		} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
			setMatchTeam1PlayerHolePoints(new Double(.5));
			setMatchTeam2PlayerHolePoints(new Double(.5));
		} else {
			setMatchTeam1PlayerHolePoints(new Double(0));
			setMatchTeam2PlayerHolePoints(new Double(0));
		}
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	public Week getSelectedWeek() {
		return selectedWeek;
	}

	public TeamMatch getSelectedTeamMatch() {
		return selectedTeamMatch;
	}

	public Side getMatchSide() {
		return matchSide;
	}

	public void setMatchSide(Side matchSide) {
		this.matchSide = matchSide;
	}

	public Player getMatchTeam1Player() {
		return matchTeam1Player;
	}

	public void setMatchTeam1Player(Player matchTeam1Player) {
		this.matchTeam1Player = matchTeam1Player;
	}

	public Player getMatchTeam2Player() {
		return matchTeam2Player;
	}

	public void setMatchTeam2Player(Player matchTeam2Player) {
		this.matchTeam2Player = matchTeam2Player;
	}

	public List<Side> getSideChoices() {
		return sideChoices;
	}

	public void setSideChoices(List<Side> sideChoices) {
		this.sideChoices = sideChoices;
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

	public List<Player> getTeam1Players() {
		return team1Players;
	}

	public void setTeam1Players(List<Player> team1Players) {
		this.team1Players = team1Players;
	}

	public List<Player> getTeam2Players() {
		return team2Players;
	}

	public void setTeam2Players(List<Player> team2Players) {
		this.team2Players = team2Players;
	}

	public Integer getMatchTeam1PlayerGross() {
		return matchTeam1PlayerGross;
	}

	public void setMatchTeam1PlayerGross(Integer matchTeam1PlayerGross) {
		this.matchTeam1PlayerGross = matchTeam1PlayerGross;
	}

	public Integer getMatchTeam1PlayerNet() {
		return matchTeam1PlayerNet;
	}

	public void setMatchTeam1PlayerNet(Integer matchTeam1PlayerNet) {
		this.matchTeam1PlayerNet = matchTeam1PlayerNet;
	}

	public Double getMatchTeam1PlayerNetPoints() {
		return matchTeam1PlayerNetPoints;
	}

	public void setMatchTeam1PlayerNetPoints(Double matchTeam1PlayerNetPoints) {
		this.matchTeam1PlayerNetPoints = matchTeam1PlayerNetPoints;
	}

	public Double getMatchTeam1PlayerHolePoints() {
		return matchTeam1PlayerHolePoints;
	}

	public void setMatchTeam1PlayerHolePoints(Double matchTeam1PlayerHolePoints) {
		this.matchTeam1PlayerHolePoints = matchTeam1PlayerHolePoints;
	}

	public Integer getMatchTeam2PlayerGross() {
		return matchTeam2PlayerGross;
	}

	public void setMatchTeam2PlayerGross(Integer matchTeam2PlayerGross) {
		this.matchTeam2PlayerGross = matchTeam2PlayerGross;
	}

	public Integer getMatchTeam2PlayerNet() {
		return matchTeam2PlayerNet;
	}

	public void setMatchTeam2PlayerNet(Integer matchTeam2PlayerNet) {
		this.matchTeam2PlayerNet = matchTeam2PlayerNet;
	}

	public Double getMatchTeam2PlayerNetPoints() {
		return matchTeam2PlayerNetPoints;
	}

	public void setMatchTeam2PlayerNetPoints(Double matchTeam2PlayerNetPoints) {
		this.matchTeam2PlayerNetPoints = matchTeam2PlayerNetPoints;
	}

	public Double getMatchTeam2PlayerHolePoints() {
		return matchTeam2PlayerHolePoints;
	}

	public void setMatchTeam2PlayerHolePoints(Double matchTeam2PlayerHolePoints) {
		this.matchTeam2PlayerHolePoints = matchTeam2PlayerHolePoints;
	}

	public List<String> getMatchHoleNumbers() {
		return matchHoleNumbers;
	}

	public void setMatchHoleNumbers(List<String> matchHoleNumbers) {
		this.matchHoleNumbers = matchHoleNumbers;
	}

	public List<String> getMatchTeam1Holes() {
		return matchTeam1Holes;
	}

	public void setMatchTeam1Holes(List<String> matchTeam1Holes) {
		this.matchTeam1Holes = matchTeam1Holes;
	}

	public List<String> getMatchTeam2Holes() {
		return matchTeam2Holes;
	}

	public void setMatchTeam2Holes(List<String> matchTeam2Holes) {
		this.matchTeam2Holes = matchTeam2Holes;
	}

	public List<String> getMatchHoleHandicaps() {
		return matchHoleHandicaps;
	}

	public void setMatchHoleHandicaps(List<String> matchHoleHandicaps) {
		this.matchHoleHandicaps = matchHoleHandicaps;
	}

	private boolean saveMatchup(AjaxRequestTarget target) {
		if (!validateMatchup()) {
			return false;
		}

		calculateResults();

		Method setMethod = ReflectionUtils.findMethod(TeamScore.class, "set" + this.matchPropertyName, null);

		// Team 1 Match
		if (team1Match == null) {
			team1Match = new WeeklyScore();
		}
		team1Match.setGhostScore(matchTeam1Player instanceof GhostPlayer);
		team1Match.setScoreDate(getSelectedWeek().getDate());
		team1Match.setSide(getMatchSide());
		team1Match.setTeamScore(getSelectedTeamMatch().getTeam1()); // set
																	// parent
		if (!team1Match.isGhostScore()) {
			team1Match.setPlayer(getMatchTeam1Player());
			team1Match.setHole1Score(new Integer(getMatchTeam1Holes().get(0)));
			team1Match.setHole2Score(new Integer(getMatchTeam1Holes().get(1)));
			team1Match.setHole3Score(new Integer(getMatchTeam1Holes().get(2)));
			team1Match.setHole4Score(new Integer(getMatchTeam1Holes().get(3)));
			team1Match.setHole5Score(new Integer(getMatchTeam1Holes().get(4)));
			team1Match.setHole6Score(new Integer(getMatchTeam1Holes().get(5)));
			team1Match.setHole7Score(new Integer(getMatchTeam1Holes().get(6)));
			team1Match.setHole8Score(new Integer(getMatchTeam1Holes().get(7)));
			team1Match.setHole9Score(new Integer(getMatchTeam1Holes().get(8)));
		}
		ReflectionUtils.invokeMethod(setMethod, getSelectedTeamMatch().getTeam1(), team1Match);

		// Team 2 Match
		if (team2Match == null) {
			team2Match = new WeeklyScore();
		}
		team2Match.setGhostScore(matchTeam2Player instanceof GhostPlayer);
		team2Match.setScoreDate(getSelectedWeek().getDate());
		team2Match.setSide(getMatchSide());
		team2Match.setTeamScore(getSelectedTeamMatch().getTeam2()); // set
																	// parent
		if (!team2Match.isGhostScore()) {
			team2Match.setPlayer(getMatchTeam2Player());
			team2Match.setHole1Score(new Integer(getMatchTeam2Holes().get(0)));
			team2Match.setHole2Score(new Integer(getMatchTeam2Holes().get(1)));
			team2Match.setHole3Score(new Integer(getMatchTeam2Holes().get(2)));
			team2Match.setHole4Score(new Integer(getMatchTeam2Holes().get(3)));
			team2Match.setHole5Score(new Integer(getMatchTeam2Holes().get(4)));
			team2Match.setHole6Score(new Integer(getMatchTeam2Holes().get(5)));
			team2Match.setHole7Score(new Integer(getMatchTeam2Holes().get(6)));
			team2Match.setHole8Score(new Integer(getMatchTeam2Holes().get(7)));
			team2Match.setHole9Score(new Integer(getMatchTeam2Holes().get(8)));
		}
		ReflectionUtils.invokeMethod(setMethod, getSelectedTeamMatch().getTeam2(), team2Match);

		leagueService.saveWeek(selectedWeek);

		info("Matchup Saved Successfully.");

		return true;
	}

	private boolean validateMatchup() {
		boolean hasErrors = false;
		// Match A
		if (getMatchCourse() == null) {
			error("Please select course for Match.");
			hasErrors = true;
		}
		if (getMatchSide() == null) {
			error("Please select side for Match.");
			hasErrors = true;
		}
		if (getMatchTeam1Player() == null) {
			error("Please select player for Team 1.");
			hasErrors = true;
		}
		if ((getMatchTeam1Player() == null || !(getMatchTeam1Player() instanceof GhostPlayer)) && getMatchTeam1Holes().size() < 9) {
			error("Please enter a score for all holes for Team 1.");
			hasErrors = true;
		}
		if (getMatchTeam2Player() == null) {
			error("Please select player for Team 2.");
			hasErrors = true;
		}
		if ((getMatchTeam2Player() == null || !(getMatchTeam2Player() instanceof GhostPlayer)) && getMatchTeam2Holes().size() < 9) {
			error("Please enter a score for all holes for Team 2.");
			hasErrors = true;
		}

		return !hasErrors;
	}

	public Course getMatchCourse() {
		return matchCourse;
	}

	public void setMatchCourse(Course matchCourse) {
		this.matchCourse = matchCourse;
	}

}
