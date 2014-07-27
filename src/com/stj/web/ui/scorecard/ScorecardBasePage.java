package com.stj.web.ui.scorecard;

import com.stj.web.ui.base.BasePage;
import com.stj.web.ui.shared.ScorecardPanel;

public class ScorecardBasePage extends BasePage {

	public ScorecardBasePage() {
		add(new ScorecardPanel("scoreCardPanel"));
	}
}
