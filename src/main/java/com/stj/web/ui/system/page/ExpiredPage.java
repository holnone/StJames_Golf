package com.stj.web.ui.system.page;

import com.stj.web.ui.base.BasePage;
import org.apache.wicket.request.http.WebResponse;

import javax.servlet.http.HttpServletResponse;

public class ExpiredPage extends BasePage {
    private static final long serialVersionUID = 704998101532211509L;

    public ExpiredPage() {
        info(getLocalizer().getString("message.pageExpired", this));
    }
    
    /**
     * @see wicket.markup.html.WebPage#configureResponse()
     */
    @Override
    protected void configureResponse(WebResponse response) {
    	((HttpServletResponse) getResponse().getContainerResponse()).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
