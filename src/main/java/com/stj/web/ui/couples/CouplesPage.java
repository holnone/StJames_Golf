package com.stj.web.ui.couples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.resource.CssResourceReference;

import java.util.ArrayList;
import java.util.List;

public class CouplesPage extends WebPage {

	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedbackPanel;

	public CouplesPage() {
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		feedbackPanel.setEscapeModelStrings(false);
		add(feedbackPanel);

		RepeatingView teamRepeater = new RepeatingView("teamRepeater");
		add(teamRepeater);
		List<String[]> teams = getTeams();
		for (String[] team : teams) {
			WebMarkupContainer item;
			item = new WebMarkupContainer(teamRepeater.newChildId());
			teamRepeater.add(item);
			item.setOutputMarkupId(true);
			item.add(new Label("teamNumber", team[1]));
			item.add(new Label("teamName", team[0]));
		}

		RepeatingView dateRepeater = new RepeatingView("dateRepeater");
		add(dateRepeater);
		List<String> dates = getDates();
		for (String date : dates) {
			WebMarkupContainer item;
			item = new WebMarkupContainer(dateRepeater.newChildId());
			dateRepeater.add(item);
			item.setOutputMarkupId(true);
			item.add(new Label("date", date));
		}
		
		RepeatingView rowRepeater = new RepeatingView("rowRepeater");
		add(rowRepeater);
		List<List<String>> rows = getMatches();
		for (List<String> matches : rows) {
			WebMarkupContainer item;
			item = new WebMarkupContainer(rowRepeater.newChildId());
			item.setOutputMarkupId(true);
			rowRepeater.add(item);
			
			RepeatingView matchRepeater = new RepeatingView("matchRepeater");
			item.add(matchRepeater);
			for (String match : matches) {
				WebMarkupContainer matchItem;
				matchItem = new WebMarkupContainer(matchRepeater.newChildId());
				matchRepeater.add(matchItem);
				matchItem.setOutputMarkupId(true);
				matchItem.add(new Label("match", match));
			}
		}
		
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(new CssResourceReference(CouplesPage.class, "CouplesPage.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(CouplesPage.class, "couples_table.css")));

	}

	List<String[]> getTeams() {
		List<String[]> teams = new ArrayList<String[]>();

		teams.add(new String[] { "Greibel, Andy & Patti", "1" });
		teams.add(new String[] { "Taylor, Bob & Julie // Burbridge, TJ & Brigid", "2" });
		teams.add(new String[] { "Hake, Brian & Barb", "3" });
		teams.add(new String[] { "Murcek, Denise & Jim Greelis", "4" });
		teams.add(new String[] { "Murcek, Ken & Kim", "5" });
		teams.add(new String[] { "Stramel, Claire & Sharon // Wagner, Travis & Allison", "6" });
		teams.add(new String[] { "Wurtz, George & Peg", "7" });

		return teams;
	}

	List<String> getDates() {
		List<String> dates = new ArrayList<String>();

		dates.add("TIME");
		dates.add("5/10");
		dates.add("5/31");
		dates.add("6/14");
		dates.add("6/28");
		dates.add("7/12");
		dates.add("7/26");
		dates.add("8/9");
		dates.add("8/23");

		return dates;
	}

	List<List<String>> getMatches() {
		List<List<String>> rows = new ArrayList<List<String>>();

		List<String> matches = new ArrayList<String>();
		matches.add("4:30");
		matches.add("2-7");
		matches.add("1-5");
		matches.add("5-7");
		matches.add("2-4");
		matches.add("1-2");
		matches.add("4-7");
		matches.add("2-3");
		matches.add("1-5");
		rows.add(matches);

		matches = new ArrayList<String>();
		matches.add("4:37");
		matches.add("1-3");
		matches.add("2-6");
		matches.add("3-6");
		matches.add("3-5");
		matches.add("5-6");
		matches.add("2-5");
		matches.add("1-7");
		matches.add("2-6");
		rows.add(matches);

		matches = new ArrayList<String>();
		matches.add("4:45");
		matches.add("4-6");
		matches.add("3-7");
		matches.add("1-4");
		matches.add("6-7");
		matches.add("3-4");
		matches.add("1-6");
		matches.add("4-5");
		matches.add("3-7");
		rows.add(matches);

		matches = new ArrayList<String>();
		matches.add("4:52");
		matches.add("5");
		matches.add("4");
		matches.add("2");
		matches.add("1");
		matches.add("7");
		matches.add("3");
		matches.add("6");
		matches.add("4");
		rows.add(matches);

		return rows;
	}

	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	public void showFeedback(AjaxRequestTarget target) {
		target.add(getFeedbackPanel());
	}

}
