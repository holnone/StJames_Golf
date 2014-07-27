package com.stj.web.ui.base;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.stj.web.ui.HomePage;
import com.stj.web.ui.admin.schedule.SchedulePage;
import com.stj.web.ui.contact.ContactUsPage;
import com.stj.web.ui.messageboard.MessageBoardPage;
import com.stj.web.ui.roster.RosterPage;
import com.stj.web.ui.roster.SkinsPage;
import com.stj.web.ui.scorecard.ScorecardBasePage;
import com.stj.web.ui.scores.ScoresPage;

public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedbackPanel;

	public BasePage() {
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		feedbackPanel.setEscapeModelStrings(false);
		add(feedbackPanel);

		// Add menu links
		add(new BookmarkablePageLink<Void>("homeLink", HomePage.class));
		add(new BookmarkablePageLink<Void>("messageBoardLink", MessageBoardPage.class));
		add(new BookmarkablePageLink<Void>("skinsLink", SkinsPage.class));
		add(new BookmarkablePageLink<Void>("rosterLink", RosterPage.class));
		add(new BookmarkablePageLink<Void>("scoresLink", ScoresPage.class));
		add(new BookmarkablePageLink<Void>("scorecardLink", ScorecardBasePage.class));
		add(new BookmarkablePageLink<Void>("contactUsLink", ContactUsPage.class));
		add(new BookmarkablePageLink<Void>("adminLink", SchedulePage.class));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "BasePage.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "table.css")));
	}

	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	public void showFeedback(AjaxRequestTarget target) {
		target.add(getFeedbackPanel());
	}

}
