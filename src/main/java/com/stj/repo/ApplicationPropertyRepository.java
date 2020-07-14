package com.stj.repo;

import com.stj.model.ApplicationProperty;

import java.util.List;

public interface ApplicationPropertyRepository {
	ApplicationProperty saveOrUpdate(ApplicationProperty property);

	ApplicationProperty findById(String id);

	List<ApplicationProperty> findAll();

}
