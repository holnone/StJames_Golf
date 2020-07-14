package com.stj.web.wicket.renderer;

import com.stj.model.Side;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class SideRenderer implements IChoiceRenderer<Side> {
    public Object getDisplayValue(Side model) {
        return model.toString();
    }

    public String getIdValue(Side model, int index) {
        return model.getId().toString();
    }

    @Override
    public Side getObject(String id, IModel<? extends List<? extends Side>> choices) {
        for (Side side : choices.getObject()) {
            if (side.getId().toString().equals(id)) {
                return side;
            }
        }
        return null;
    }


}
