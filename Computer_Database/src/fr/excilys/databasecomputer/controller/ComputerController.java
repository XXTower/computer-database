package fr.excilys.databasecomputer.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.excilys.databasecomputer.dao.ComputerDAOAbstract;
import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.dao.entity.Company;
import fr.excilys.databasecomputer.dao.entity.Computer;
import fr.excilys.databasecomputer.dao.implement.ComputerDAO;
import fr.excilys.databasecomputer.warpper.DateWapper;

public class ComputerController {
	Scanner scComputer = new Scanner(System.in);
	
	public void addComputer() {
		boolean result;
		Computer computer= new Computer();
		System.out.print("Nom (Obligatoire): ");
		computer.setName(verificationEntreUserString());
			
		System.out.print("Date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateintroduced = verificationEntreUserDate();
		computer.setIntroduced(DateWapper.changeToSQLDate(dateintroduced));

		System.out.print("Date d'interruption : (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateinterruption = verificationEntreUserDate();
			
		verificationDateIntervale(computer,dateintroduced, dateinterruption);
		
		System.out.println("Si aucun nom ne correspond, le cahmps sera null");
		System.out.print("Nouveaux nom company(Optionnel): ");
		computer.setCompany(new Company(0, scComputer.nextLine()));
		
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		result= computerDAO.addComputer(computer);
		if(result) {
			System.out.println("Ordinateur ajouter");
		}else {
			System.out.println("Ordinateur non ajouter");
		}
	}
	
 	public void displayAllComputer() {
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		ArrayList<Computer> computers = computerDAO.findAll();
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}
	
	public void displayOneComputeur() {
		System.out.println("Entrez l'id de l'ordinateur souhaitez consulter");
		
		//Vérification valeur entrée  et un int 
		int idComputer= verificationEntreUserInt();
		
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		Computer computer= computerDAO.find(idComputer);
		System.out.println(computer.toString());

	}
	
	public void deleteComputer() {
		boolean result;
		System.out.println("Entrez l'id de l'ordinateur souhaitez supprimer");
		
		//Vérification valeur entrée  et un int 
		int idComputer= verificationEntreUserInt();
		
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		result= computerDAO.delete(idComputer);
		if(result) {
			System.out.println("Ordinateur supprimer");
		}else {
			System.out.println("Ordinateur non trouver ou opération compromise");
		}
	}
	
	public void updateComputer() {
		boolean result;
		System.out.println("Entrez l'id de l'ordinateur souhaitez modifier");
		
		//Vérification valeur entrée  et un int 
		int idComputer= verificationEntreUserInt();
		
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		Computer computer= computerDAO.find(idComputer);
	
		System.out.println("Ancien nom: " + computer.getName());
		System.out.print("Nouveau nom (Obligatoire): ");
		computer.setName(verificationEntreUserString());
			
		System.out.println("Ancienne date d'introduction: " + computer.getIntroduced());
		System.out.print("Nouvelle date d'introduction (Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateintroduced = verificationEntreUserDate();
		computer.setIntroduced(DateWapper.changeToSQLDate(dateintroduced));
			
		System.out.println("Ancienne date d'interruption : " + computer.getDiscontinued());
		System.out.print("Nouvelle date d'interruption(Optionnel, format: AAAAA-MM-JJ): ");
		LocalDate dateinterruption = verificationEntreUserDate();
			
		verificationDateIntervale(computer,dateintroduced, dateinterruption);
		
		System.out.println("Ancienne nom Company : " + computer.getCompany().getName());
		System.out.println("Si aucun nom ne correspond, le champs sera null");
		System.out.print("Nouveaux nom company(Optionnel): ");
		computer.getCompany().setName(scComputer.nextLine());
		
		ComputerDAOAbstract computerDAO2 = new ComputerDAO(ConnextionDB.getInstance());
		result= computerDAO2.update(computer);
		if(result) {
			System.out.println("Ordinateur modifier");
		}else {
			System.out.println("Ordinateur non modifier");
		}
		
	}
	
	private int verificationEntreUserInt() {
		int idComputer;
		
		boolean testID = scComputer.hasNextInt();
		while(true) {
			if (testID) {
				idComputer=scComputer.nextInt();
				break;
			}
			System.out.println("Veillez recommencer, vous n'avez pas rentrer un id correcte");
			scComputer.next();
			testID = scComputer.hasNextInt();
		}
		return idComputer;
	}
	
	private String verificationEntreUserString() {
		String name=null;
		
		name = scComputer.nextLine();
		while(true) {	
			if (name.length()!=0) {
				break;
			}
			System.out.println("Veillez recommencer, vous n'avez pas rentrer un nom correcte");
			name = scComputer.nextLine().trim();
		}
		return name;
	}
	
	private LocalDate verificationEntreUserDate() {
		String date;
		boolean verifRegex;
		date = scComputer.nextLine().trim();
		verifRegex = Pattern.matches("[1-2][0-9]{3}-(0[1-9]|1[1-2])-(0[1-9]|[1-2][0-9]|3[0-1])",date);
		while(!verifRegex) {	
			if (date.length()==0) {
				return null;
			}else if(date.length()!=0 && Pattern.matches("[1-9]{4}-(0[1-9]|1[1-2])-(0[1-9]|[1-2][0-9]|3[0-1])",date)) {
				break;
			}
			System.out.println("Veillez recommencer, vous n'avez pas rentrer une date correcte");
			date = scComputer.nextLine().trim();
		}
		return DateWapper.changeToLocalDate(date);
		
		
	}
	
	private void verificationDateIntervale (Computer computer, LocalDate discontinuedDate, LocalDate dateinterruption) {
		if ((!(discontinuedDate==null)) && (!(dateinterruption==null))) {
			
		    long diff = discontinuedDate.until(dateinterruption, ChronoUnit.DAYS);
		    if(diff<0) {
		    	System.out.println("La date d'interruption doit etre supérieur a la date d'introduction, la date vas donc etre initialiser a null");
		    	System.out.println("Veillez remodifier l'ordinateur est entrez une date correcte après avoir terminer");
		    	computer.setDiscontinued(null);
		    }else {
				computer.setDiscontinued(DateWapper.changeToSQLDate(dateinterruption));
			}
		}
	}
}
