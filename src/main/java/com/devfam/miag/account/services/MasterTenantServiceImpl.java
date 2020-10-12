package com.devfam.miag.account.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devfam.miag.account.dao.DataSourceConfigRepository;
import com.devfam.miag.account.entities.DataSourceConfig;
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

}
