package com.stj.web.wicket.form.input;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class LabelList extends FormComponentPanel<List<String>> {
	private static final long serialVersionUID = 1L;

	private List<Model<String>> modelList;

	private RepeatingView repeater;

	private boolean ajaxUpdateBehavior;

	public LabelList(String id, IModel<List<String>> model, int numberOfComponents) {
		super(id, model);
		setup(numberOfComponents);
	}

	private void setup(int numberOfComponents) {
		setRenderBodyOnly(false);
		modelList = new ArrayList<Model<String>>(numberOfComponents);
		repeater = new RepeatingView("listLabel");
		add(repeater);

		for (int i = 0; i < numberOfComponents; i++) {
			Model<String> itemModel = new Model<String>();
			modelList.add(itemModel);
			final Label label = new Label(repeater.newChildId(), itemModel);
			if (ajaxUpdateBehavior) {
				label.add(new AjaxFormComponentUpdatingBehavior("onchange") {

					private static final long serialVersionUID = -1676421820563450981L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						refreshModalObject();
						target.add(label);
					}

					private void refreshModalObject() {
						List<String> textModel = (List<String>) getModelObject();
						if (textModel != null) {
							textModel.clear();
						} else {
							textModel = new ArrayList<String>(getModelList().size());
							setModelObject(textModel);
						}
						for (Component child : getRepeater()) {
							if (child.getDefaultModelObject() != null) {
								textModel.add((String) child.getDefaultModelObject());
							}
						}
					}

				});
			}
			repeater.add(label);
		}
	}

	@Override
	protected void convertInput() {
		ArrayList<String> outputList = new ArrayList<String>();
		for (Component child : getRepeater()) {
			String obj = (String) child.getDefaultModelObject();

			if (obj != null) {
				outputList.add(obj);
			}

		}
		setConvertedInput(outputList);
	}

	@SuppressWarnings("unchecked")
	String getNewObject(Map<String, String> validObjs, String value) {
		String obj = null;
		for (String key : validObjs.keySet()) {
			obj = validObjs.get(key);
			break;
		}

		try {
			if (obj != null) {
				Class<String> cls = (Class<String>) Class.forName(obj.getClass().getName());
				Constructor<String> constructor = cls.getConstructor(String.class);
				return constructor.newInstance(value.toUpperCase());
			}
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();

		// clear the models
		for (Model<String> model : modelList) {
			model.setObject(null);
		}

		List<String> modelObjects = (List<String>) getModelObject();
		if (modelObjects != null) {

			for (int i = 0; i < (modelObjects.size() < modelList.size() ? modelObjects.size() : modelList.size()); i++) {
				Model<String> model = modelList.get(i);
				model.setObject(modelObjects.get(i));

			}
		}
	}

	@Override
	public Component add(Behavior... behavior) {
		for (Component child : getRepeater()) {
			child.add(behavior);
		}
		return this;
	}

	public void add(int childIndex, Behavior behavior) {
		int idx = 0;
		for (Component child : getRepeater()) {
			if (idx == childIndex) {
				child.add(behavior);
				break;
			}
			idx++;
		}
	}

	public void setModelList(List<String> modelList) {
		int i = 0;
		for (Component child : getRepeater()) {
			if (i < modelList.size()) {
				child.setDefaultModelObject(modelList.get(i));
			} else {
				child.setDefaultModelObject(null);
			}
			i++;
		}
		List<String> textModel = (List<String>) getModelObject();
		if (textModel != null) {
			textModel.clear();
			textModel.addAll(modelList);
		} else {
			setModelObject(new ArrayList<String>(modelList));
		}

	}

	protected List<Model<String>> getModelList() {
		return modelList;
	}

	protected RepeatingView getRepeater() {
		return repeater;
	}

}
