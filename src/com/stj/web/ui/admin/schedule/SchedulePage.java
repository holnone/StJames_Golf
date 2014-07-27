package com.stj.web.ui.admin.schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Round;
import com.stj.model.Season;
import com.stj.model.Week;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.util.DateUtils;
import com.stj.web.ui.admin.base.AdminBasePage;

public class SchedulePage extends AdminBasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	private DateFormat dateFormat = new SimpleDateFormat("MM-dd");

	public SchedulePage() {
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);

		RepeatingView weeks = new RepeatingView("week");
		add(weeks);

		boolean setCurrent = true;

		add(new Label("frontNineTeeTime1Group1", Constants.TEE_TIME_1_GROUP_1));
		add(new Label("frontNineTeeTime2Group1", Constants.TEE_TIME_2_GROUP_1));
		add(new Label("frontNineTeeTime3Group1", Constants.TEE_TIME_3_GROUP_1));
		add(new Label("backNineTeeTime1Group1", Constants.TEE_TIME_1_GROUP_1));
		add(new Label("backNineTeeTime2Group1", Constants.TEE_TIME_2_GROUP_1));
		add(new Label("backNineTeeTime3Group1", Constants.TEE_TIME_3_GROUP_1));
		
		add(new Label("frontNineTeeTime1Group2", Constants.TEE_TIME_1_GROUP_2));
		add(new Label("frontNineTeeTime2Group2", Constants.TEE_TIME_2_GROUP_2));
		add(new Label("frontNineTeeTime3Group2", Constants.TEE_TIME_3_GROUP_2));
		add(new Label("backNineTeeTime1Group2", Constants.TEE_TIME_1_GROUP_2));
		add(new Label("backNineTeeTime2Group2", Constants.TEE_TIME_2_GROUP_2));
		add(new Label("backNineTeeTime3Group2", Constants.TEE_TIME_3_GROUP_2));
		
		List<Round> list = new ArrayList<Round>(season.getRounds());
		Collections.sort(list);
		for (Round round : list) {
			List<Week> weekList = new ArrayList<Week>(round.getWeeks());
			Collections.sort(weekList);
			for (Week week : weekList) {
				WebMarkupContainer item;
				item = new WebMarkupContainer(weeks.newChildId());
				item.setOutputMarkupId(true);
				if (setCurrent && DateUtils.isEqualOrAfterToday(week.getDate())) {
					item.add(AttributeModifier.replace("class", "current"));
					setCurrent = false;
				}
				weeks.add(item);

				item.add(new Label("weekDate", dateFormat.format(week.getDate())));
				item.add(new Label("frontNineTeeTime1", week.getFrontNineTeeTime1() != null ? week.getFrontNineTeeTime1().toString() : null));
				item.add(new Label("frontNineTeeTime2", week.getFrontNineTeeTime2() != null ? week.getFrontNineTeeTime2().toString() : null));
				item.add(new Label("frontNineTeeTime3", week.getFrontNineTeeTime3() != null ? week.getFrontNineTeeTime3().toString() : null));
				item.add(new Label("backNineTeeTime1", week.getBackNineTeeTime1() != null ? week.getBackNineTeeTime1().toString() : null));
				item.add(new Label("backNineTeeTime2", week.getBackNineTeeTime2() != null ? week.getBackNineTeeTime2().toString() : null));
				item.add(new Label("backNineTeeTime3", week.getBackNineTeeTime3() != null ? week.getBackNineTeeTime3().toString() : null));

				item.add(getEditWeekLink("editWeekLink", week));
			}
		}
	}

	private Component getEditWeekLink(String componentId, final Week week) {
		AjaxLink<String> ajaxLink = new AjaxLink<String>(componentId) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				showFeedback(target);
				setResponsePage(new EditWeekPage(week));
			}

		};
		return ajaxLink;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

}
