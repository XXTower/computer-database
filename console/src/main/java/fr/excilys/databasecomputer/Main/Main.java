package fr.excilys.databasecomputer.Main;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.dtos.ComputerDTO;
import fr.excilys.databasecomputer.restclient.RestClient;
import fr.excilys.databasecomputer.validator.ValidatorCLI;

@Component
public class Main {
	private static ValidatorCLI validator;
	private static RestClient client;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		client = new RestClient();
		String reponce = "";
		System.out.println("Bonjour que voulez-vous faire ?");
		do {
			afficherMenu();
			reponce = sc.nextLine().trim();
			switch (reponce) {
			case "1":
				afficherComputer();
				break;
			case "2":
				afficherCompany();
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
//				main.supprimerComputer(sc);
				break;
			case "7":
//				main.deleteComputer(sc);
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

	public static void afficherComputer() {
		for (ComputerDTO computerDTO : client.getAllComputers()) {
			System.out.println(computerDTO);
		}
	}

	public static void afficherCompany() {
		for (CompanyDTO companyDTO : client.getAllCompanys()) {
			System.out.println(companyDTO);
		}
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

	public static void ajouterComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();
		ComputerDTO newComputer = new ComputerDTO();
		CompanyDTO companyDTO = new CompanyDTO();

		System.out.print("Nom (Obligatoire): ");
		newComputer.setName(validator.verificationEntreUserString(sc));

		System.out.print("Date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		String dateintroduced = validator.verificationEntreUserDate(sc);
		newComputer.setIntroduced(dateintroduced);

		System.out.print("Date d'interruption : (Optionnel, format: AAAAA-MM-JJ): ");
		String datediscontinued = validator.verificationEntreUserDate(sc);
		newComputer.setDiscontinued(datediscontinued);

		System.out.println("Si zero est reinseingner le champs sera null");
		System.out.print("Nouveaux id company(Optionnel): ");
		int id = validator.verificationEntreUserInt(sc);
		if (id != 0) {
			companyDTO.setId(id);
			newComputer.setCompanyDTO(companyDTO);
		}

		if (client.create(newComputer)) {
			System.out.println("Ordinateur ajouter");
		} else {
			System.out.println("Ordinateur non ajouter");
		}
	}

	public static void trouverComputer(Scanner sc) {
		ComputerDTO computerDTO = new ComputerDTO();
		System.out.println("Entrez l'id de l'ordinateur souhaitez consulter");
		computerDTO = client.getComputerById(sc.next());
		System.out.println(computerDTO);
	}

	public void supprimerComputer(Scanner sc) {
//		validator = ValidatorCLI.getInstance();
//
//		System.out.println("Entrez l'id de l'ordinateur souhaitez supprimer");
//
//		int idComputer = validator.verificationEntreUserInt(sc);
//
//		if (computerService.deleteComputer(idComputer)) {
//			System.out.println("Ordinateur supprimer");
//		} else {
//			System.out.println("Ordinateur non trouver ou opération compromise");
//		}
	}

	public static void majComputer(Scanner sc) {
		validator = ValidatorCLI.getInstance();
		ComputerDTO computerDTO = new ComputerDTO();
		CompanyDTO companyDTO = new CompanyDTO();
		System.out.println("Entrez l'id de l'ordinateur souhaitez modifier");

		computerDTO = client.getComputerById(sc.next());

		System.out.println("Ancien nom: " + computerDTO.getName());
		System.out.print("Nouveau nom (Obligatoire): ");
		computerDTO.setName(validator.verificationEntreUserString(sc));

		System.out.println("Ancienne date d'introduction: " + computerDTO.getIntroduced());
		System.out.print("Nouvelle date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		String dateintroduced = validator.verificationEntreUserDate(sc);
		computerDTO.setIntroduced(dateintroduced);

		System.out.println("Ancienne date d'interruption : " + computerDTO.getDiscontinued());
		System.out.print("Nouvelle date d'interruption(Optionnel, format: AAAAA-MM-JJ): ");
		String dateinterruption = validator.verificationEntreUserDate(sc);
		computerDTO.setDiscontinued(dateinterruption);

		System.out.println("Ancienne nom Company : " + computerDTO.getCompanyDTO());
		System.out.println("Si zero est reinseingner le champs sera null");
		System.out.print("Nouveaux id company(Optionnel): ");
		int id = validator.verificationEntreUserInt(sc);
		if (id != 0) {
			companyDTO.setId(id);
			computerDTO.setCompanyDTO(companyDTO);
			computerDTO.setName("");
		}
		if (client.update(computerDTO)) {
			System.out.println("Ordinateur modifier");
		} else {
			System.out.println("Ordinateur non modifier");
		}
	}

	public void deleteComputer(Scanner sc) {
//		System.out.println("Reinseingner le nom de la companie que vous voulez supprimer");
//		String companyName = sc.nextLine().trim();
//		System.out.println(companyName);
//		if (computerService.deleteComputerByCompanyName(companyName)) {
//			companyService.deleteCopany(companyName);
//			System.out.println("La companie et les ordinateur associer ont été supprimer");
//		} else {
//			System.out.println("Mauvvais nom reinseigner ou aucune companie trouver sous la nom reinseigner");
//		}
	}
}
