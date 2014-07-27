package com.stj.web.ui.roster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.wicket.AttributeModifier;
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

import com.stj.model.BackNine;
import com.stj.model.FrontNine;
import com.stj.model.Player;
import com.stj.model.Round;
import com.stj.model.Season;
import com.stj.model.Week;
import com.stj.model.WeeklyScore;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.base.BasePage;

public class SkinsPage extends BasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	private Week selectedWeek;

	WebMarkupContainer skinsContainer;

	public SkinsPage() {
		initPage();
	}

	public SkinsPage(final PageParameters params) {
		try {
			this.selectedWeek = leagueService.getWeek(Integer.parseInt(params.get("selectedWeek").toString()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		initPage();
	}

	private void initPage() {
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);
		List<Round> list = new ArrayList<Round>(season.getRounds());
		List<Week> weekChoices = new ArrayList<Week>();
		for (Round round : list) {
			weekChoices.addAll(round.getWeeks());
		}
		Collections.sort(weekChoices);
		if (selectedWeek == null) {
			selectedWeek = weekChoices.get(0);
			for (Week week : weekChoices) {
				if (week.getDate().before(Calendar.getInstance().getTime())) {
					selectedWeek = week;
				}
			}
		}

		renderContent(weekChoices);
	}

	private void renderContent(List<Week> weekChoices) {
		add(buildWeekDropDown(weekChoices));
		add(getSkinsContainer());
		RepeatingView repeater = new RepeatingView("repeater");
		getSkinsContainer().add(repeater);

		List<WeeklyScore> scores = new ArrayList<WeeklyScore>();
		String publicMessage = "";
		List<Player> skinsPlayers = leagueService.getSkinsPlayers(selectedWeek.getDate());
		if (!skinsPlayers.isEmpty()) {
			scores = leagueService.getScores(selectedWeek.getDate(), skinsPlayers);
		} else {
			publicMessage = "No players signed up for skins";
		}
		if (StringUtils.isBlank(publicMessage) && scores.isEmpty()) {
			publicMessage = "No scores have been entered for this week.";
		}
		add(new Label("publicMessage", publicMessage));
		Collections.sort(scores, new WeeklyScorePlayerComparator());
		Map<Integer, Integer> lowScoreMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> lowScoreCountMap = new HashMap<Integer, Integer>();
		findLowScores(scores, lowScoreMap, lowScoreCountMap);
		for (WeeklyScore score : scores) {
			WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
			container.setOutputMarkupId(true);
			repeater.add(container);

			container.add(new Label("playerName", score.getPlayer().toString()).add(AttributeModifier.replace("class", "playerName")));
			if (score.getSide() instanceof FrontNine) {
				for (int i = 1; i <= 9; i++) {
					Label scoreLabel = new Label("hole" + i, String.valueOf(score.getHoleScore(i)));
					if (lowScoreMap.get(i).equals(score.getHoleScore(i)) && lowScoreCountMap.get(i).equals(1)) {
						scoreLabel.add(AttributeModifier.replace("class", "lowScore"));
					}
					container.add(scoreLabel);
				}
				for (int i = 10; i <= 18; i++) {
					container.add(new Label("hole" + i, ""));
				}
			} else {
				for (int i = 1; i <= 9; i++) {
					container.add(new Label("hole" + i, ""));
				}
				for (int i = 10; i <= 18; i++) {
					Label scoreLabel = new Label("hole" + i, String.valueOf(score.getHoleScore(i - 9)));
					if (lowScoreMap.get(i).equals(score.getHoleScore(i - 9)) && lowScoreCountMap.get(i).equals(1)) {
						scoreLabel.add(AttributeModifier.replace("class", "lowScore"));
					}
					container.add(scoreLabel);
				}
			}
		}
	}

	private DropDownChoice<Week> buildWeekDropDown(List<Week> weekChoices) {
		DropDownChoice<Week> component = new DropDownChoice<Week>("weekChoice", new PropertyModel<Week>(this, "selectedWeek"), weekChoices,
				getWeekRenderer());
		component.setOutputMarkupId(true);
		component.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				PageParameters parameters = new PageParameters();
				parameters.add("selectedWeek", selectedWeek.getId());
				setResponsePage(SkinsPage.class, parameters);
			}
		});
		return component;
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

	private WebMarkupContainer getSkinsContainer() {
		if (skinsContainer == null) {
			skinsContainer = new WebMarkupContainer("skinsContainer");
			skinsContainer.setOutputMarkupId(true);
			skinsContainer.add(AttributeModifier.replace("class", "stj-field"));
		}
		return skinsContainer;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	private class WeeklyScorePlayerComparator implements Comparator<WeeklyScore> {

		@Override
		public int compare(WeeklyScore score1, WeeklyScore score2) {
			return new CompareToBuilder().append(score1.getSide(), score2.getSide()).append(score1.getPlayer(), score2.getPlayer()).toComparison();
		}

	}

	private void findLowScores(List<WeeklyScore> scores, Map<Integer, Integer> lowScoreMap, Map<Integer, Integer> lowScoreCountMap) {
		initializeMaps(lowScoreMap, lowScoreCountMap);
		for (WeeklyScore score : scores) {
			if (score.getSide() instanceof FrontNine) {
				for (int i = 1; i <= 9; i++) {
					if (score.getHoleScore(i) < lowScoreMap.get(i)) {
						lowScoreMap.put(i, score.getHoleScore(i));
						lowScoreCountMap.put(i, 1);
					} else if (score.getHoleScore(i).equals(lowScoreMap.get(i))) {
						lowScoreCountMap.put(i, lowScoreCountMap.get(i) + 1);
					}
				}
			} else if (score.getSide() instanceof BackNine) {
				for (int i = 10; i <= 18; i++) {
					if (score.getHoleScore(i - 9) < lowScoreMap.get(i)) {
						lowScoreMap.put(i, score.getHoleScore(i - 9));
						lowScoreCountMap.put(i, 1);
					} else if (score.getHoleScore(i - 9).equals(lowScoreMap.get(i))) {
						lowScoreCountMap.put(i, lowScoreCountMap.get(i) + 1);
					}
				}
			}
		}
	}

	private void initializeMaps(Map<Integer, Integer> lowScoreMap, Map<Integer, Integer> lowScoreCountMap) {
		lowScoreMap.clear();
		lowScoreCountMap.clear();
		for (int i = 1; i <= 18; i++) {
			lowScoreMap.put(i, 99);
			lowScoreCountMap.put(i, 0);
		}
	}
}
