package com.stj.web.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;

import com.stj.web.ui.base.BasePage;
import com.stj.web.wicket.component.FocusComponentBehavior;

public class LoginPage extends WebPage {

	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedbackPanel;

	public LoginPage() {
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setEscapeModelStrings(false);
		add(feedbackPanel);

		add(new LoginForm("form"));
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

	class LoginForm extends Form<LoginForm> {
		private static final long serialVersionUID = 1L;

		private String username;

		private String password;

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel<LoginForm>(this));
			add(new RequiredTextField<String>("username").add(new FocusComponentBehavior()));
			add(new PasswordTextField("password"));
		}

		@Override
		protected void onSubmit() {
			AuthenticatedWebSession session = AuthenticatedWebSession.get();
			if (session.signIn(username, password)) {
				setDefaultResponsePageIfNecessary();
			} else {
				error("Login Failed");
			}
		}

		private void setDefaultResponsePageIfNecessary() {
			continueToOriginalDestination();
			setResponsePage(getApplication().getHomePage());
		}
	}
}
