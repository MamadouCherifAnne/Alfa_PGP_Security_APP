package com.devfam.miag.account.services;

import com.devfam.miag.account.entities.DataSourceConfig;

public interface MasterTenantService {
	
	public DataSourceConfig getByTenantId(String tenant);

}
