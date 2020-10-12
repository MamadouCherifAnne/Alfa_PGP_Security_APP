package com.devfam.miag.account.services;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devfam.miag.account.entities.Utilisateur;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UtilisateurService userService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Charger l'utilisateur qui souhaite se connecter a partir de la base de donnees
		Utilisateur user = userService.getUserByUsername(username);
		if(user == null) throw new UsernameNotFoundException("Impossible de trouver cet utilisateur");
		List<GrantedAuthority> permissions = new ArrayList<>();
		user.getRole().forEach(rol->{
			permissions.add(new SimpleGrantedAuthority(rol.getRole()));
		});
		
		return new User(user.getUsername(),user.getPassword(),permissions);
	}

}
