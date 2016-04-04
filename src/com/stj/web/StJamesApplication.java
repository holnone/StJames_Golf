package com.stj.web;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.core.request.handler.BookmarkableListenerInterfaceRequestHandler;
import org.apache.wicket.core.request.handler.ListenerInterfaceRequestHandler;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.stj.security.StJamesWebSession;
import com.stj.web.ui.HomePage;
import com.stj.web.ui.LoginPage;
import com.stj.web.ui.admin.SettingsPage;
import com.stj.web.ui.admin.roster.InactivePlayersPage;
import com.stj.web.ui.admin.roster.RosterPage;
import com.stj.web.ui.admin.schedule.SchedulePage;
import com.stj.web.ui.admin.scorecard.ScorecardPage;
import com.stj.web.ui.admin.scores.EditScoresPage;
import com.stj.web.ui.admin.scores.ScoresPage;
import com.stj.web.ui.contact.ContactUsPage;
import com.stj.web.ui.couples.CouplesPage;
import com.stj.web.ui.messageboard.MessageBoardPage;
import com.stj.web.ui.roster.PlayerDetailPage;
import com.stj.web.ui.roster.SkinsPage;
import com.stj.web.ui.scorecard.ScorecardBasePage;
import com.stj.web.ui.system.page.PageNotFoundPage;
import com.stj.web.ui.team.TeamPage;
import com.stj.web.webservice.ExternalDataPage;

public class StJamesApplication extends AuthenticatedWebApplication implements ApplicationContextAware {
	private ApplicationContext context;

	boolean isInitialized = false;

	@Override
	protected void init() {
		if (!isInitialized) {
			super.init();
			isInitialized = true;
			mount(new MountedMapperWithoutPageComponentInfo("/home", HomePage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/login", LoginPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/roster", com.stj.web.ui.roster.RosterPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/scores", com.stj.web.ui.scores.ScoresPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/contactUs", ContactUsPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/admin", SchedulePage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/admin/roster", RosterPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/admin/inactivePlayers", InactivePlayersPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/admin/scores", ScoresPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/admin/schedule", SchedulePage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/admin/settings", SettingsPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/team", TeamPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/couples", CouplesPage.class));
			//mount(new MountedMapperWithoutPageComponentInfo("/skins", SkinsPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/messageBoard", MessageBoardPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/editScoresPage", EditScoresPage.class));
			mount(new MountedMapperWithoutPageComponentInfo("/playerDetail", PlayerDetailPage.class));

			// WebService pages
			mount(new MountedMapperWithoutPageComponentInfo("/externalData", ExternalDataPage.class));

			// System pages
			mount(new MountedMapperWithoutPageComponentInfo("/pageNotFound", PageNotFoundPage.class));
			getDebugSettings().setAjaxDebugModeEnabled(false);

			getRequestCycleListeners().add(new MyRequestCycle());

			getComponentInstantiationListeners().add(new SpringComponentInjector(this));
			
		}
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return StJamesWebSession.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	class NoHybridMountedMapper extends MountedMapper {
		public NoHybridMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass) {
			super(mountPath, pageClass);
		}

		@Override
		protected void encodePageComponentInfo(Url url, PageComponentInfo info) {
			final boolean isComponentListenter = info != null && info.getComponentInfo() != null;
			if (isComponentListenter) {
				super.encodePageComponentInfo(url, info);
			}
		}
	}

	class MountedMapperWithoutPageComponentInfo extends MountedMapper {

		public MountedMapperWithoutPageComponentInfo(String mountPath, Class<? extends IRequestablePage> pageClass) {
			super(mountPath, pageClass, new PageParametersEncoder());
		}

		@Override
		public Url mapHandler(IRequestHandler requestHandler) {
			if (requestHandler instanceof ListenerInterfaceRequestHandler || requestHandler instanceof BookmarkableListenerInterfaceRequestHandler) {
				return null;
			}
			return super.mapHandler(requestHandler);
		}

		@Override
		protected void encodePageComponentInfo(Url url, PageComponentInfo info) {
			// do nothing so that component info does not get rendered in url
		}

	}
}
