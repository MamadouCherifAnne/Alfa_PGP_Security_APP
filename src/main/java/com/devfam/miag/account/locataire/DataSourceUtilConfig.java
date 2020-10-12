package com.devfam.miag.account.locataire;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devfam.miag.account.entities.DataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceUtilConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceUtilConfig.class);

    public static DataSource createAndConfigureDataSource(DataSourceConfig masterTenant) {
    	  HikariDataSource ds = new HikariDataSource();
          ds.setUsername(masterTenant.getUsername());
          ds.setPassword(masterTenant.getPassword());
          ds.setJdbcUrl(masterTenant.getUrl());
          ds.setDriverClassName(masterTenant.getDriverClassName());
          ds.setConnectionTimeout(20000);
         
          ds.setMinimumIdle(3);
         
          ds.setMaximumPoolSize(500);
          
          ds.setIdleTimeout(300000);
          
          ds.setConnectionTimeout(20000);
          // Setting up a pool name for each tenant datasource
          String tenantConnectionPoolName = masterTenant.getName() + "-connection-pool";
          ds.setPoolName(tenantConnectionPoolName);
          LOGGER.info("Configured datasource:" + masterTenant.getName() + ". Connection pool name:" + tenantConnectionPoolName);
          return ds;
    }
}
