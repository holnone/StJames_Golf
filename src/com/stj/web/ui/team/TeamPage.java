package com.stj.web.ui.team;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import com.stj.model.Player;
import com.stj.model.PlayerSchedule;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.model.Week;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.admin.roster.TeamPlayerSchedulePage;
import com.stj.web.ui.base.BasePage;

public class TeamPage extends BasePage {
	private static final long serialVersionUID = 1L;

	final static DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd");

	@SpringBean
	LeagueService leagueService;

	public TeamPage(PageParameters params) {
		Team team = leagueService.getTeam(Integer.valueOf(params.get("teamId").toString()));
		initPage(team);
	}
	public TeamPage(Team team) {
		initPage(team);
	}
	
	void initPage(Team team) {
		add(new Label("teamLabel", team.toString()));
		RepeatingView repeater = new RepeatingView("repeater1");
		add(repeater);
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);
		List<Week> weekList = season.getAllWeeks();
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 0);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		DateTime now = new DateTime(nowCal.getTime());
		boolean setCurrent = true;
		for (Week week : weekList) {
			WebMarkupContainer item;
			item = new WebMarkupContainer(repeater.newChildId());
			item.setOutputMarkupId(true);
			DateTime matchDate = new DateTime(week.getDate());
			if (setCurrent && (!matchDate.isBeforeNow() || matchDate.isEqual(now))) {
				item.add(AttributeModifier.replace("class", "current"));
				setCurrent = false;
			}
			repeater.add(item);

			item.add(getWeekLink("week", team, week));
			item.add(new Label("onPlayers", getOnPlayers(team, week)));
			item.add(new Label("offPlayers", getOffPlayers(team, week)));
		}
	}

	private Component getWeekLink(String componentId, final Team team, final Week week) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				setResponsePage(new TeamPlayerSchedulePage(team, week));
			}

			@Override
			public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, DATE_FORMAT.format(week.getDate()));
			}

		};
		return ajaxLink;
	}

	String getOnPlayers(Team team, Week week) {
		StringBuffer sb = new StringBuffer();
		PlayerSchedule schedule = team.getPlayerSchedule(week);
		List<Player> players = new ArrayList<Player>(schedule.getPlayers());
		Collections.sort(players);
		for (Iterator<Player> iter = players.iterator(); iter.hasNext();) {
			sb.append(iter.next().getPlayerName().getFirstName());
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	String getOffPlayers(Team team, Week week) {
		StringBuffer sb = new StringBuffer();
		List<Player> players = new ArrayList<Player>(team.getSortedPlayers());
		PlayerSchedule schedule = team.getPlayerSchedule(week);
		players.removeAll(schedule.getPlayers());
		Collections.sort(players);
		for (Iterator<Player> iter = players.iterator(); iter.hasNext();) {
			sb.append(iter.next().getPlayerName().getFirstName());
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}
