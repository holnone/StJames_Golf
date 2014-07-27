package com.stj.web.ui.system.page;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;

import com.stj.web.ui.base.BasePage;

public class UnauthorizedPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public UnauthorizedPage() {
		String errorMsg = getLocalizer().getString("message.unauthorized", this);
		warn(errorMsg);
	}

	/**
	 * @see wicket.markup.html.WebPage#configureResponse()
	 */
	@Override
	protected void configureResponse(WebResponse response) {
		((HttpServletResponse) getResponse().getContainerResponse()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/**
	 * @see wicket.Component#isVersioned()
	 */
	@Override
	public boolean isVersioned() {
		return false;
	}

	/**
	 * @see wicket.Page#isErrorPage()
	 */
	@Override
	public boolean isErrorPage() {
		return true;
	}
}
