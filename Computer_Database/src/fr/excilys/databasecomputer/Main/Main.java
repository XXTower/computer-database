package fr.excilys.databasecomputer.Main;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		char reponce = ' ' , option = ' ';
		do {
			System.out.println("Bonjour quevouslez-vous faire ?");
			do {
				System.out.println("1 - Liste Ordinateurs");
				System.out.println("2 - List Companies");
				System.out.println("3 - Voir detail d'un ordinateur");
				System.out.println("4 - Creer un ordinateur");
				System.out.println("5 - Mettre a jour un ordinateur");
				System.out.println("6 - Supprimmer un ordinateur");
				option = sc.nextLine().charAt(0);
				
				if(option != '1' && option != '2' && option != '3' && option != '4' && option != '5' && option != '6') {
					System.out.println("Option inconnu, veillez choisir une option corrcte");
				}
			}while(option != '1' && option != '2' && option != '3' && option != '4' && option != '5' && option != '6');
			
			System.out.println("Voulez-vous quiter ?");
			System.out.println("1 - Oui");
			System.out.println("2 - Non");
			reponce= sc.nextLine().charAt(0);
			
		}while(reponce == '2');
					
	}

}
