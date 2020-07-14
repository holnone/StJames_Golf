package com.stj.web.wicket.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class YearRenderer implements IChoiceRenderer<Integer> {
    public Object getDisplayValue(Integer year) {
        return year.toString();
    }

    public String getIdValue(Integer year, int index) {
        return year.toString();
    }

    @Override
    public Integer getObject(String id, IModel<? extends List<? extends Integer>> choices) {
        for (Integer year : choices.getObject()) {
            if (year.toString().equals(id)) {
                return year;
            }
        }
        return null;
    }


}
