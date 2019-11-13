package fr.excilys.databasecomputer.Main;

import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;
import fr.excilys.databasecomputer.entity.Company.CompanyBuilder;
import fr.excilys.databasecomputer.configuration.CLIConfiguration;
import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.pageable.Page;
import fr.excilys.databasecomputer.service.CompanyService;
import fr.excilys.databasecomputer.service.ComputerService;
import fr.excilys.databasecomputer.validator.ValidatorCLI;
@Component
public class Main {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private static ValidatorCLI validator;
	@Autowired
	private Page page;
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new AnnotationConfigApplicationContext(CLIConfiguration.class);
		Main main = ctx.getBean(Main.class);
		Scanner sc = new Scanner(System.in);

		String reponce = "";
		System.out.println("Bonjour que voulez-vous faire ?");
		do {
			afficherMenu();
			reponce = sc.nextLine().trim();
			switch (reponce) {
			case "1":
				main.afficherComputer(sc);
				break;
			case "2":
				main.afficherCompany(sc);
				break;
			case "3":
				main.trouverComputer(sc);
				break;
			case "4":
				main.ajouterComputer(sc);
				break;
			case "5":
				main.majComputer(sc);
				break;
			case "6":
				main.supprimerComputer(sc);
				break;
			case "7":
				main.deleteComputer(sc);
				break;
			case "8":
				System.out.print("Au revoir");
				break;
			default:
				System.out.println("Option inconnu, veuillez choisir une option correcte");
				break;
			}
		} while (!reponce.equals("8"));
		sc.close();
	}

	public void afficherComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();
		int reponse = 1;
		int offset = 0;
		long nbComputer = computerService.nbComputer();
		int maxPage = page.nbPageMax(nbComputer);
		do {
			for (Computer computer : computerService.displayAllComputer(page.getLimite(), offset, "ASC")) {
				System.out.println(computer.toString());
			}
			System.out.println("Page " + reponse + " sur " + maxPage);
			System.out.println("Sur quelle page voulez vous aller ?");
			System.out.println("Pour quitter marqué -1");
			do {
				reponse = validator.verificationEntreUserInt(sc);
				if (1 <= reponse && reponse <= maxPage) {
					offset = page.calculeNewOffset(reponse);
					break;
				} else if (reponse == -1) {
					break;
				} else {
					System.out.println("Hors porter");
				}
			} while (true);
		} while (reponse != -1);
	}

	public void afficherCompany(Scanner sc) {
		validator = ValidatorCLI.getInstance();
		int reponse = 1;
		int offset = 0;
		long nbCompany = companyService.nbCompany();
		int maxPage = page.nbPageMax(nbCompany);
		do {
			for (Company company : companyService.displayAllCompany(page.getLimite(), offset)) {
				System.out.println(company.toString());
			}
			System.out.println("Page " + reponse + " sur " + maxPage);
			System.out.println("Sur quelle page voulez vous aller ?");
			System.out.println("Pour quitter marqué -1");
			do {
				reponse = validator.verificationEntreUserInt(sc);
				if (1 <= reponse && reponse <= maxPage) {
					offset = page.calculeNewOffset(reponse);
					break;
				} else if (reponse == -1) {
					break;
				} else {
					System.out.println("Hors range");
				}
			} while (true);
		} while (reponse != -1);
	}

	public static void afficherMenu() {
		System.out.println("1 - Liste Ordinateurs");
		System.out.println("2 - Liste Companies");
		System.out.println("3 - Voir detail d'un ordinateur");
		System.out.println("4 - Creer un ordinateur");
		System.out.println("5 - Mettre à jour un ordinateur");
		System.out.println("6 - Supprimer un ordinateur");
		System.out.println("7 - Supprimer une company et tout les ordinateur associer");
		System.out.println("8 - Quitter");
	}

	public  void ajouterComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();
		ComputerBuilder newComputer = new ComputerBuilder();

		System.out.print("Nom (Obligatoire): ");
		newComputer.name(validator.verificationEntreUserString(sc));

		System.out.print("Date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateintroduced = validator.verificationEntreUserDate(sc);
		newComputer.introduced(dateintroduced);

		System.out.print("Date d'interruption : (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateinterruption = validator.verificationEntreUserDate(sc);

		validator.verificationDateIntervale(newComputer, dateintroduced, dateinterruption);

		System.out.println("Si aucun nom ne correspond, le cahmps sera null");
		System.out.print("Nouveaux nom company(Optionnel): ");
		newComputer.company(new CompanyBuilder().name(sc.nextLine()).build());

		try {
			computerService.addComputer(newComputer.build());
			System.out.println("Ordinateur ajouter");
		} catch (FailSaveComputer e) {
			System.out.println("Ordinateur non ajouter");
		}
	}

	public void trouverComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();

		System.out.println("Entrez l'id de l'ordinateur souhaitez consulter");

		Computer computer;
		try {
			computer = computerService.displayOneComputeur(validator.verificationEntreUserInt(sc));
			System.out.println(computer.toString());
		} catch (SQLExceptionComputerNotFound e) {
			System.err.println(e);
		}
	}

	public void supprimerComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();

		System.out.println("Entrez l'id de l'ordinateur souhaitez supprimer");

		int idComputer = validator.verificationEntreUserInt(sc);

		if (computerService.deleteComputer(idComputer)) {
			System.out.println("Ordinateur supprimer");
		} else {
			System.out.println("Ordinateur non trouver ou opération compromise");
		}
	}

	public void majComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();
		ComputerBuilder updateComputer = new ComputerBuilder();

		System.out.println("Entrez l'id de l'ordinateur souhaitez modifier");
		int idComputer = validator.verificationEntreUserInt(sc);

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

			validator.verificationDateIntervale(updateComputer, dateintroduced, dateinterruption);

			System.out.println("Ancienne nom Company : " + computer.getCompany().getName());
			System.out.println("Si aucun nom ne correspond, le champs sera null");
			System.out.print("Nouveaux nom company(Optionnel): ");
			updateComputer.company(new CompanyBuilder().name(sc.nextLine()).build());

			try {
				computerService.updateComputer(updateComputer.build());
				System.out.println("Ordinateur modifier");
			} catch (FailSaveComputer e) {
				System.out.println("Ordinateur non modifier");
			}
		} catch (SQLExceptionComputerNotFound e) {
			System.err.println(e);
		}
	}

	public void deleteComputer(Scanner sc) {
		System.out.println("Reinseingner le nom de la companie que vous voulez supprimer");
		String companyName = sc.nextLine().trim();
		System.out.println(companyName);
		if (computerService.deleteComputerByCompanyName(companyName)) {
			companyService.deleteCopany(companyName);
			System.out.println("La companie et les ordinateur associer ont été supprimer");
		} else {
			System.out.println("Mauvvais nom reinseigner ou aucune companie trouver sous la nom reinseigner");
		}
	}
}
