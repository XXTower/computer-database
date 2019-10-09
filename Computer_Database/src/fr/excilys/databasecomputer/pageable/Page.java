package fr.excilys.databasecomputer.pageable;

import fr.excilys.databasecomputer.controller.CompanyController;

public class Page {
	CompanyController company= new CompanyController();
	private int limite =10;

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}
	
	
	public void displayCompany() {
		int reponse = 0;
		int maxPage = nbPageMaxCompany();
		do {
			company.displayAllCompany(10);
		}while(reponse!=-1);
	}
	
	private int nbPageMaxCompany() {
		int nbCompany = company.nbCompany();
		
		return 0;
	}
	
}
