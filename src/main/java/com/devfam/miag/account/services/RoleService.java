package  com.devfam.miag.account.services;


import java.util.List;

import com.devfam.miag.account.entities.Role;

public interface RoleService {
	
	// Ajout de role
	public Role addRole(Role role);
	//Modification de role
	public Role updateRole(Role role);

	// Suppression de role
	public boolean deleteRole(Long id);
	 //Afficher tout les roles
	public List<Role> getAll();
	//fin a role by role
	public Role findByRole(String role);



}
