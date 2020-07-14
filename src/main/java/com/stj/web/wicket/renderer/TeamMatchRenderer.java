package com.stj.web.wicket.renderer;

import com.stj.model.Team;
import com.stj.model.TeamMatch;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class TeamMatchRenderer implements IChoiceRenderer<TeamMatch> {
    public Object getDisplayValue(TeamMatch model) {
        return model.toString();
    }

    public String getIdValue(TeamMatch model, int index) {
        return model.getId().toString();
    }

    @Override
    public TeamMatch getObject(String id, IModel<? extends List<? extends TeamMatch>> choices) {
        for (TeamMatch teamMatch : choices.getObject()) {
            if (teamMatch.getId().toString().equals(id)) {
                return teamMatch;
            }
        }
        return null;
    }


}
