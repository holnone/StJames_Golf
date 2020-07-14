package com.stj.web.ui.admin.base;

import com.stj.web.ui.HomePage;
import com.stj.web.ui.base.BasePage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.resource.CssResourceReference;

@AuthorizeInstantiation("ROLE_ADMIN")
public class AdminBasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedbackPanel;

	public AdminBasePage() {
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		feedbackPanel.setEscapeModelStrings(false);
		add(feedbackPanel);

		add(getLogoutLink("logout"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "BasePage.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "table.css")));
	}

	private Component getLogoutLink(String componentId) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				AuthenticatedWebSession session = AuthenticatedWebSession.get();
				session.signOut();
				setResponsePage(HomePage.class);
			}

		};
		return ajaxLink;
	}

	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	public void showFeedback(AjaxRequestTarget target) {
		target.add(getFeedbackPanel());
	}

}
