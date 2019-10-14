package fr.excilys.databasecomputer.Main;

import java.time.LocalDate;
import java.util.Scanner;

import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

public class Main{
		
	public static void main(String[] args) {
//		ComputerService computerSrv = new ComputerService();
		Scanner sc = new Scanner(System.in);
		Page pagination = new Page();
		
		String reponce ="";
		System.out.println("Bonjour que vouslez-vous faire ?");
		do {
			afficherMenu();
			reponce=sc.nextLine().trim();
			switch (reponce) {
			case "1":
//				computerSrv.displayAllComputer();
				pagination.displayComputer();
				break;
			case "2":
				pagination.displayCompany();
				break;
			case "3":
				trouverComputer(sc);
				break;
			case "4":
				ajouterComputer(sc);
				break;
			case "5":
				majComputer(sc);
				break;
			case "6":
				supprimerComputer(sc);
				break;
			case "7":
				System.out.print("Au revoir");
				break;
			default:
				System.out.println("Option inconnu, veillez choisir une option correcte");
				break;
			}		
		}while(!reponce.equals("7"));
		
		sc.close();		
	}
	
	public static void afficherMenu() {
		System.out.println("1 - Liste Ordinateurs");
		System.out.println("2 - List Companies");
		System.out.println("3 - Voir detail d'un ordinateur");
		System.out.println("4 - Creer un ordinateur");
		System.out.println("5 - Mettre a jour un ordinateur");
		System.out.println("6 - Supprimmer un ordinateur");
		System.out.println("7 - Quitter");
	}
	
	public static void ajouterComputer(Scanner sc) {
		Validator valid = new Validator();
		ComputerBuilder newComputer = new ComputerBuilder();
		ComputerService computerSrv = new ComputerService();
		
		System.out.print("Nom (Obligatoire): ");
		newComputer.name(valid.verificationEntreUserString(sc));
			
		System.out.print("Date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateintroduced = valid.verificationEntreUserDate(sc);
		newComputer.introduced(dateintroduced);

		System.out.print("Date d'interruption : (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateinterruption = valid.verificationEntreUserDate(sc);
			
		valid.verificationDateIntervale(newComputer,dateintroduced, dateinterruption);
		
		System.out.println("Si aucun nom ne correspond, le cahmps sera null");
		System.out.print("Nouveaux nom company(Optionnel): ");
		newComputer.company(new CompanyBuilder().name(sc.nextLine()).build());
		
		if(computerSrv.addComputer(newComputer.build())) {
			System.out.println("Ordinateur ajouter");
		}else {
			System.out.println("Ordinateur non ajouter");
		}
	}
	
	public static void trouverComputer(Scanner sc) {
		ComputerService computerSrv = new ComputerService();
		Validator valid = new Validator();
		
		System.out.println("Entrez l'id de l'ordinateur souhaitez consulter");
		
		Computer computer = computerSrv.displayOneComputeur(valid.verificationEntreUserInt(sc));
		
		System.out.println(computer.toString());
	}
	
	public static void supprimerComputer(Scanner sc) {
		Validator valid = new Validator();
		ComputerService computerSrv = new ComputerService();
		
		System.out.println("Entrez l'id de l'ordinateur souhaitez supprimer");
		
		int idComputer= valid.verificationEntreUserInt(sc);
		
		if(computerSrv.deleteComputer(idComputer)) {
			System.out.println("Ordinateur supprimer");
		}else {
			System.out.println("Ordinateur non trouver ou opération compromise");
		}
	}
	
	public static void majComputer(Scanner sc) {
		Validator valid = new Validator();
		ComputerBuilder updateComputer = new ComputerBuilder();
		ComputerService computerSrv = new ComputerService();
		
		System.out.println("Entrez l'id de l'ordinateur souhaitez modifier");
		int idComputer= valid.verificationEntreUserInt(sc);
		
		Computer computer= computerSrv.displayOneComputeur(idComputer);
		
		System.out.println("Ancien nom: " + computer.getName());
		System.out.print("Nouveau nom (Obligatoire): ");
		updateComputer.name(valid.verificationEntreUserString(sc));
		
		System.out.println("Ancienne date d'introduction: " + computer.getIntroduced());
		System.out.print("Nouvelle date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateintroduced = valid.verificationEntreUserDate(sc);
		updateComputer.introduced(dateintroduced);
		
		System.out.println("Ancienne date d'interruption : " + computer.getDiscontinued());
		System.out.print("Nouvelle date d'interruption(Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateinterruption = valid.verificationEntreUserDate(sc);
			
		valid.verificationDateIntervale(updateComputer,dateintroduced, dateinterruption);
		
		System.out.println("Ancienne nom Company : " + computer.getCompany().getName());
		System.out.println("Si aucun nom ne correspond, le champs sera null");
		System.out.print("Nouveaux nom company(Optionnel): ");
		updateComputer.company(new CompanyBuilder().name(sc.nextLine()).build());
		
		if(computerSrv.updateComputer(updateComputer.build())) {
			System.out.println("Ordinateur modifier");
		}else {
			System.out.println("Ordinateur non modifier");
		}
	}

}
