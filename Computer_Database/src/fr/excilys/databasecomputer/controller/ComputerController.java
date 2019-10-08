package fr.excilys.databasecomputer.controller;

import java.util.ArrayList;
import java.util.Scanner;

import fr.excilys.databasecomputer.dao.ComputerDAOAbstract;
import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.dao.entity.Computer;
import fr.excilys.databasecomputer.dao.implement.ComputerDAO;

public class ComputerController {
	Scanner scComputer = new Scanner(System.in);
	
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
		System.out.println("Entrez l'id de l'ordinateur souhaitez modifier");
		
		//Vérification valeur entrée  et un int 
		int idComputer= verificationEntreUserInt();
		
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		Computer computer= computerDAO.find(idComputer);
		try(Scanner scNewComputer = new Scanner(System.in)){
			System.out.println("Ancien nom: " + computer.getName());
			System.out.print("Nouveau nom (Obligatoire): ");
			computer.setName(verificationEntreUserString());
			
			System.out.println("Ancienne date d'introduction: " + computer.getName());
			System.out.print("Nouvelle date d'introduction (Optionnel): ");
			computer.setName(verificationEntreUserString());
			
			System.out.println("Ancien nom: " + computer.getName());
			System.out.print("Nouveau nom (Optionnel): ");
			computer.setName(verificationEntreUserString());
			
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
}
