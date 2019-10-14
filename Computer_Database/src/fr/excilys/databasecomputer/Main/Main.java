package fr.excilys.databasecomputer.Main;

import java.time.LocalDate;
import java.util.Scanner;

import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.Validator;

public class Main{
	private static ComputerService computerService;
	private static CompanyService companyService;
	private static Validator validator;
	private static Page page;
	
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		String reponce ="";
		System.out.println("Bonjour que vouslez-vous faire ?");
		do {
			afficherMenu();
			reponce=sc.nextLine().trim();
			switch (reponce) {
			case "1":
				afficherComputer(sc);
				break;
			case "2":
				afficherCompany(sc);
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
				System.out.println("Option inconnu, veuillez choisir une option correcte");
				break;
			}		
		}while(!reponce.equals("7"));
		
		sc.close();		
	}
	
	public static void afficherComputer(Scanner sc) {
		page = Page.getInstance();
		computerService = ComputerService.getInstance();
		validator = Validator.getInstance();
		int reponse = 1;
		int offset = 0;
		int nbComputer = computerService.nbComputer();
		int maxPage = page.nbPageMax(nbComputer);
		do {
			computerService.displayAllComputer(page.getLimite(),offset);
			System.out.println("Page " + reponse + " sur " + maxPage);
			System.out.println("Sur quelle page voulez vous aller ?");
			System.out.println("Pour quitter marquer -1");
			do {
				reponse = validator.verificationEntreUserInt(sc);
				if(1 <= reponse && reponse <= maxPage) {
					offset = page.calculeNewOffset(reponse);
					break;
				} else if (reponse==-1) {
					break;
				}else {
					System.out.println("Hors range");
				}
			}while(true);			
		}while(reponse!=-1);
	}
	
	public static void afficherCompany(Scanner sc) {
		page = Page.getInstance();
		companyService = CompanyService.getInstance();
		validator = Validator.getInstance();
		int reponse = 1;
		int offset = 0;
		int nbCompany = companyService.nbCompany();
		int maxPage = page.nbPageMax(nbCompany);
		do {
			companyService.displayAllCompany(page.getLimite(),offset);
			System.out.println("Page " + reponse + " sur " + maxPage);
			System.out.println("Sur quelle page voulez vous aller ?");
			System.out.println("Pour quitter marquer -1");
			do {
				reponse = validator.verificationEntreUserInt(sc);
				if(1 <= reponse && reponse <= maxPage) {
					offset = page.calculeNewOffset(reponse);
					break;
				} else if (reponse==-1) {
					break;
				}else {
					System.out.println("Hors range");
				}
			}while(true);			
		}while(reponse!=-1);
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
		validator = Validator.getInstance();
		ComputerBuilder newComputer = new ComputerBuilder();
		computerService = ComputerService.getInstance();
		
		System.out.print("Nom (Obligatoire): ");
		newComputer.name(validator.verificationEntreUserString(sc));
			
		System.out.print("Date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateintroduced = validator.verificationEntreUserDate(sc);
		newComputer.introduced(dateintroduced);

		System.out.print("Date d'interruption : (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateinterruption = validator.verificationEntreUserDate(sc);
			
		validator.verificationDateIntervale(newComputer,dateintroduced, dateinterruption);
		
		System.out.println("Si aucun nom ne correspond, le cahmps sera null");
		System.out.print("Nouveaux nom company(Optionnel): ");
		newComputer.company(new CompanyBuilder().name(sc.nextLine()).build());
		
		if(computerService.addComputer(newComputer.build())) {
			System.out.println("Ordinateur ajouter");
		}else {
			System.out.println("Ordinateur non ajouter");
		}
	}
	
	public static void trouverComputer(Scanner sc) {
		computerService = ComputerService.getInstance();
		validator = Validator.getInstance();
		
		System.out.println("Entrez l'id de l'ordinateur souhaitez consulter");
		
		Computer computer;
		try {
			computer = computerService.displayOneComputeur(validator.verificationEntreUserInt(sc));
			System.out.println(computer.toString());
		} catch (SQLExceptionComputerNotFound e) {
			System.err.println(e);
		}
	}
	
	public static void supprimerComputer(Scanner sc) {
		validator = Validator.getInstance();
		computerService = ComputerService.getInstance();
		
		System.out.println("Entrez l'id de l'ordinateur souhaitez supprimer");
		
		int idComputer= validator.verificationEntreUserInt(sc);
		
		if(computerService.deleteComputer(idComputer)) {
			System.out.println("Ordinateur supprimer");
		}else {
			System.out.println("Ordinateur non trouver ou opération compromise");
		}
	}
	
	public static void majComputer(Scanner sc){
		validator = Validator.getInstance();
		ComputerBuilder updateComputer = new ComputerBuilder();
		computerService = ComputerService.getInstance();
		
		System.out.println("Entrez l'id de l'ordinateur souhaitez modifier");
		int idComputer= validator.verificationEntreUserInt(sc);
		
		Computer computer;
		try {
			computer = computerService.displayOneComputeur(idComputer);
		
			updateComputer.id(computer.getId());
			System.out.println("Ancien nom: " + computer.getName());
			System.out.print("Nouveau nom (Obligatoire): ");
			updateComputer.name(validator.verificationEntreUserString(sc));
			
			System.out.println("Ancienne date d'introduction: " + computer.getIntroduced());
			System.out.print("Nouvelle date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
			LocalDate dateintroduced = validator.verificationEntreUserDate(sc);
			updateComputer.introduced(dateintroduced);
			
			System.out.println("Ancienne date d'interruption : " + computer.getDiscontinued());
			System.out.print("Nouvelle date d'interruption(Optionnel, format: AAAAA-MM-JJ): ");
			LocalDate dateinterruption = validator.verificationEntreUserDate(sc);
				
			validator.verificationDateIntervale(updateComputer,dateintroduced, dateinterruption);
			
			System.out.println("Ancienne nom Company : " + computer.getCompany().getName());
			System.out.println("Si aucun nom ne correspond, le champs sera null");
			System.out.print("Nouveaux nom company(Optionnel): ");
			updateComputer.company(new CompanyBuilder().name(sc.nextLine()).build());
			
			if(computerService.updateComputer(updateComputer.build())) {
				System.out.println("Ordinateur modifier");
			}else {
				System.out.println("Ordinateur non modifier");
			}
		} catch (SQLExceptionComputerNotFound e) {
			System.err.println(e);
		}
	}


}
