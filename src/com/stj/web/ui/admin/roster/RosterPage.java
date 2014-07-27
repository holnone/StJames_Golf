package com.stj.web.ui.admin.roster;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Player;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.wicket.component.IconLinkPanel;

public class RosterPage extends AdminBasePage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private LeagueService leagueService;

	public RosterPage() {
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);
		List<Player> nonTeamPlayers = leagueService.getNonTeamPlayers();

		RepeatingView repeater = new RepeatingView("repeater");
		add(repeater);

		for (Iterator<Team> iter = season.getTeams().iterator(); iter.hasNext();) {
			Team team1 = iter.next();
			Team team2 = null;
			if (iter.hasNext()) {
				team2 = iter.next();
			}

			WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
			container.setOutputMarkupId(true);
			repeater.add(container);

			addTeamLabels(container, team1, team2);

			int team1Size = team1.getSortedPlayers().size();
			int team2Size = team2 != null ? team2.getSortedPlayers().size() : 0;
			int idx = team1Size <= team2Size ? team2Size : team1Size;
			RepeatingView playerRepeater = new RepeatingView("playerRepeater");
			container.add(playerRepeater);
			for (int i = 0; i < idx; i++) {
				WebMarkupContainer playerContainer = new WebMarkupContainer(playerRepeater.newChildId());
				playerRepeater.add(playerContainer);
				Player player1 = null;
				Player player2 = null;
				if (i < team1Size) {
					player1 = team1.getSortedPlayers().get(i);
				}
				if (i < team2Size) {
					player2 = team2.getSortedPlayers().get(i);
				}
				addPlayerLabels(playerContainer, player1, player2);
			}
		}

		if (nonTeamPlayers != null && !nonTeamPlayers.isEmpty()) {
			WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
			container.setOutputMarkupId(true);
			repeater.add(container);
			container.add(new Label("team1Link", "Players without a Team"));
			container.add(new Label("team1EditLink", ""));
			container.add(new Label("team2Link", ""));
			container.add(new Label("team2EditLink", ""));
			container.add(new Label("last5Scores1", "Last 5 Scores"));
			container.add(new Label("hdcp1", "HDCP"));
			container.add(new Label("last5Scores2", " "));
			container.add(new Label("hdcp2", " "));

			RepeatingView playerRepeater = new RepeatingView("playerRepeater");
			container.add(playerRepeater);
			Collections.sort(nonTeamPlayers);
			for (Player player1 : nonTeamPlayers) {
				WebMarkupContainer playerContainer = new WebMarkupContainer(playerRepeater.newChildId());
				playerRepeater.add(playerContainer);
				addPlayerLabels(playerContainer, player1, null);
			}
		}
	}

	private void addTeamLabels(WebMarkupContainer item, Team team1, Team team2) {
		item.add(new Label("team1Link", team1.toString()));
		item.add(getEditTeamLink("team1EditLink", team1));
		if (team2 != null) {
			if (team2.getTeamNumber() == 6) {
				item.add(getTeamLink("team2Link", team2));
			} else {
				item.add(new Label("team2Link", team2.toString()));
			}
			item.add(getEditTeamLink("team2EditLink", team2));
		} else {
			item.add(new Label("team2Link", ""));
			item.add(new Label("team2EditLink", ""));
		}
		item.add(new Label("last5Scores1", "Last 5 Scores"));
		item.add(new Label("hdcp1", "HDCP"));
		if (team2 != null) {
			item.add(new Label("last5Scores2", "Last 5 Scores"));
			item.add(new Label("hdcp2", "HDCP"));
		} else {
			item.add(new Label("last5Scores2", " "));
			item.add(new Label("hdcp2", " "));
		}
	}

	private void addPlayerLabels(WebMarkupContainer item, Player player1, Player player2) {
		if (player1 != null) {
			item.add(new IconLinkPanel("deactivatePlayer1", "res/delete.png", getDeactivatePlayerLink("link", player1)));
			item.add(getEditPlayerLink("playerLink1", player1));
			item.add(new Label("scores1", player1.getLast5Rounds_String()));
			item.add(new Label("hdcp1", player1.getCurrentHandicap(null) == null ? "" : String.valueOf(player1.getCurrentHandicap(null)))).add(
					AttributeModifier.replace("class", "handicap"));
		} else {
			item.add(new Label("deactivatePlayer1", ""));
			item.add(new Label("playerLink1", ""));
			item.add(new Label("scores1", ""));
			item.add(new Label("hdcp1", ""));
		}
		if (player2 != null) {
			item.add(new IconLinkPanel("deactivatePlayer2", "res/delete.png", getDeactivatePlayerLink("link", player2)));
			item.add(getEditPlayerLink("playerLink2", player2));
			item.add(new Label("scores2", player2.getLast5Rounds_String()));
			item.add((new Label("hdcp2", player2.getCurrentHandicap(null) == null ? "" : String.valueOf(player2.getCurrentHandicap(null))))
					.add(AttributeModifier.replace("class", "handicap")));
		} else {
			item.add(new Label("deactivatePlayer2", ""));
			item.add(new Label("playerLink2", ""));
			item.add(new Label("scores2", ""));
			item.add(new Label("hdcp2", ""));
		}
	}

	private Component getTeamLink(String componentId, final Team team) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				setResponsePage(new TeamPlayerSchedulePage(team));
			}

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, team.toString());
			}

		};
		return ajaxLink;
	}

	private Component getEditTeamLink(String componentId, final Team team) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				setResponsePage(new EditTeamPage(team));
			}

		};
		return ajaxLink;
	}

	private Component getEditPlayerLink(String componentId, final Player player) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				setResponsePage(new EditPlayerPage(RosterPage.this, player));
			}

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, player.toString());
			}

		};
		return ajaxLink;
	}

	private AjaxLink<String> getDeactivatePlayerLink(final String componentId, final Player player) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				player.setTeam(null);
				player.setActive(false);
				leagueService.savePlayer(player);
				getSession().info((new StringBuilder("Player '")).append(player.toString()).append("' deactivated.").toString());
				setResponsePage(new RosterPage());
			}
		};
		return ajaxLink;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}
}
