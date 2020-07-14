package com.stj.web.wicket.component;

import com.stj.web.ui.HomePage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.SharedResourceReference;

public class IconLinkPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public IconLinkPanel(String id, String iconURL, AjaxLink<String> ajaxLink) {
		super(id);
		setRenderBodyOnly(true);
		Image icon = new Image("icon", new SharedResourceReference(HomePage.class, iconURL));
		ajaxLink.add(new Component[] { icon });
		add(new Component[] { ajaxLink });
	}
}
