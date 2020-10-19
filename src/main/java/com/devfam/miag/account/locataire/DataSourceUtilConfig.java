package com.devfam.miag.account.locataire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataSourceUtilConfig {
	// Classe qui permettra de configurer les champs du datasource config a partir du fichier properties config

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceUtilConfig.class);
	
	
	private String username;
	private String password;
	private String driverClassName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	
	
	
}
