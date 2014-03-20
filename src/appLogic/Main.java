package appLogic;

import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args) {
	MainLogic main = new MainLogic();
	Scanner input = new Scanner(System.in);
	Employee tryEmp = null;
	for(Employee emp: main.employees){
		System.out.println(emp);
	}
	System.out.println("velg brukerindex");
	int index = input.nextInt();
	tryEmp = main.employees.get(index);
	String brukernavn = main.employees.get(index).toString();
	String passord = null;
	while(main.loggedIn == false){
		System.out.println("Velkommen til kalenderen");
		System.out.println("Skriv inn ditt passord");
		passord = input.next();
		main.LogIn(brukernavn, passord);
		if (main.loggedIn == true){
			main.currentUser = tryEmp;
			System.out.println("Logget inn");
		}
		else{
			System.out.println("Feil brukernavn eller passord");
		}
		
	}
	System.out.print("\n \n \nTrykk 1 for å vise kalender, ");
	System.out.print("Trykk 2 for å opprette avtale, ");
	System.out.println("Trykk 3 for å endre avtale, ");
	System.out.print("Trykk 4 for å vise nye varsler, ");
	System.out.print("Trykk 5 for å endre deltakerstatus, ");
	System.out.print("Trykk 6 for gruppemeny, ");
	System.out.println("Trykk 7 for å logge ut: ");
	System.out.println(": ");
	main.createAppointment();
	}
}
