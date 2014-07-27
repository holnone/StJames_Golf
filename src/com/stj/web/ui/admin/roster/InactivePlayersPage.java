package com.stj.web.ui.admin.roster;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Player;
import com.stj.services.LeagueService;
import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.wicket.component.IconLinkPanel;

public class InactivePlayersPage extends AdminBasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	public InactivePlayersPage() {
		List<Player> inactivePlayers = leagueService.getAllInactivePlayers();
		RepeatingView repeater = new RepeatingView("repeater");
		add(repeater);
		if (inactivePlayers != null && !inactivePlayers.isEmpty()) {
			WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
			container.setOutputMarkupId(true);
			repeater.add(container);
			container.add(new Label("inactivePlayers", "Inactive Players"));
			container.add(new Label("last5Scores", "Last 5 Scores"));
			container.add(new Label("hdcp", "HDCP"));
			RepeatingView playerRepeater = new RepeatingView("playerRepeater");
			container.add(playerRepeater);
			WebMarkupContainer playerContainer;
			Collections.sort(inactivePlayers);
			for (Player player : inactivePlayers) {
				playerContainer = new WebMarkupContainer(playerRepeater.newChildId());
				addPlayerLabels(playerContainer, player);
				playerRepeater.add(playerContainer);
			}

		}
	}

	private void addPlayerLabels(WebMarkupContainer item, Player player) {
		item.add(new IconLinkPanel("activatePlayer", "res/add.jpg", getActivatePlayerLink("link", player)));
		item.add(getEditPlayerLink("playerLink", player));
		item.add(new Label("scores", player.getLast5Rounds_String()));
		item.add((new Label("hdcp", player.getCurrentHandicap(null) == null ? "" : String.valueOf(player.getCurrentHandicap(null))))
				.add(new Behavior[] { AttributeModifier.replace("class", "handicap") }));
	}

	private Component getEditPlayerLink(final String componentId, final Player player) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				setResponsePage(new EditPlayerPage(InactivePlayersPage.this, player));
			}

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, player.toString());
			}

		};
		return ajaxLink;
	}

	private AjaxLink<String> getActivatePlayerLink(String componentId, final Player player) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				player.setTeam(null);
				player.setActive(true);
				leagueService.savePlayer(player);
				getSession().info((new StringBuilder("Player '")).append(player.toString()).append("' activated.").toString());
				setResponsePage(new RosterPage());
			}
		};
		return ajaxLink;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

}
