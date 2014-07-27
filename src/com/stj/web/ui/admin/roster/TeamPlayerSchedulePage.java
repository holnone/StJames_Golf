package com.stj.web.ui.admin.roster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Player;
import com.stj.model.PlayerSchedule;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.model.Week;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.ui.roster.RosterPage;

public class TeamPlayerSchedulePage extends AdminBasePage {
	private static final long serialVersionUID = 1L;

	Team team = new Team();

	@SpringBean
	LeagueService leagueService;

	List<Week> weekChoices = new ArrayList<Week>();

	List<Player> selectedOriginals = new ArrayList<Player>();
	List<Player> selectedDestinations = new ArrayList<Player>();
	List<Player> originalChoices = new ArrayList<Player>();
	List<Player> destinationChoices = new ArrayList<Player>();

	ListMultipleChoice<Player> originals;
	ListMultipleChoice<Player> destinations;
	Week selectedWeek;
	PlayerSchedule selectedSchedule;

	Form<Object> form;

	public TeamPlayerSchedulePage(Team team) {
		this.team = team;
		init(null);
	}

	public TeamPlayerSchedulePage(Team team, Week week) {
		this.team = team;
		init(week);
	}

	void init(Week week) {
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);
		weekChoices = season.getAllWeeks();
		if (week == null) {
			selectedWeek = season.getCurrentWeek();
		} else {
			selectedWeek = week;
		}
		refreshData();
		renderContent();
	}

	private void renderContent() {
		form = new Form<Object>("form");
		form.setOutputMarkupId(true);
		form.add(new Label("teamLabel", team.toString()));
		form.add(buildWeekDropDown());
		originals = new ListMultipleChoice<Player>("originals", new PropertyModel(this, "selectedOriginals"), new ListModel<Player>(
				this.originalChoices), getPlayerRenderer());
		originals.setOutputMarkupId(true);
		destinations = new ListMultipleChoice<Player>("destinations", new PropertyModel(this, "selectedDestinations"), new ListModel<Player>(
				this.destinationChoices), getPlayerRenderer());
		destinations.setOutputMarkupId(true);
		AjaxButton add = new AjaxButton("add") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				update(target, selectedOriginals, originals, destinations);
			}
		};

		AjaxButton remove = new AjaxButton("remove") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				update(target, selectedDestinations, destinations, originals);
			}
		};
		form.add(originals);
		form.add(destinations);
		form.add(add);
		form.add(remove);
		form.add(getSaveButton());
		form.add(getBackButton());
		add(form);
	}

	void refreshData() {
		selectedSchedule = team.getPlayerSchedule(selectedWeek);
		this.originalChoices.clear();
		this.originalChoices.addAll(leagueService.getAllPlayers());
		this.originalChoices.removeAll(this.team.getPlayers());
		Collections.sort(originalChoices);
		this.originalChoices.addAll(0, this.team.getSortedPlayers());
		this.destinationChoices.clear();
		this.destinationChoices.addAll(selectedSchedule.getPlayers());
		this.originalChoices.removeAll(destinationChoices);
		Collections.sort(destinationChoices);
		selectedOriginals.clear();
		selectedDestinations.clear();
	}

	private DropDownChoice<Week> buildWeekDropDown() {
		DropDownChoice<Week> component = new DropDownChoice<Week>("weekChoice", new PropertyModel<Week>(this, "selectedWeek"), weekChoices,
				getWeekRenderer());
		component.setOutputMarkupId(true);
		component.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				refreshData();
				target.add(originals);
				target.add(destinations);
			}
		});
		return component;
	}

	@SuppressWarnings("unchecked")
	private void update(AjaxRequestTarget target, List<Player> selections, ListMultipleChoice<Player> from, ListMultipleChoice<Player> to) {
		for (Player player : selections) {
			List<Player> choices = getChoices(from);
			if (!to.getChoices().contains(player)) {
				((List<Player>) to.getChoices()).add(player);
				choices.remove(player);
				from.setChoices(choices);
			}
		}
		Collections.sort(to.getChoices());
		target.add(to);
		target.add(from);
	}

	private List<Player> getChoices(ListMultipleChoice<Player> choice) {
		List<Player> choices = new ArrayList<Player>();
		choices.addAll(choice.getChoices());
		return choices;
	}

	private Component getSaveButton() {
		return new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				showFeedback(target);
				selectedSchedule.getPlayers().clear();
				selectedSchedule.getPlayers().addAll(destinations.getChoices());
				if (selectedSchedule.getId() == null) {
					team.addSchedule(selectedSchedule);
				}
				team = leagueService.mergeTeam(team);
				getSession().info("Player Schedule Saved Successfully.");
				setResponsePage(new TeamPlayerSchedulePage(team, selectedWeek));
			}
		}.setMarkupId("saveButton");
	}

	private Component getBackButton() {
		AjaxButton cancelButton = new AjaxButton("backButton") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				setResponsePage(new RosterPage());
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		return cancelButton;
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

	private IChoiceRenderer<Player> getPlayerRenderer() {
		return new IChoiceRenderer<Player>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Player player) {
				return player.toString();
			}

			public String getIdValue(Player player, int index) {
				return player.getId().toString();
			}
		};
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	public void setSelectedOriginals(List<Player> selectedOriginals) {
		this.selectedOriginals = selectedOriginals;
	}

	public void setSelectedDestinations(List<Player> selectedDestinations) {
		this.selectedDestinations = selectedDestinations;
	}

	public void setOriginals(ListMultipleChoice<Player> originals) {
		this.originals = originals;
	}

	public void setDestinations(ListMultipleChoice<Player> destinations) {
		this.destinations = destinations;
	}

	public void setOriginalChoices(List<Player> originalChoices) {
		this.originalChoices = originalChoices;
	}

	public void setDestinationChoices(List<Player> destinationChoices) {
		this.destinationChoices = destinationChoices;
	}
}
