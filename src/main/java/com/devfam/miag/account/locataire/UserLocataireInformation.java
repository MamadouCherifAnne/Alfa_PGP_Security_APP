package com.devfam.miag.account.locataire;

import java.util.HashMap;
import java.util.Map;

public class UserLocataireInformation {


	    private Map<String, String> map = new HashMap<>();

	    public UserLocataireInformation() {
	    }

	    public Map<String, String> getMap() {
	        return map;
	    }

	    public UserLocataireInformation setMap(Map<String, String> map) {
	        this.map = map;
	        return this;
	    }
}
