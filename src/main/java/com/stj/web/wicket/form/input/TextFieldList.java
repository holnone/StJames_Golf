package com.stj.web.wicket.form.input;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextFieldList<T extends Serializable> extends FormComponentPanel<List<T>> {
	private static final long serialVersionUID = 1L;

	private List<Model<T>> modelList;

	private RepeatingView repeater;

	private boolean ajaxUpdateBehavior;

	public TextFieldList(String id, Class<T> type, int numberOfComponents) {
		this(id, null, type, numberOfComponents);
	}

	public TextFieldList(String id, IModel<List<T>> model, Class<T> type, int numberOfComponents) {
		super(id, model);
		setType(type);
		setup(numberOfComponents);
	}

	public TextFieldList(String id, IModel<List<T>> model, int numberOfComponents, Class<T> type, boolean ajaxUpdateBehavior) {
		super(id, model);
		setType(type);
		this.ajaxUpdateBehavior = ajaxUpdateBehavior;
		setup(numberOfComponents);
	}

	private void setup(int numberOfComponents) {
		setRenderBodyOnly(false);
		modelList = new ArrayList<Model<T>>(numberOfComponents);
		repeater = new RepeatingView("textInput");
		add(repeater);

		for (int i = 0; i < numberOfComponents; i++) {
			Model<T> itemModel = new Model<T>();
			modelList.add(itemModel);
			final TextField<T> textField = new TextField<T>(repeater.newChildId(), itemModel);
			if (ajaxUpdateBehavior) {
				textField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

					private static final long serialVersionUID = -1676421820563450981L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						T obj = textField.getModelObject();
						if (obj != null && getValidObjects() != null) {
							if (getValidObjects().containsKey(obj.toString().toUpperCase())) {
								textField.getModel().setObject(getValidObjects().get(obj.toString().toUpperCase()));
							} else if (obj.toString().indexOf("*") != -1 || obj.toString().contains("*")) {
								textField.getModel().setObject(getNewObject(getValidObjects(), obj.toString()));
							} else {
								error(obj.toString() + " is invalid.");
							}

							refreshModalObject();
						} else {
							refreshModalObject();
						}
						modelUpdated(target);
						target.add(textField);
					}

					@SuppressWarnings("unchecked")
					private void refreshModalObject() {
						List<T> textModel = (List<T>) getModelObject();
						if (textModel != null) {
							textModel.clear();
						} else {
							textModel = new ArrayList<T>(getModelList().size());
							setModelObject(textModel);
						}
						for (Component child : getRepeater()) {
							if (((FormComponent) child).getModelObject() != null) {
								textModel.add((T) ((FormComponent) child).getModelObject());
							}
						}
					}

				});
			}
			repeater.add(textField);
		}
	}

	public Map<String, T> getValidObjects() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void convertInput() {
		ArrayList<T> outputList = new ArrayList<T>();
		for (Component child : getRepeater()) {
			T obj = (T) ((FormComponent) child).getConvertedInput();

			if (obj != null) {
				if (getValidObjects() != null && getValidObjects().containsKey(obj.toString().toUpperCase().trim())) {
					outputList.add(getValidObjects().get(obj.toString().toUpperCase().trim()));
				} else if (getValidObjects() != null && obj.toString().indexOf("*") != -1) {
					outputList.add(getNewObject(getValidObjects(), obj.toString()));
				} else if (getValidObjects() != null && obj instanceof String) {
					error(obj.toString() + " is invalid");
					return;
				} else {
					outputList.add(obj);
				}

			}

		}
		setConvertedInput(outputList);
	}

	@SuppressWarnings("unchecked")
	T getNewObject(Map<String, T> validObjs, String value) {
		T obj = null;
		for (String key : validObjs.keySet()) {
			obj = validObjs.get(key);
			break;
		}

		try {
			if (obj != null) {
				Class<T> cls = (Class<T>) Class.forName(obj.getClass().getName());
				Constructor<T> constructor = cls.getConstructor(String.class);
				return constructor.newInstance(value.toUpperCase());
			}
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onBeforeRender() {
		super.onBeforeRender();

		// clear the models
		for (Model model : modelList) {
			model.setObject(null);
		}

		List<T> modelObjects = (List<T>) getModelObject();
		if (modelObjects != null) {

			for (int i = 0; i < (modelObjects.size() < modelList.size() ? modelObjects.size() : modelList.size()); i++) {
				Model model = modelList.get(i);
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

	@SuppressWarnings("unchecked")
	public void setModelList(List<T> modelList) {
		int i = 0;
		for (Component child : getRepeater()) {
			if (i < modelList.size()) {
				((FormComponent) child).getModel().setObject(modelList.get(i));
			} else {
				((FormComponent) child).getModel().setObject(null);
			}
			i++;
		}
		List<T> textModel = (List<T>) getModelObject();
		if (textModel != null) {
			textModel.clear();
			textModel.addAll(modelList);
		} else {
			setModelObject(new ArrayList<T>(modelList));
		}

	}

	protected List<Model<T>> getModelList() {
		return modelList;
	}

	protected RepeatingView getRepeater() {
		return repeater;
	}

	public void modelUpdated(AjaxRequestTarget target) {
		// Override to handle onchange event
	}
}
