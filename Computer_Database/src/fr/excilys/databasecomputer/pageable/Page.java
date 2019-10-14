package fr.excilys.databasecomputer.pageable;

import java.util.Scanner;

import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;

public class Page {
	Scanner scPage = new Scanner(System.in);
	CompanyService company= new CompanyService();
	ComputerService computer = new ComputerService();
	
	private int limite =10;

	public int getLimite() {
		return limite;
	}
	
	public int nbPageMax(int nbobject) {		
		return (int)Math.ceil(((double)nbobject/(double)this.limite));
	}
	
	public int calculeNewOffset(int page) {
		return page* this.limite - this.limite;
		
	}
	
}
