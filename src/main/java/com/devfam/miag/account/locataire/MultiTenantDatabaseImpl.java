package com.devfam.miag.account.locataire;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.devfam.miag.account.ConstantDataSource;
@Configuration

public class MultiTenantDatabaseImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl  {
	  @Autowired
	    private DataSource defaultDS;

	    @Autowired
	    private ApplicationContext context;

	    private Map<String, DataSource> map = new HashMap<>();

	    boolean init = false;

	    @PostConstruct
	    public void load() {
	        map.put(ConstantDataSource.DEFAULT_TENANT, defaultDS);
	    }

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(ConstantDataSource.DEFAULT_TENANT);
		
	}
	
	
	@Override
	public DataSource selectDataSource(String tenantIdentifier) {
		 if (!init) {
	            init = true;
	            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
	            map.putAll(tenantDataSource.getAll());
	            if(map.get(tenantIdentifier)!=null) {
	            System.out.println(" LES MAP DE SELECT DATASOURCE EST TJRS"+map.get(tenantIdentifier).toString());
	            return  map.get(tenantIdentifier);
	            }else {
	            System.out.println(" LES MAP DE SELECT DATASOURCE EST TJRS VIDE");
	            }
	           
	        }
		
	        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(ConstantDataSource.DEFAULT_TENANT);
	    }



}
