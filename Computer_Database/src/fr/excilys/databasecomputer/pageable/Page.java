package fr.excilys.databasecomputer.pageable;

import java.util.Scanner;

import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;

public class Page {
	Scanner scPage = new Scanner(System.in);
	CompanyService company= new CompanyService();
	ComputerService computer = new ComputerService();
	
	private int limite =10;
	private int offset = 0;

	public void displayCompany() {
		int reponse = 1;
		int nbCompany = company.nbCompany();
		int maxPage = nbPageMax(nbCompany);

		do {
			company.displayAllCompany(this.limite,this.offset);
			System.out.println("Page " + reponse + " sur " + maxPage);
			reponse = pageInRange(maxPage);
			
		}while(reponse!=-1);
	}
	
	public void displayComputer() {
		int reponse = 1;
		int nbComputer = computer.nbComputer();
		int maxPage = nbPageMax(nbComputer);
		System.out.println(maxPage);
		do {
			computer.displayAllComputer(this.limite,this.offset);
			System.out.println("Page " + reponse + " sur " + maxPage);
			reponse = pageInRange(maxPage);
		}while(reponse!=-1);
	}
	
	private int nbPageMax(int nbobject) {		
		return (int)Math.ceil(((double)nbobject/(double)this.limite));
	}
	
	private void calculeNewOffset(int page) {
		this.offset = page* this.limite - this.limite;
		
	}
	
	private int verificationEntreUserInt() {
		int page;
		System.out.println("Sur quelle page voulez vous aller ?");
		System.out.println("Pour quitter marquer -1");
		while(true) {
			if (scPage.hasNextInt()) {
				page=scPage.nextInt();
				break;
			}
			System.out.println("Veillez recommencer, vous n'avez pas rentrer un nombre correcte");
			scPage.next();
			
		}
		return page;
	}
	
	private int pageInRange(int maxPage) {
		int reponse =0;
		do {
			reponse = verificationEntreUserInt();
			if(1 <= reponse && reponse <= maxPage) {
				calculeNewOffset(reponse);
				break;
			} else if (reponse==-1) {
				break;
			}else {
				System.out.println("Hors range");
			}
		}while(true);
		
		return reponse;
	}
	
}
