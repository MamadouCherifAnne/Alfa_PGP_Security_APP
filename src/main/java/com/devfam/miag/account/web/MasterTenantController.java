package com.devfam.miag.account.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfam.miag.account.entities.DataSourceConfig;
import com.devfam.miag.account.locataire.DataSourceUtilConfig;
import com.devfam.miag.account.services.MasterTenantService;


@RestController
@RequestMapping("/locataire")
@CrossOrigin(origins="*")
public class MasterTenantController {
	@Autowired
	MasterTenantService masterTenantservice;
	@Autowired
	DataSourceUtilConfig dsConfig;
	
	@PostMapping(value = "/newLocataire")
	public String addNewTenant(@RequestBody DataSourceConfig tenantDS) {
		DataSourceConfig ds = null;
		ds =masterTenantservice.addNewLocataire(tenantDS.getName(),tenantDS.getUrl(),dsConfig);
		if(ds == null) {
			return "Le compte n'a pas pu etre creer verifier le service CLient";
			
		}
		return "Une espace de travail va etre configurer pour vous  Un email va vous etre envoye de notre Part veuillez patienter";
	}
	
}
