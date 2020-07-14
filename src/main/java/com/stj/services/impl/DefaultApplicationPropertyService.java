package com.stj.services.impl;

import com.stj.model.ApplicationProperty;
import com.stj.repo.ApplicationPropertyRepository;
import com.stj.services.ApplicationPropertyService;

import java.util.List;

public class DefaultApplicationPropertyService implements ApplicationPropertyService {

	private ApplicationPropertyRepository applicationPropertyRepository;

	public ApplicationProperty find(String id) {
		return applicationPropertyRepository.findById(id);
	}

	public List<ApplicationProperty> findAll() {
		return applicationPropertyRepository.findAll();
	}

	public ApplicationProperty save(ApplicationProperty property) {
		return applicationPropertyRepository.saveOrUpdate(property);
	}

	public void setApplicationPropertyRepository(ApplicationPropertyRepository applicationPropertyRepository) {
		this.applicationPropertyRepository = applicationPropertyRepository;
	}

}
