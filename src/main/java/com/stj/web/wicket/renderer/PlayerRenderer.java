package com.stj.web.wicket.renderer;

import com.stj.model.Player;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class PlayerRenderer implements IChoiceRenderer<Player> {
    public Object getDisplayValue(Player model) {
        return model.toString();
    }

    public String getIdValue(Player model, int index) {
        return model.getId().toString();
    }

    @Override
    public Player getObject(String id, IModel<? extends List<? extends Player>> choices) {
        for (Player player : choices.getObject()) {
            if (player.getId().toString().equals(id)) {
                return player;
            }
        }
        return null;
    }


}
