package com.devfam.miag.account.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devfam.miag.account.dao.DataSourceConfigRepository;
import com.devfam.miag.account.entities.DataSourceConfig;
import com.devfam.miag.account.locataire.DataSourceUtilConfig;
@Service
public class MasterTenantServiceImpl  implements MasterTenantService{
	
	@Autowired
	DataSourceConfigRepository dsCOnfigRepo;
	
	@Override
	public DataSourceConfig getByTenantId(String tenant) {
		// TODO Auto-generated method stub
		DataSourceConfig dataSource =dsCOnfigRepo.findByName(tenant);
		if(dataSource!=null) {
			return dataSource;
		}
		
		return null;
	}
	@Override
	public DataSourceConfig addNewLocataire(String tenantName,String url, DataSourceUtilConfig dsConfig) {
		// TODO Auto-generated method stub
		DataSourceConfig newTenant = new DataSourceConfig();
		DataSourceConfig tenantDb =this.getByTenantId(tenantName);
		if(tenantDb == null) {
			newTenant.setUsername(dsConfig.getUsername());
			newTenant.setDriverClassName(dsConfig.getDriverClassName());
			newTenant.setName(tenantName);
			newTenant.setPassword(dsConfig.getPassword());
			newTenant.setInitialize(false);
			return dsCOnfigRepo.save(newTenant);
		}
		
		
		return null;
	}

}
