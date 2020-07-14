package com.stj.web.wicket.renderer;

import com.stj.model.Week;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class WeekRenderer implements IChoiceRenderer<Week> {
    public Object getDisplayValue(Week model) {
        return model.toString();
    }

    public String getIdValue(Week model, int index) {
        return model.getId().toString();
    }

    @Override
    public Week getObject(String id, IModel<? extends List<? extends Week>> choices) {
        for (Week week : choices.getObject()) {
            if (week.getId().toString().equals(id)) {
                return week;
            }
        }
        return null;
    }


}
