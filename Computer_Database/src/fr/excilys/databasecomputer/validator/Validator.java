package fr.excilys.databasecomputer.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.excilys.databasecomputer.Mapper.DateMapper;
import fr.excilys.databasecomputer.entity.Computer.ComputerBuilder;

public class Validator {
	private static Validator instance;
	
	private Validator() {}
	
	public static Validator getInstance() {
		if(instance==null) {
			instance = new Validator();
		}
		return instance;
	}
	
	public int verificationEntreUserInt(Scanner scComputer) {
		int idComputer;
		while(true) {
			if (scComputer.hasNextInt()) {
				idComputer=scComputer.nextInt();
				break;
			}
			System.out.println("Veillez recommencer, vous n'avez pas rentrer un nombre correcte");
			scComputer.nextInt();
		}
		return idComputer;
	}
	
	public String verificationEntreUserString(Scanner scComputer) {
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
	
	public LocalDate verificationEntreUserDate(Scanner scComputer) {
		String date;
		boolean verifRegex;
		date = scComputer.nextLine().trim();
		verifRegex = Pattern.matches("[1-2][0-9]{3}-(0[1-9]|1[1-2])-(0[1-9]|[1-2][0-9]|3[0-1])",date);
		while(!verifRegex) {	
			if (date.length()==0) {
				return null;
			}else if(date.length()!=0 && Pattern.matches("[1-2][0-9]{3}-(0[1-9]|1[1-2])-(0[1-9]|[1-2][0-9]|3[0-1])",date)) {
				break;
			}
			System.out.println("Veillez recommencer, vous n'avez pas rentrer une date correcte");
			date = scComputer.nextLine().trim();
		}
		return DateMapper.changeToLocalDate(date);
		
		
	}
	
	public void verificationDateIntervale (ComputerBuilder updateComputer, LocalDate discontinuedDate, LocalDate dateinterruption) {
		if ((!(discontinuedDate==null)) && (!(dateinterruption==null))) {
			
		    long diff = discontinuedDate.until(dateinterruption, ChronoUnit.DAYS);
		    if(diff<0) {
		    	System.out.println("La date d'interruption doit etre supérieur a la date d'introduction, la date vas donc etre initialiser a null");
		    	System.out.println("Veillez remodifier l'ordinateur et entrez une date correcte après avoir terminer");
		    	updateComputer.discontinued(null);
		    }else {
		    	updateComputer.discontinued(dateinterruption);
			}
		}
	}
}