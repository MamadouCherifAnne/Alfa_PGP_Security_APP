package com.devfam.miag.account.services;

import com.devfam.miag.account.entities.DataSourceConfig;
import com.devfam.miag.account.locataire.DataSourceUtilConfig;

public interface MasterTenantService {
	
	public DataSourceConfig getByTenantId(String tenant);
	
	// Ajouter un nouveau client
	public DataSourceConfig addNewLocataire(String tenantName,String url, DataSourceUtilConfig dsConfig);

}
