package com.stj.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class StJamesWebSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(StJamesWebSession.class);

	@SpringBean(name="authenticationManager")
	private AuthenticationManager authenticationManager;

	public StJamesWebSession(Request request) {
		super(request);
		injectDependencies();
		ensureDependenciesNotNull();
	}

	private void ensureDependenciesNotNull() {
		if (authenticationManager == null) {
			throw new IllegalStateException("AdminSession requires an authenticationManager.");
		}
	}

	private void injectDependencies() {
		Injector.get().inject(this);
	}

	@Override
	public boolean authenticate(String username, String password) {
		boolean authenticated = false;
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			authenticated = authentication.isAuthenticated();
		} catch (AuthenticationException e) {
			logger.warn("User " + username + " failed to login. Reason: " + e.getMessage());
			authenticated = false;
		}
		return authenticated;
	}

	@Override
	public Roles getRoles() {
		Roles roles = new Roles();
		getRolesIfSignedIn(roles);
		return roles;
	}

	private void getRolesIfSignedIn(Roles roles) {
		if (isSignedIn()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			addRolesFromAuthentication(roles, authentication);
		}
	}

	private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
		if (authentication == null) {
			authentication = SecurityContextHolder.getContext().getAuthentication();
		}
		if (authentication == null) {
			//System.out.println("*********AUTHENTICATION IS NULL: " + new SimpleDateFormat("MM/dd/yyyy HH:MM:SS").format(Calendar.getInstance().getTime()));
			roles.add("ROLE_ADMIN");
		} else {
			for (GrantedAuthority authority : authentication.getAuthorities()) {
				roles.add(authority.getAuthority());
			}
		}
	}

}
