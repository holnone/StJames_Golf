package com.stj.web.webservice;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.external.DefaultExternalDataService;
import com.stj.external.model.ExternalRoster;
import com.stj.external.model.Schedule;
import com.stj.external.model.Standings;
import com.stj.model.ApplicationProperty;
import com.stj.services.ApplicationPropertyService;
import com.stj.util.Constants;

public class ExternalDataPage extends WebServicePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private DefaultExternalDataService externalDataService;

	@SpringBean
	private ApplicationPropertyService applicationPropertyService;

	public ExternalDataPage(PageParameters pg) {
		Integer year = null;//pg.getAsInteger("year");
		String method = pg.get("method").toString();
		if (year == null) {
			year = Constants.CURRENT_YEAR;
		}

		Object object = null;
		if ("getPlayers".equalsIgnoreCase(method)) {
			object = getPlayers();
		} else if ("getSchedule".equalsIgnoreCase(method)) {
			object = getSchedule(year);
		} else if ("getStandings".equalsIgnoreCase(method)) {
			object = getStandings(year);
		} else if ("getMessage".equalsIgnoreCase(method)) {
			object = getMessage();
		}
		setDefaultModel(new Model<Serializable>((Serializable) object));
		add(new Label("data", getXML()));
	}

	private ExternalRoster getPlayers() {
		return externalDataService.getPlayers();
	}

	public Schedule getSchedule(Integer year) {
		return externalDataService.getSchedule(year);
	}

	public Standings getStandings(Integer year) {
		return externalDataService.getStandings(year);
	}

	public String getMessage() {
		ApplicationProperty publicMessage = applicationPropertyService.find(Constants.PUBLIC_MESSAGE);
		return publicMessage.getValue();
	}
}
