package com.stj.services;

import java.util.List;

import com.stj.model.ApplicationProperty;

public interface ApplicationPropertyService {

	public List<ApplicationProperty> findAll();

	public ApplicationProperty find(String id);

	public ApplicationProperty save(ApplicationProperty property);

}
