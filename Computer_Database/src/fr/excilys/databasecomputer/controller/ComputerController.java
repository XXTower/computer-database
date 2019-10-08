package fr.excilys.databasecomputer.controller;

import java.util.ArrayList;
import java.util.Scanner;

import fr.excilys.databasecomputer.dao.ComputerDAOAbstract;
import fr.excilys.databasecomputer.dao.ConnextionDB;
import fr.excilys.databasecomputer.dao.entity.Computer;
import fr.excilys.databasecomputer.dao.implement.ComputerDAO;

public class ComputerController {
	
	public void displayAllComputer() {
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		ArrayList<Computer> computers = computerDAO.findAll();
		for(Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}
	
	public void displayOneComputeur() {
		Scanner sccomputer = new Scanner(System.in);
		int idComputer;
		System.out.println("Entrez l'id de l'ordinateur souhaitez");
		
		//Vérification valeur entrée  et un int 
		boolean testID = sccomputer.hasNextInt();
		while(true) {
			if (testID) {
				idComputer=sccomputer.nextInt();
				break;
			}
			System.out.print("Veillez recommencer, vous n'avez pas rentrer un id correcte");
			sccomputer.next();
			testID = sccomputer.hasNextInt();
		}
		ComputerDAOAbstract computerDAO = new ComputerDAO(ConnextionDB.getInstance());
		Computer computer= computerDAO.find(idComputer);
		System.out.println(computer.toString());
		
		sccomputer.close();
	}
}
