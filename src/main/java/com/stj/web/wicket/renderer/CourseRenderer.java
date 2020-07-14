package com.stj.web.wicket.renderer;

import com.stj.model.Course;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class CourseRenderer implements IChoiceRenderer<Course> {
    public Object getDisplayValue(Course model) {
        return model.toString();
    }

    public String getIdValue(Course model, int index) {
        return model.getId().toString();
    }

    @Override
    public Course getObject(String id, IModel<? extends List<? extends Course>> choices) {
        for (Course course : choices.getObject()) {
            if (course.getId().toString().equals(id)) {
                return course;
            }
        }
        return null;
    }


}
