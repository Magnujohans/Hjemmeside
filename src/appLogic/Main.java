package appLogic;

import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args) {
	MainLogic main = new MainLogic();
	Scanner input = new Scanner(System.in);
	String brukernavn = null;
	String passord = null;
	while(main.loggedIn == false){
		System.out.println("Velkommen til kalenderen");
		System.out.println("Skriv inn ditt brukernavn");
		brukernavn = input.next();
		System.out.println("Skriv inn ditt passord");
		
		
	}

	}

}
