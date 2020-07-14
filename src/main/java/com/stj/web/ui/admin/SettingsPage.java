package com.stj.web.ui.admin;

import com.stj.model.ApplicationProperty;
import com.stj.services.ApplicationPropertyService;
import com.stj.util.Constants;
import com.stj.web.ui.admin.base.AdminBasePage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class SettingsPage extends AdminBasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ApplicationPropertyService applicationPropertyService;

	private String publicMessage;

	public SettingsPage() {
		ApplicationProperty property = applicationPropertyService.find(Constants.PUBLIC_MESSAGE);
		if (property != null) {
			this.publicMessage = property.getValue();
		}
		Form<Object> form = new Form<Object>("form");
		form.add(new TextArea<String>("publicMessage", new PropertyModel<String>(this, "publicMessage")));
		form.add(getSaveButton());
		add(form);
	}

	private Component getSaveButton() {
		return new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				showFeedback(target);
				ApplicationProperty property = applicationPropertyService.find(Constants.PUBLIC_MESSAGE);
				if (property != null) {
					property.setValue(getPublicMessage());
				} else {
					property = new ApplicationProperty(Constants.PUBLIC_MESSAGE, getPublicMessage());
				}
				applicationPropertyService.save(property);
				getSession().info("Message Saved Successfully.");
			}
		}.setMarkupId("saveButton");
	}

	public void setApplicationPropertyService(ApplicationPropertyService applicationPropertyService) {
		this.applicationPropertyService = applicationPropertyService;
	}

	public String getPublicMessage() {
		return publicMessage;
	}

	public void setPublicMessage(String publicMessage) {
		this.publicMessage = publicMessage;
	}
}
