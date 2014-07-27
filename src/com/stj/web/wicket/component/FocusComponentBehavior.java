package com.stj.web.wicket.component;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class FocusComponentBehavior extends Behavior {
	private static final long serialVersionUID = 1L;

	private Component component;

	@Override
	public void bind(Component pComponent) {
		this.component = pComponent;
		this.component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse iHeaderResponse) {
		super.renderHead(component, iHeaderResponse);
		StringBuilder sb = new StringBuilder();
		sb.append("$(document).ready(function () {");
		sb.append("$('#");
		sb.append(component.getMarkupId());
		sb.append("').focus();");
		sb.append("});");
		iHeaderResponse.render(OnDomReadyHeaderItem.forScript(sb.toString()));
	}

	@Override
	public boolean isTemporary(Component component) {
		// remove the behavior after component has been rendered
		return true;
	}

}
