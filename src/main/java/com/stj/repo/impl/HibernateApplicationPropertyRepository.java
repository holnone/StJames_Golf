package com.stj.repo.impl;

import com.stj.model.ApplicationProperty;
import com.stj.repo.ApplicationPropertyRepository;
import com.stj.repo.BaseHibernateRepository;

public class HibernateApplicationPropertyRepository extends BaseHibernateRepository<ApplicationProperty, String> implements
		ApplicationPropertyRepository {

	@Override
	protected Class<ApplicationProperty> getEntityClass() {
		return ApplicationProperty.class;
	}

}