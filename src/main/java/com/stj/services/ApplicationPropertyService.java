package com.stj.services;

import com.stj.model.ApplicationProperty;

import java.util.List;

public interface ApplicationPropertyService {

	public List<ApplicationProperty> findAll();

	public ApplicationProperty find(String id);

	public ApplicationProperty save(ApplicationProperty property);

}
