package com.stj.web.ui.admin.scorecard;

import com.stj.web.ui.admin.base.AdminBasePage;
import com.stj.web.ui.shared.ScorecardPanel;

public class ScorecardPage extends AdminBasePage {

	private static final long serialVersionUID = 1L;

	public ScorecardPage() {
		ScorecardPanel panel = new ScorecardPanel("scoreCardPanel");
		add(panel);
	}
}
