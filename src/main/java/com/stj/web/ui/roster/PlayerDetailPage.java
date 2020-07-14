package com.stj.web.ui.roster;

import com.stj.model.Player;
import com.stj.model.WeeklyScore;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.base.BasePage;
import com.stj.web.wicket.component.JFreeChartImage;
import com.stj.web.wicket.renderer.PlayerRenderer;
import com.stj.web.wicket.renderer.YearRenderer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class PlayerDetailPage extends BasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	final static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	final static DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("MM/dd");
	final static NumberFormat averageFormat = new DecimalFormat("#0.0");
	final static NumberFormat scoreFormat = new DecimalFormat("#0");

	double eagles = 0;
	double birdies = 0;
	double pars = 0;
	double bogeys = 0;
	double doubleBogeys = 0;
	double others = 0;
	double totalGross = 0;
	double totalNet = 0;
	double totalEagles = 0;
	double totalBirdies = 0;
	double totalPars = 0;
	double totalBogeys = 0;
	double totalDoubleBogeys = 0;
	double totalOthers = 0;
	double totalPar3 = 0;
	double par3Count = 0;
	double totalPar4 = 0;
	double par4Count = 0;
	double totalPar5 = 0;
	double par5Count = 0;

	List<Integer> yearChoices = new ArrayList<Integer>();
	List<Player> playerChoices = new ArrayList<Player>();

	Integer selectedYear;
	Player selectedPlayer;

	public PlayerDetailPage() {
		renderContent();
	}
	
	public PlayerDetailPage(PageParameters params) {
		this.selectedPlayer = leagueService.getPlayer(Integer.parseInt(params.get("playerId").toString()));
		if (params.getNamedKeys().contains("year")) {
			this.selectedYear = Integer.parseInt(params.get("year").toString());
		}
		renderContent();
	}

	private void renderContent() {
		playerChoices = leagueService.getAllPlayers();
		Collections.sort(playerChoices);
		if (selectedPlayer == null) {
			selectedPlayer = playerChoices.get(0);
		}
		yearChoices = new ArrayList<Integer>(getScoreYears());
		Collections.sort(yearChoices);
		if (selectedYear == null) {
			if (yearChoices.isEmpty()) {
				selectedYear = Constants.CURRENT_YEAR;
			} else {
				selectedYear = yearChoices.get(yearChoices.size() - 1);
			}
		}

		
		add(buildPlayerDropDown());
		add(buildYearDropDown());

		RepeatingView repeater = new RepeatingView("scoreRepeater");
		add(repeater);

		List<WeeklyScore> scores = getSelectedYearScores();

		for (WeeklyScore score : scores) {
			WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
			container.setOutputMarkupId(true);
			repeater.add(container);

			accumulateScores(score);

			container.add(new Label("scoreDate", DATE_FORMAT.format(score.getScoreDate())));
			container.add(new Label("course", score.getSide().getCourse().getName()));
			container.add(new Label("side", score.getSide().getSideType()));
			container.add(new Label("score1", scoreFormat.format(score.getHole1Score())));
			container.add(new Label("score2", scoreFormat.format(score.getHole2Score())));
			container.add(new Label("score3", scoreFormat.format(score.getHole3Score())));
			container.add(new Label("score4", scoreFormat.format(score.getHole4Score())));
			container.add(new Label("score5", scoreFormat.format(score.getHole5Score())));
			container.add(new Label("score6", scoreFormat.format(score.getHole6Score())));
			container.add(new Label("score7", scoreFormat.format(score.getHole7Score())));
			container.add(new Label("score8", scoreFormat.format(score.getHole8Score())));
			container.add(new Label("score9", scoreFormat.format(score.getHole9Score())));
			container.add(new Label("gross", score.getScore().toString()).add(AttributeModifier.replace("class", "bold")));
			container.add(new Label("sidePar", score.getSide().getPar().toString()));
			container.add(new Label("handicap", score.getHandicap().toString()));
			container.add(new Label("net", score.getNet().toString()).add(AttributeModifier.replace("class", "bold")));
			container.add(new Label("eagles", scoreFormat.format(eagles)));
			container.add(new Label("birdies", scoreFormat.format(birdies)));
			container.add(new Label("pars", scoreFormat.format(pars)));
			container.add(new Label("bogeys", scoreFormat.format(bogeys)));
			container.add(new Label("doubleBogeys", scoreFormat.format(doubleBogeys)));
			container.add(new Label("others", scoreFormat.format(others)));
		}
		add(new Label("avgGross", averageFormat.format(totalGross / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace(
				"class", "bold")));
		add(new Label("avgNet", averageFormat.format(totalNet / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace("class",
				"bold")));
		add(new Label("totalEagles", averageFormat.format(totalEagles / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace(
				"class", "bold")));
		add(new Label("totalBirdies", averageFormat.format(totalBirdies / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace(
				"class", "bold")));
		add(new Label("totalPars", averageFormat.format(totalPars / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace(
				"class", "bold")));
		add(new Label("totalBogeys", averageFormat.format(totalBogeys / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace(
				"class", "bold")));
		add(new Label("totalDoubleBogeys", averageFormat.format(totalDoubleBogeys / (scores.size() > 0 ? scores.size() : 1)))
				.add(AttributeModifier.replace("class", "bold")));
		add(new Label("totalOthers", averageFormat.format(totalOthers / (scores.size() > 0 ? scores.size() : 1))).add(AttributeModifier.replace(
				"class", "bold")));

		add(new Label("par3Average", averageFormat.format(totalPar3 / par3Count)).add(AttributeModifier.replace("class", "bold")));
		add(new Label("par4Average", averageFormat.format(totalPar4 / par4Count)).add(AttributeModifier.replace("class", "bold")));
		add(new Label("par5Average", averageFormat.format(totalPar5 / par5Count)).add(AttributeModifier.replace("class", "bold")));

		add(buildLeagueAverageChart());
	}

	private Set<Integer> getScoreYears() {
		Set<Integer> years = new HashSet<Integer>();
		for (WeeklyScore score : selectedPlayer.getScores()) {
			Calendar scoreDate = Calendar.getInstance();
			scoreDate.setTime(score.getScoreDate());
			years.add(scoreDate.get(Calendar.YEAR));
		}
		return years;
	}

	private DropDownChoice<Player> buildPlayerDropDown() {
		DropDownChoice<Player> dropDown = new DropDownChoice<Player>("playerChoice", new PropertyModel<Player>(this, "selectedPlayer"),
				playerChoices, new PlayerRenderer());
		dropDown.setOutputMarkupId(true);
		dropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				PageParameters params = new PageParameters();
				params.add("playerId", selectedPlayer.getId());
				setResponsePage(PlayerDetailPage.class, params);
			}
		});
		return dropDown;
	}

	private DropDownChoice<Integer> buildYearDropDown() {
		DropDownChoice<Integer> component = new DropDownChoice<Integer>("yearChoice", new PropertyModel<Integer>(this, "selectedYear"), yearChoices,
				new YearRenderer());
		component.setOutputMarkupId(true);
		component.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				PageParameters params = new PageParameters();
				params.add("playerId", selectedPlayer.getId());
				params.add("year", selectedYear);
				setResponsePage(PlayerDetailPage.class, params);
			}
		});
		return component;
	}

	private void accumulateScores(WeeklyScore score) {
		eagles = 0;
		birdies = 0;
		pars = 0;
		bogeys = 0;
		doubleBogeys = 0;
		others = 0;

		accumulateHole(score.getSide().getHoles().get(0).getPar(), score.getHole1Score());
		accumulateHole(score.getSide().getHoles().get(1).getPar(), score.getHole2Score());
		accumulateHole(score.getSide().getHoles().get(2).getPar(), score.getHole3Score());
		accumulateHole(score.getSide().getHoles().get(3).getPar(), score.getHole4Score());
		accumulateHole(score.getSide().getHoles().get(4).getPar(), score.getHole5Score());
		accumulateHole(score.getSide().getHoles().get(5).getPar(), score.getHole6Score());
		accumulateHole(score.getSide().getHoles().get(6).getPar(), score.getHole7Score());
		accumulateHole(score.getSide().getHoles().get(7).getPar(), score.getHole8Score());
		accumulateHole(score.getSide().getHoles().get(8).getPar(), score.getHole9Score());

		totalEagles += eagles;
		totalBirdies += birdies;
		totalPars += pars;
		totalBogeys += bogeys;
		totalDoubleBogeys += doubleBogeys;
		totalOthers += others;

		totalGross += score.getScore();
		totalNet += score.getNet();
	}

	private void accumulateHole(int par, int score) {
		switch (par) {
		case 3:
			par3Count++;
			totalPar3 += score;
			break;
		case 4:
			par4Count++;
			totalPar4 += score;
			break;
		case 5:
			par5Count++;
			totalPar5 += score;
			break;
		default:
			break;
		}

		switch (score - par) {
		case -2:
			eagles++;
			break;
		case -1:
			birdies++;
			break;
		case 0:
			pars++;
			break;
		case 1:
			bogeys++;
			break;
		case 2:
			doubleBogeys++;
			break;
		default:
			others++;
			break;
		}
	}

	public Component buildLeagueAverageChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<WeeklyScore> scores = getSelectedYearScores();
		List<Date> scoreDates = new ArrayList<Date>();
		for (WeeklyScore score : scores) {
			dataset.addValue(score.getScore(), "Player Score", SHORT_DATE_FORMAT.format(score.getScoreDate()));
			scoreDates.add(score.getScoreDate());
		}
		Map<Date, Integer> leagueAverages = getLeageYearAverage();
		List<Date> dates = new ArrayList<Date>(leagueAverages.keySet());
		Collections.sort(dates);
		for (Date leagueDate : dates) {
			if (scoreDates.contains(leagueDate)) {
				dataset.addValue(leagueAverages.get(leagueDate), "League Average", SHORT_DATE_FORMAT.format(leagueDate));
			}
		}

		JFreeChart chart = ChartFactory.createLineChart("Scores Compared to League Average", "Date", "Score", dataset, PlotOrientation.VERTICAL,
				true, true, true);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getRangeAxis().setRange(30, 70);

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setDefaultShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setDefaultFillPaint(Color.white);
		return new JFreeChartImage("leagueAverage", chart, 900, 300);
	}

	private Map<Date, Integer> getLeageYearAverage() {
		Map<Date, Integer> averages = new HashMap<Date, Integer>();

		Map<Date, List<Integer>> scoreMap = new HashMap<Date, List<Integer>>();
		List<WeeklyScore> scores = leagueService.getScores(selectedYear);
		for (WeeklyScore score : scores) {
			if (!scoreMap.containsKey(score.getScoreDate())) {
				scoreMap.put(score.getScoreDate(), new ArrayList<Integer>());
			}
			scoreMap.get(score.getScoreDate()).add(score.getScore());
		}

		for (Date key : scoreMap.keySet()) {
			averages.put(key, calculateAverage(scoreMap.get(key)));
		}

		return averages;
	}

	private Integer calculateAverage(List<Integer> scores) {
		int totalScore = 0;
		for (Integer score : scores) {
			totalScore += score;
		}

		return (scores.size() == 0) ? 0 : totalScore / scores.size();
	}

	private List<WeeklyScore> getSelectedYearScores() {
		List<WeeklyScore> scores = new ArrayList<WeeklyScore>(selectedPlayer.getScores());
		Collections.sort(scores);

		List<WeeklyScore> list = new ArrayList<WeeklyScore>();
		for (WeeklyScore score : scores) {
			if (selectedYear != null) {
				Calendar scoreDate = Calendar.getInstance();
				scoreDate.setTime(score.getScoreDate());
				if (scoreDate.get(Calendar.YEAR) != selectedYear) {
					continue;
				}
			}
			list.add(score);
		}
		return list;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

}
