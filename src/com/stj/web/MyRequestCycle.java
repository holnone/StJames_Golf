package com.stj.web;

import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import com.stj.web.ui.HomePage;

public class MyRequestCycle extends AbstractRequestCycleListener {

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		ex.printStackTrace();
		return new RenderPageRequestHandler(new PageProvider(new HomePage()));
	}

}
