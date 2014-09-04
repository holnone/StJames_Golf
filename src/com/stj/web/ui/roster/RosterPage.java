package com.stj.web.ui.roster;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.core.util.resource.PackageResourceStream;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Player;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.base.BasePage;
import com.stj.web.ui.team.TeamPage;

public class RosterPage extends BasePage {

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
			int idx = team1Size > team2Size ? team1Size : team2Size;
			RepeatingView playerRepeater = new RepeatingView("playerRepeater");
			container.add(playerRepeater);
			for (int i = 0; i < idx; i++) {
				WebMarkupContainer playerContainer = new WebMarkupContainer(playerRepeater.newChildId());
				playerRepeater.add(playerContainer);
				Player player1 = new Player();
				Player player2 = new Player();
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
			container.add(new Label("team2Link", ""));
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
				addPlayerLabels(playerContainer, player1, new Player());
			}
		}

	}

	private void addTeamLabels(WebMarkupContainer item, Team team1, Team team2) {
		if (team1.getTeamNumber() == 3) {
			item.add(getTeamScheduleLink("team1Link", team1));
		} else {
			item.add(new Label("team1Link", team1.toString()));
		}
		if (team2 != null) {
			if (team2.getTeamNumber() == 6) {
				item.add(getTeamLink("team2Link", team2));
			} else {
				item.add(new Label("team2Link", team2.toString()));
			}
		} else {
			item.add(new Label("team2Link", ""));
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
		item.add(getPlayerDetailLink("playerName1", player1));
		item.add(new Label("scores1", player1.getLast5Rounds_String()));
		item.add(new Label("hdcp1", player1.getCurrentHandicap(null) != null ? String.valueOf(player1.getCurrentHandicap(null)) : "")
				.add(AttributeModifier.replace("class", "handicap")));
		item.add(getPlayerDetailLink("playerName2", player2));
		item.add(new Label("scores2", player2.getLast5Rounds_String()));
		item.add(new Label("hdcp2", player2.getCurrentHandicap(null) != null ? String.valueOf(player2.getCurrentHandicap(null)) : "")
				.add(AttributeModifier.replace("class", "handicap")));
	}

	private Component getTeamLink(String componentId, final Team team) {
		PageParameters params = new PageParameters();
		params.add("teamId", team.getId());
		BookmarkablePageLink<String> link = new BookmarkablePageLink<String>(componentId, TeamPage.class, params) {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, team.toString());
			}

		};
		return link;
	}

	private ResourceLink<Object> getTeamScheduleLink(String componentId, final Team team) {
		int BUFFER_SIZE = 10 * 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String fileName = team.getName() + "_Schedule_" + Calendar.getInstance().get(Calendar.YEAR) + ".pdf";
		PackageResourceStream stream = new PackageResourceStream(RosterPage.class, fileName);
		try {
			InputStream is = stream.getInputStream();
			byte[] buf = new byte[BUFFER_SIZE];

			while (true) {
				int tam = is.read(buf);
				if (tam == -1) {
					break;
				}
				out.write(buf, 0, tam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		byte[] pdf = out.toByteArray();
		PopupSettings popupSettings = new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS).setHeight(600).setWidth(800);

		ByteArrayResource resource = new ByteArrayResource("application/pdf", pdf);

		ResourceLink<Object> resourceLink = new ResourceLink<Object>(componentId, resource) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, team.toString());
			}

		};

		resourceLink.setPopupSettings(popupSettings);

		return resourceLink;
	}

	private Component getPlayerDetailLink(String componentId, final Player player) {
		PageParameters params = new PageParameters();
		if (player.getId() != null) {
			params.add("playerId", player.getId());
		}
		BookmarkablePageLink<String> link = new BookmarkablePageLink<String>(componentId, PlayerDetailPage.class, params) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, player.toString());
			}

		};
		return link;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}
}
