package com.stj.web.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import com.stj.model.ApplicationProperty;
import com.stj.model.Player;
import com.stj.model.Round;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.model.Week;
import com.stj.services.ApplicationPropertyService;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.util.PdfExportUtils;
import com.stj.web.ui.base.BasePage;
import com.stj.web.wicket.component.FocusComponentBehavior;

public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private LeagueService leagueService;

	@SpringBean
	private ApplicationPropertyService applicationPropertyService;

	private Week selectedWeek;

	ModalWindow pdfModal;

	ApplicationProperty publicMessage;

	public HomePage() {
		initPage();
	}

	public HomePage(final PageParameters parameters) {
		try {
			Integer weekId = Integer.parseInt(parameters.get("selectedWeek").toString());
			this.selectedWeek = leagueService.getWeek(weekId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		initPage();
	}

	private void initPage() {
		publicMessage = applicationPropertyService.find(Constants.PUBLIC_MESSAGE);
		Season season = leagueService.getSeason(Constants.CURRENT_YEAR);
		List<Round> rounds = new ArrayList<Round>(season.getRounds());
		Collections.sort(rounds);
		Round round1 = rounds.get(0);
		Round round2 = rounds.get(1);
		List<Week> weekChoices = new ArrayList<Week>();
		for (Round round : rounds) {
			weekChoices.addAll(round.getWeeks());
		}
		Collections.sort(weekChoices);
		if (selectedWeek == null) {
			selectedWeek = weekChoices.get(0);
			for (Week week : weekChoices) {
				if (week.getDate().before(Calendar.getInstance().getTime())) {
					selectedWeek = week;
				}
			}
		}

		add(new Label("publicMessage", publicMessage != null ? publicMessage.getValue() : ""));
		add(buildWeekDropDown(weekChoices));
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

		RepeatingView round1Repeater = new RepeatingView("round1");
		add(round1Repeater);
		List<Week> weekList = new ArrayList<Week>(round1.getWeeks());
		Collections.sort(weekList);
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 0);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		DateTime matchDate = new DateTime();
		DateTime now = new DateTime(nowCal.getTime());
		for (Week week : weekList) {
			WebMarkupContainer item;
			item = new WebMarkupContainer(round1Repeater.newChildId());
			item.setOutputMarkupId(true);
			matchDate = new DateTime(week.getDate());
			if (setCurrent && (!matchDate.isBefore(now.getMillis()) || matchDate.isEqual(now.getMillis()))) {
				item.add(AttributeModifier.replace("class", "current"));
				setCurrent = false;
			}
			round1Repeater.add(item);

			item.add(new Label("weekDate", Constants.DATE_FORMAT.format(week.getDate())));
			item.add(new Label("frontNineTeeTime1", week.getFrontNineTeeTime1() != null ? week.getFrontNineTeeTime1().toString() : null));
			item.add(new Label("frontNineTeeTime2", week.getFrontNineTeeTime2() != null ? week.getFrontNineTeeTime2().toString() : null));
			item.add(new Label("frontNineTeeTime3", week.getFrontNineTeeTime3() != null ? week.getFrontNineTeeTime3().toString() : null));
			item.add(new Label("backNineTeeTime1", week.getBackNineTeeTime1() != null ? week.getBackNineTeeTime1().toString() : null));
			item.add(new Label("backNineTeeTime2", week.getBackNineTeeTime2() != null ? week.getBackNineTeeTime2().toString() : null));
			item.add(new Label("backNineTeeTime3", week.getBackNineTeeTime3() != null ? week.getBackNineTeeTime3().toString() : null));
			
			//Add Easter weekend label
			if (matchDate.isEqual(new DateTime(2017, 4, 7, 0, 0, 0, 0))) {
				item = new WebMarkupContainer(round1Repeater.newChildId());
				item.setOutputMarkupId(true);
				round1Repeater.add(item);
				item.add(new Label("weekDate", Constants.DATE_FORMAT.format(new DateTime(2017, 4, 14, 0, 0, 0, 0).toDate())));
				item.add(new Label("frontNineTeeTime1", "***"));
				item.add(new Label("frontNineTeeTime2", "NO"));
				item.add(new Label("frontNineTeeTime3", "PLAY"));
				item.add(new Label("backNineTeeTime1", "EASTER"));
				item.add(new Label("backNineTeeTime2", "WEEKEND"));
				item.add(new Label("backNineTeeTime3", "***"));
			}
		}

		RepeatingView round2Repeater = new RepeatingView("round2");
		add(round2Repeater);
		weekList = new ArrayList<Week>(round2.getWeeks());
		Collections.sort(weekList);
		for (Week week : weekList) {
			WebMarkupContainer item;
			item = new WebMarkupContainer(round2Repeater.newChildId());
			item.setOutputMarkupId(true);
			matchDate = new DateTime(week.getDate());
			if (setCurrent && (!matchDate.isBeforeNow() || matchDate.isEqual(now))) {
				item.add(AttributeModifier.replace("class", "current"));
				setCurrent = false;
			}
			round2Repeater.add(item);

			item.add(new Label("weekDate", Constants.DATE_FORMAT.format(week.getDate())));
			item.add(new Label("frontNineTeeTime1", week.getFrontNineTeeTime1() != null ? week.getFrontNineTeeTime1().toString() : null));
			item.add(new Label("frontNineTeeTime2", week.getFrontNineTeeTime2() != null ? week.getFrontNineTeeTime2().toString() : null));
			item.add(new Label("frontNineTeeTime3", week.getFrontNineTeeTime3() != null ? week.getFrontNineTeeTime3().toString() : null));
			item.add(new Label("backNineTeeTime1", week.getBackNineTeeTime1() != null ? week.getBackNineTeeTime1().toString() : null));
			item.add(new Label("backNineTeeTime2", week.getBackNineTeeTime2() != null ? week.getBackNineTeeTime2().toString() : null));
			item.add(new Label("backNineTeeTime3", week.getBackNineTeeTime3() != null ? week.getBackNineTeeTime3().toString() : null));
			
			//Add League Outing label
			if (matchDate.isEqual(new DateTime(2017, 7, 7, 0, 0, 0, 0))) {
				item = new WebMarkupContainer(round1Repeater.newChildId());
				item.setOutputMarkupId(true);
				round2Repeater.add(item);
				item.add(new Label("weekDate", Constants.DATE_FORMAT.format(new DateTime(2017, 7, 8, 0, 0, 0, 0).toDate())));
				item.add(new Label("frontNineTeeTime1", "WOOD"));
				item.add(new Label("frontNineTeeTime2", "LAND"));
				item.add(new Label("frontNineTeeTime3", "HILLS"));
				item.add(new Label("backNineTeeTime1", "OUTING"));
				item.add(new Label("backNineTeeTime2", "8:00 AM"));
				item.add(new Label("backNineTeeTime3", "START"));
			}
		}

		add(new Label("playoffDate", Constants.DATE_FORMAT.format(matchDate.plusWeeks(1).toDate())));

		List<Team> teamStandings = rounds.get(0).getStandings(season, selectedWeek != null ? selectedWeek.getDate() : null);
		RepeatingView repeater1 = new RepeatingView("repeater1");
		for (Team team : teamStandings) {
			WebMarkupContainer container = new WebMarkupContainer(repeater1.newChildId());
			container.add(new Label("teamPlace", String.valueOf(team.getPlace()) + ". "));
			container.add(new Label("teamName", team.getName() + " (" + team.getTeamNumber() + ")"));
			container.add(new Label("teamPoints", String.valueOf(team.getPoints())));
			repeater1.add(container);
		}
		add(repeater1);

		teamStandings = rounds.get(1).getStandings(season, selectedWeek != null ? selectedWeek.getDate() : null);
		RepeatingView repeater2 = new RepeatingView("repeater2");
		for (Team team : teamStandings) {
			WebMarkupContainer container = new WebMarkupContainer(repeater2.newChildId());
			container.add(new Label("teamPlace", String.valueOf(team.getPlace()) + ". "));
			container.add(new Label("teamName", team.getName() + " (" + team.getTeamNumber() + ")"));
			container.add(new Label("teamPoints", String.valueOf(team.getPoints())));
			repeater2.add(container);
		}
		add(repeater2);

		RepeatingView lowNetRepeater = new RepeatingView("lowNetRepeater");
		for (Player player : season.getLowNet()) {
			WebMarkupContainer container = new WebMarkupContainer(lowNetRepeater.newChildId());
			container.add(new Label("lowNet", player.toString() + " - " + player.getLowScore()));
			lowNetRepeater.add(container);
		}
		add(lowNetRepeater);

		RepeatingView lowGrossRepeater = new RepeatingView("lowGrossRepeater");
		for (Player player : season.getLowGross()) {
			WebMarkupContainer container = new WebMarkupContainer(lowGrossRepeater.newChildId());
			container.add(new Label("lowGross", player.toString() + " - " + player.getLowScore()));
			lowGrossRepeater.add(container);
		}
		add(lowGrossRepeater);

		RepeatingView firstRoundIndividualRepeater = new RepeatingView("firstRoundIndividualRepeater");
		for (Player player : round1.getIndividualPoints()) {
			WebMarkupContainer container = new WebMarkupContainer(firstRoundIndividualRepeater.newChildId());
			container.add(new Label("individualPoints", player.toString() + " - " + player.getIndividualPoints()));
			firstRoundIndividualRepeater.add(container);
		}
		add(firstRoundIndividualRepeater);

		RepeatingView secondRoundIndividualRepeater = new RepeatingView("secondRoundIndividualRepeater");
		for (Player player : round2.getIndividualPoints()) {
			WebMarkupContainer container = new WebMarkupContainer(secondRoundIndividualRepeater.newChildId());
			container.add(new Label("individualPoints", player.toString() + " - " + player.getIndividualPoints()));
			secondRoundIndividualRepeater.add(container);
		}
		add(secondRoundIndividualRepeater);

		add(getViewSchedulePdfLink(season));
		add(getViewPdfLink(season));
		add(getPdfModal());
	}

	private ModalWindow getPdfModal() {
		if (pdfModal == null) {
			pdfModal = new ModalWindow("pdfModal");
			pdfModal.setResizable(true);
			pdfModal.setInitialWidth(800);
			pdfModal.setInitialHeight(600);
		}
		return pdfModal;
	}

	private ResourceLink<Object> getViewPdfLink(final Season season) {
		byte[] pdf = PdfExportUtils.buildWeeklyReport(season, selectedWeek, publicMessage);
		PopupSettings popupSettings = new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS).setHeight(600).setWidth(800);

		ByteArrayResource resource = new ByteArrayResource("application/pdf", pdf);

		ResourceLink<Object> resourceLink = new ResourceLink<Object>("viewPdf", resource);

		resourceLink.setPopupSettings(popupSettings);

		return resourceLink;
	}

	private ResourceLink<Object> getViewSchedulePdfLink(final Season season) {

		byte[] pdf = PdfExportUtils.buildScheduleReport(season);
		PopupSettings popupSettings = new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS).setHeight(600).setWidth(800);

		ByteArrayResource resource = new ByteArrayResource("application/pdf", pdf);

		ResourceLink<Object> resourceLink = new ResourceLink<Object>("viewSchedulePdf", resource);

		resourceLink.setPopupSettings(popupSettings);

		return resourceLink;
	}

	private DropDownChoice<Week> buildWeekDropDown(List<Week> weekChoices) {
		DropDownChoice<Week> component = new DropDownChoice<Week>("weekChoice", new PropertyModel<Week>(this, "selectedWeek"), weekChoices,
				getWeekRenderer());
		component.setOutputMarkupId(true);
		component.add(new FocusComponentBehavior());
		component.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				PageParameters parameters = new PageParameters();
				parameters.add("selectedWeek", selectedWeek.getId());
				setResponsePage(HomePage.class, parameters);
			}
		});
		return component;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	private IChoiceRenderer<Week> getWeekRenderer() {
		return new IChoiceRenderer<Week>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Week model) {
				return model.toString();
			}

			public String getIdValue(Week model, int index) {
				return model.getId().toString();
			}
		};
	}

	public Week getSelectedWeek() {
		return selectedWeek;
	}

	public void setSelectedWeek(Week selectedWeek) {
		this.selectedWeek = selectedWeek;
	}

	public void setApplicationPropertyService(ApplicationPropertyService applicationPropertyService) {
		this.applicationPropertyService = applicationPropertyService;
	}

}
