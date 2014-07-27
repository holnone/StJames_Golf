package com.stj.web.ui.shared;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.stj.web.ui.HomePage;

public class ScorecardPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public ScorecardPanel(String id) {
		super(id);
		Image theKnolls = new Image("theKnolls", new PackageResourceReference(HomePage.class, "res/KNOLLS_SCORECARD_FRONT.jpg"));
		theKnolls.add(AttributeModifier.replace("width", "800"));
		theKnolls.add(AttributeModifier.replace("height", "400"));
		add(theKnolls);
	}
}
