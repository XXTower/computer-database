package fr.excilys.databasecomputer.Main;

import java.util.Scanner;

import fr.excilys.databasecomputer.controller.ComputerController;
import fr.excilys.databasecomputer.pageable.Page;

public class Main{

	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		Page pagination = new Page();
		ComputerController computer = new ComputerController();
		
		
		char reponce = ' ' , option = ' ';
		do {
			System.out.println("Bonjour que vouslez-vous faire ?");
			
			do {
				System.out.println("1 - Liste Ordinateurs");
				System.out.println("2 - List Companies");
				System.out.println("3 - Voir detail d'un ordinateur");
				System.out.println("4 - Creer un ordinateur");
				System.out.println("5 - Mettre a jour un ordinateur");
				System.out.println("6 - Supprimmer un ordinateur");
				System.out.println("7 - Quitter");
				option = sc.nextLine().charAt(0);
				
				if(option != '1' && option != '2' && option != '3' && option != '4' && option != '5' && option != '6' && option != '7') {
					System.out.println("Option inconnu, veillez choisir une option correcte");
				}
			}while(option != '1' && option != '2' && option != '3' && option != '4' && option != '5' && option != '6' && option != '7');
			
			switch (option) {
			case '1':
				computer.displayAllComputer();
				break;
			case '2':
				pagination.displayCompany();
				break;
			case '3':
				computer.displayOneComputeur();
				break;
			case '4':
				computer.addComputer();
				break;
			case '5':
				computer.updateComputer();
				break;
			case '6':
				computer.deleteComputer();
				break;
			}
			
			do {
				System.out.println("Voulez-vous quiter ?");
				System.out.println("1 - Oui");
				System.out.println("2 - Non");
				reponce= sc.nextLine().charAt(0);
				
				if(reponce != '1' && reponce != '2') {
					System.out.println("Option inconnu, veillez choisir une option correcte");
				}
			}while(reponce!='1' && reponce!= '2');
			
		}while(reponce == '2');
		
		sc.close();		
		
	}

}
