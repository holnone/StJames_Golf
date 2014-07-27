package com.stj.web.ui.admin;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.stj.web.ui.admin.base.AdminBasePage;

@AuthorizeInstantiation("ROLE_ADMIN")
public class AdminHomePage extends AdminBasePage {
	private static final long serialVersionUID = 1L;

	public AdminHomePage(final PageParameters parameters) {
		add(new Label("message", "Welcome to the St. James Golf Administration"));
	}
}
