package com.stj.web;

import com.stj.web.ui.HomePage;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

public class MyRequestCycle implements IRequestCycleListener {

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		ex.printStackTrace();
		return new RenderPageRequestHandler(new PageProvider(new HomePage()));
	}

}
