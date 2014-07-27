package com.stj.repo;

import java.util.List;

import com.stj.model.ApplicationProperty;

public interface ApplicationPropertyRepository {
	ApplicationProperty saveOrUpdate(ApplicationProperty property);

	ApplicationProperty findById(String id);

	List<ApplicationProperty> findAll();

}
