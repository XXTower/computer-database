package fr.excilys.databasecomputer.pageable;

import java.util.Scanner;

import fr.excilys.databasecomputer.controller.CompanyController;
import fr.excilys.databasecomputer.controller.ComputerController;

public class Page {
	Scanner scPage = new Scanner(System.in);
	CompanyController company= new CompanyController();
	ComputerController computer = new ComputerController();
	
	private int limite =10;
	private int offset = 0;

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}
	
	
	public void displayCompany() {
//		int reponse = 0;
		int nbCompany = company.nbCompany();
		int maxPage = nbPageMax(nbCompany);
		System.out.println(maxPage);
//		do {
			company.displayAllCompany(this.limite,this.offset);
//		}while(reponse!=-1);
	}
	
	public void displayComputer() {
//		int reponse = 0;
		int nbComputer = computer.nbComputer();
		int maxPage = nbPageMax(nbComputer);
		System.out.println(maxPage);
//		do {
			computer.displayAllComputer(this.limite,this.offset);
//		}while(reponse!=-1);
	}
	
	private int nbPageMax(int nbobject) {		
		return (int)Math.ceil(((double)nbobject/(double)this.limite));
	}
	
	private void calculeNewOffset(int page) {
		this.offset = page* this.limite - this.limite;
		
	}
	
}
