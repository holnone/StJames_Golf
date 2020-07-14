package com.stj.web.ui.admin.schedule;

import com.stj.model.Team;
import com.stj.model.Week;
import com.stj.services.LeagueService;
import com.stj.util.Constants;
import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.wicket.component.FocusComponentBehavior;
import com.stj.web.wicket.renderer.TeamRenderer;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditWeekPage extends AdminBasePage {
    private static final long serialVersionUID = 1L;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    private Week week = new Week();

    private final static String FRONT_NINE_TEE_TIME1_TEAM1 = "frontNineTeeTime1_Team1";
    private final static String FRONT_NINE_TEE_TIME1_TEAM2 = "frontNineTeeTime1_Team2";
    private final static String FRONT_NINE_TEE_TIME2_TEAM1 = "frontNineTeeTime2_Team1";
    private final static String FRONT_NINE_TEE_TIME2_TEAM2 = "frontNineTeeTime2_Team2";
    private static final String FRONT_NINE_TEE_TIME3_TEAM1 = "frontNineTeeTime3_Team1";
    private static final String FRONT_NINE_TEE_TIME3_TEAM2 = "frontNineTeeTime3_Team2";
    private final static String BACK_NINE_TEE_TIME1_TEAM1 = "backNineTeeTime1_Team1";
    private final static String BACK_NINE_TEE_TIME1_TEAM2 = "backNineTeeTime1_Team2";
    private final static String BACK_NINE_TEE_TIME2_TEAM1 = "backNineTeeTime2_Team1";
    private final static String BACK_NINE_TEE_TIME2_TEAM2 = "backNineTeeTime2_Team2";
    private static final String BACK_NINE_TEE_TIME3_TEAM1 = "backNineTeeTime3_Team1";
    private static final String BACK_NINE_TEE_TIME3_TEAM2 = "backNineTeeTime3_Team2";

    @SpringBean
    private LeagueService leagueService;

    private List<Team> choices = new ArrayList<Team>();

    private Form<Object> form;

    public EditWeekPage(Week week) {
        this.week = week;
        choices = leagueService.getAllTeams();
        renderContent();
    }

    private void renderContent() {
        form = new Form<Object>("form");
        form.setOutputMarkupId(true);
        form.add(new Label("weekDate", DATE_FORMAT.format(week.getDate())));

        form.add(new Label("frontNineTeeTime1Group1", Constants.TEE_TIME_1_GROUP_1));
        form.add(new Label("frontNineTeeTime1Group2", Constants.TEE_TIME_1_GROUP_2));
        form.add(new Label("frontNineTeeTime2Group1", Constants.TEE_TIME_2_GROUP_1));
        form.add(new Label("frontNineTeeTime2Group2", Constants.TEE_TIME_2_GROUP_2));
        form.add(new Label("frontNineTeeTime3Group1", Constants.TEE_TIME_3_GROUP_1));
        form.add(new Label("frontNineTeeTime3Group2", Constants.TEE_TIME_3_GROUP_2));

        form.add(new Label("backNineTeeTime1Group1", Constants.TEE_TIME_1_GROUP_1));
        form.add(new Label("backNineTeeTime1Group2", Constants.TEE_TIME_1_GROUP_2));
        form.add(new Label("backNineTeeTime2Group1", Constants.TEE_TIME_2_GROUP_1));
        form.add(new Label("backNineTeeTime2Group2", Constants.TEE_TIME_2_GROUP_2));
        form.add(new Label("backNineTeeTime3Group1", Constants.TEE_TIME_3_GROUP_1));
        form.add(new Label("backNineTeeTime3Group2", Constants.TEE_TIME_3_GROUP_2));

        form.add(buildDropDown(FRONT_NINE_TEE_TIME1_TEAM1, "frontNineTeeTime1.team1.team").add(new FocusComponentBehavior()));
        form.add(buildDropDown(FRONT_NINE_TEE_TIME1_TEAM2, "frontNineTeeTime1.team2.team"));
        form.add(buildDropDown(FRONT_NINE_TEE_TIME2_TEAM1, "frontNineTeeTime2.team1.team"));
        form.add(buildDropDown(FRONT_NINE_TEE_TIME2_TEAM2, "frontNineTeeTime2.team2.team"));
        form.add(buildDropDown("frontNineTeeTime3_Team1", "frontNineTeeTime3.team1.team"));
        form.add(buildDropDown("frontNineTeeTime3_Team2", "frontNineTeeTime3.team2.team"));
        form.add(buildDropDown(BACK_NINE_TEE_TIME1_TEAM1, "backNineTeeTime1.team1.team"));
        form.add(buildDropDown(BACK_NINE_TEE_TIME1_TEAM2, "backNineTeeTime1.team2.team"));
        form.add(buildDropDown(BACK_NINE_TEE_TIME2_TEAM1, "backNineTeeTime2.team1.team"));
        form.add(buildDropDown(BACK_NINE_TEE_TIME2_TEAM2, "backNineTeeTime2.team2.team"));
        form.add(buildDropDown("backNineTeeTime3_Team1", "backNineTeeTime3.team1.team"));
        form.add(buildDropDown("backNineTeeTime3_Team2", "backNineTeeTime3.team2.team"));

        form.add(getSaveButton().setOutputMarkupId(true));
        form.add(getCancelButton().setOutputMarkupId(true));
        add(form);
    }

    private Component buildDropDown(final String id, String property) {
        final DropDownChoice<Team> component = new DropDownChoice<Team>(id, new PropertyModel<Team>(this.week, property), choices, new TeamRenderer());
        component.setOutputMarkupId(true);
        component.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Team team = component.getModel().getObject();
                if (team != null) {
                    if (!FRONT_NINE_TEE_TIME1_TEAM1.equals(id)) {
                        if (week.getFrontNineTeeTime1() != null && week.getFrontNineTeeTime1().getTeam1() != null) {
                            if (team.equals(week.getFrontNineTeeTime1().getTeam1().getTeam())) {
                                week.getFrontNineTeeTime1().getTeam1().setTeam(null);
                                target.add(get("form:" + FRONT_NINE_TEE_TIME1_TEAM1));
                                return;
                            }
                        }
                    }
                    if (!FRONT_NINE_TEE_TIME1_TEAM2.equals(id)) {
                        if (week.getFrontNineTeeTime1() != null && week.getFrontNineTeeTime1().getTeam2() != null) {
                            if (team.equals(week.getFrontNineTeeTime1().getTeam2().getTeam())) {
                                week.getFrontNineTeeTime1().getTeam2().setTeam(null);
                                target.add(get("form:" + FRONT_NINE_TEE_TIME1_TEAM2));
                                return;
                            }
                        }
                    }
                    if (!FRONT_NINE_TEE_TIME2_TEAM1.equals(id)) {
                        if (week.getFrontNineTeeTime2() != null && week.getFrontNineTeeTime2().getTeam1() != null) {
                            if (team.equals(week.getFrontNineTeeTime2().getTeam1().getTeam())) {
                                week.getFrontNineTeeTime2().getTeam1().setTeam(null);
                                target.add(get("form:" + FRONT_NINE_TEE_TIME2_TEAM1));
                                return;
                            }
                        }
                    }
                    if (!FRONT_NINE_TEE_TIME2_TEAM2.equals(id)) {
                        if (week.getFrontNineTeeTime2() != null && week.getFrontNineTeeTime2().getTeam2() != null) {
                            if (team.equals(week.getFrontNineTeeTime2().getTeam2().getTeam())) {
                                week.getFrontNineTeeTime2().getTeam2().setTeam(null);
                                Component comp = get("form:" + FRONT_NINE_TEE_TIME2_TEAM2);
                                target.add(comp);
                                return;
                            }
                        }
                    }
                    if (!FRONT_NINE_TEE_TIME3_TEAM1.equals(id) && week.getFrontNineTeeTime3() != null
                            && week.getFrontNineTeeTime3().getTeam1() != null && team.equals(week.getFrontNineTeeTime3().getTeam1().getTeam())) {
                        week.getFrontNineTeeTime3().getTeam1().setTeam(null);
                        target.add(get("form:" + FRONT_NINE_TEE_TIME3_TEAM1));
                        return;
                    }
                    if (!FRONT_NINE_TEE_TIME3_TEAM2.equals(id) && week.getFrontNineTeeTime3() != null
                            && week.getFrontNineTeeTime3().getTeam2() != null && team.equals(week.getFrontNineTeeTime3().getTeam2().getTeam())) {
                        week.getFrontNineTeeTime3().getTeam2().setTeam(null);
                        Component comp = get("form:" + FRONT_NINE_TEE_TIME3_TEAM2);
                        target.add(comp);
                        return;
                    }
                    if (!BACK_NINE_TEE_TIME1_TEAM1.equals(id)) {
                        if (week.getBackNineTeeTime1() != null && week.getBackNineTeeTime1().getTeam1() != null) {
                            if (team.equals(week.getBackNineTeeTime1().getTeam1().getTeam())) {
                                week.getBackNineTeeTime1().getTeam1().setTeam(null);
                                target.add(get("form:" + BACK_NINE_TEE_TIME1_TEAM1));
                                return;
                            }
                        }
                    }
                    if (!BACK_NINE_TEE_TIME1_TEAM2.equals(id)) {
                        if (week.getBackNineTeeTime1() != null && week.getBackNineTeeTime1().getTeam2() != null) {
                            if (team.equals(week.getBackNineTeeTime1().getTeam2().getTeam())) {
                                week.getBackNineTeeTime1().getTeam2().setTeam(null);
                                target.add(get("form:" + BACK_NINE_TEE_TIME1_TEAM2));
                                return;
                            }
                        }
                    }
                    if (!BACK_NINE_TEE_TIME2_TEAM1.equals(id)) {
                        if (week.getBackNineTeeTime2() != null && week.getBackNineTeeTime2().getTeam1() != null) {
                            if (team.equals(week.getBackNineTeeTime2().getTeam1().getTeam())) {
                                week.getBackNineTeeTime2().getTeam1().setTeam(null);
                                target.add(get("form:" + BACK_NINE_TEE_TIME2_TEAM1));
                                return;
                            }
                        }
                    }
                    if (!BACK_NINE_TEE_TIME2_TEAM2.equals(id)) {
                        if (week.getBackNineTeeTime2() != null && week.getBackNineTeeTime2().getTeam2() != null) {
                            if (team.equals(week.getBackNineTeeTime2().getTeam2().getTeam())) {
                                week.getBackNineTeeTime2().getTeam2().setTeam(null);
                                target.add(get("form:" + BACK_NINE_TEE_TIME2_TEAM2));
                                return;
                            }
                        }
                    }
                    if (!BACK_NINE_TEE_TIME3_TEAM1.equals(id) && week.getBackNineTeeTime3() != null && week.getBackNineTeeTime3().getTeam1() != null
                            && team.equals(week.getBackNineTeeTime3().getTeam1().getTeam())) {
                        week.getBackNineTeeTime3().getTeam1().setTeam(null);
                        target.add(get("form:" + BACK_NINE_TEE_TIME3_TEAM1));
                        return;
                    }
                    if (!BACK_NINE_TEE_TIME3_TEAM2.equals(id) && week.getBackNineTeeTime3() != null && week.getBackNineTeeTime3().getTeam2() != null
                            && team.equals(week.getBackNineTeeTime3().getTeam2().getTeam())) {
                        week.getBackNineTeeTime3().getTeam2().setTeam(null);
                        target.add(get("form:" + BACK_NINE_TEE_TIME3_TEAM2));
                        return;
                    }
                }
            }

        });
        return component;
    }

    private Component getSaveButton() {
        return new AjaxButton("saveButton") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                showFeedback(target);
                if (!validateWeek()) {
                    week = leagueService.saveWeek(week);
                    getSession().info("Week Saved Successfully.");
                    setResponsePage(SchedulePage.class);
                }
            }
        }.setMarkupId("saveButton");
    }

    private Component getCancelButton() {
        AjaxButton cancelButton = new AjaxButton("cancelButton") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                setResponsePage(SchedulePage.class);
            }
        };
        cancelButton.setDefaultFormProcessing(false);
        return cancelButton;
    }

    boolean validateWeek() {
        boolean hasErrors = false;
        if (week.getDate() == null) {
            error("Please enter a Date.");
            hasErrors = true;
        }
        if (week.getFrontNineTeeTime1().getTeam1().getTeam() == null || week.getFrontNineTeeTime1().getTeam2().getTeam() == null) {
            error("Please select both teams for Front Nine - 4:56 & 5:04");
            hasErrors = true;
        }
        if (week.getFrontNineTeeTime2().getTeam1().getTeam() == null || week.getFrontNineTeeTime2().getTeam2().getTeam() == null) {
            error("Please select both teams for Front Nine - 5:12 & 5:20");
            hasErrors = true;
        }
        if (week.getFrontNineTeeTime3().getTeam1().getTeam() == null || week.getFrontNineTeeTime3().getTeam2().getTeam() == null) {
            error("Please select both teams for Front Nine - 5:28 & 5:36");
            hasErrors = true;
        }
        if (week.getBackNineTeeTime1().getTeam1().getTeam() == null || week.getBackNineTeeTime1().getTeam2().getTeam() == null) {
            error("Please select both teams for Back Nine - 4:56 & 5:04");
            hasErrors = true;
        }
        if (week.getBackNineTeeTime2().getTeam1().getTeam() == null || week.getBackNineTeeTime2().getTeam2().getTeam() == null) {
            error("Please select both teams for Back Nine - 5:12 & 5:20");
            hasErrors = true;
        }
        if (week.getBackNineTeeTime3().getTeam1().getTeam() == null || week.getBackNineTeeTime3().getTeam2().getTeam() == null) {
            error("Please select both teams for Back Nine - 5:28 & 5:36");
            hasErrors = true;
        }

        return hasErrors;
    }

    public void setLeagueService(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

}
