package com.stj.web.wicket.renderer;

import com.stj.model.Team;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class TeamRenderer implements IChoiceRenderer<Team> {
    public Object getDisplayValue(Team model) {
        return model.toString();
    }

    public String getIdValue(Team model, int index) {
        return model.getId().toString();
    }

    @Override
    public Team getObject(String id, IModel<? extends List<? extends Team>> choices) {
        for (Team team : choices.getObject()) {
            if (team.getId().toString().equals(id)) {
                return team;
            }
        }
        return null;
    }


}
