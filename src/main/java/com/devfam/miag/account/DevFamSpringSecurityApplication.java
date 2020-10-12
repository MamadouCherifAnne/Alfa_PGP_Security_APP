package com.devfam.miag.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class DevFamSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevFamSpringSecurityApplication.class, args);
	}

}
