package com.stj.web.ui.admin.roster;

import com.stj.model.Player;
import com.stj.model.Team;
import com.stj.services.LeagueService;
import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.wicket.component.FocusComponentBehavior;
import com.stj.web.wicket.renderer.PlayerRenderer;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditTeamPage extends AdminBasePage {
    private static final long serialVersionUID = 1L;

    private Team team = new Team();

    @SpringBean
    private LeagueService leagueService;

    private List<Player> selectedOriginals = new ArrayList<Player>();
    private List<Player> selectedDestinations = new ArrayList<Player>();
    private List<Player> originalChoices = new ArrayList<Player>();
    private List<Player> destinationChoices = new ArrayList<Player>();

    ListMultipleChoice<Player> originals;
    ListMultipleChoice<Player> destinations;

    public EditTeamPage(Team team) {
        this.team = team;
        this.originalChoices = leagueService.getAllPlayers();
        this.destinationChoices = team.getSortedPlayers();
        this.originalChoices.removeAll(destinationChoices);
        Collections.sort(originalChoices);
        renderContent();
    }

    private void renderContent() {
        Form<Object> form = new Form<Object>("form");
        form.add(new TextField<Integer>("teamNumberText", new PropertyModel<Integer>(this.team, "teamNumber")).add(new FocusComponentBehavior()));
        form.add(new TextField<String>("teamNameText", new PropertyModel<String>(this.team, "name")));
        originals = new ListMultipleChoice<Player>("originals", new PropertyModel(this, "selectedOriginals"), new ListModel<Player>(
                this.originalChoices), new PlayerRenderer());
        originals.setOutputMarkupId(true);
        destinations = new ListMultipleChoice<Player>("destinations", new PropertyModel(this, "selectedDestinations"), new ListModel<Player>(
                this.destinationChoices), new PlayerRenderer());
        destinations.setOutputMarkupId(true);
        AjaxButton add = new AjaxButton("add") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                update(target, selectedOriginals, originals, destinations);
            }
        };

        AjaxButton remove = new AjaxButton("remove") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                update(target, selectedDestinations, destinations, originals);
            }
        };
        form.add(originals);
        form.add(destinations);
        form.add(add);
        form.add(remove);
        form.add(getNewPlayerLink());
        form.add(getSaveButton());
        form.add(getCancelButton());
        add(form);
    }

    private Component getNewPlayerLink() {
        AjaxLink<String> ajaxLink = new AjaxLink<String>("newPlayerLink") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                showFeedback(target);
                setResponsePage(new EditPlayerPage(EditTeamPage.this, new Player()));
            }

        };
        return ajaxLink;
    }

    private void update(AjaxRequestTarget target, List<Player> selections, ListMultipleChoice<Player> from, ListMultipleChoice<Player> to) {
        for (Player player : selections) {
            List<Player> choices = getChoices(from);
            if (!to.getChoices().contains(player)) {
                ((List<Player>) to.getChoices()).add(player);
                choices.remove(player);
                from.setChoices(choices);
            }
        }
        Collections.sort(to.getChoices());
        target.add(to);
        target.add(from);
    }

    private List<Player> getChoices(ListMultipleChoice<Player> choice) {
        List<Player> choices = new ArrayList<Player>();
        choices.addAll(choice.getChoices());
        return choices;
    }

    private Component getSaveButton() {
        return new AjaxButton("saveButton") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                showFeedback(target);
                if (team.getTeamNumber() == null || team.getName() == null) {
                    error("Please enter both a Team Number and a Team Name.");
                } else {
                    team.getPlayers().clear();
                    team.getPlayers().addAll(destinations.getChoices());
                    team = leagueService.saveTeam(team);
                    getSession().info("Team Saved Successfully.");
                    setResponsePage(RosterPage.class);
                }
            }
        }.setMarkupId("saveButton");
    }

    private Component getCancelButton() {
        AjaxButton cancelButton = new AjaxButton("cancelButton") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                setResponsePage(RosterPage.class);
            }
        };
        cancelButton.setDefaultFormProcessing(false);
        return cancelButton;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setLeagueService(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    public void setSelectedOriginals(List<Player> selectedOriginals) {
        this.selectedOriginals = selectedOriginals;
    }

    public void setSelectedDestinations(List<Player> selectedDestinations) {
        this.selectedDestinations = selectedDestinations;
    }

    public void setOriginals(ListMultipleChoice<Player> originals) {
        this.originals = originals;
    }

    public void setDestinations(ListMultipleChoice<Player> destinations) {
        this.destinations = destinations;
    }

    public void setOriginalChoices(List<Player> originalChoices) {
        this.originalChoices = originalChoices;
    }

    public void setDestinationChoices(List<Player> destinationChoices) {
        this.destinationChoices = destinationChoices;
    }
}
