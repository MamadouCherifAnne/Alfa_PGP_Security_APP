package com.devfam.miag.account;

public class TenantContexte {

	 private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	 public  static String defaultTenant="alfapgpdefaultbd";
	 // Afficher le contexte locataire courant
	 public static String getCurrentTenant() {
		 return contextHolder.get();
	 }
	 //Modifier le contexte Locataire courant
	 public static void setCurrentTenant(String tenant) {
		contextHolder.set(tenant); 
	 }
	 
	 public static void clear() {
		 contextHolder.remove();
	 }
}
