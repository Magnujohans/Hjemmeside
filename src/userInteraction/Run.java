package userInteraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import logic.Person;

import connect.MultipleConnect;

public class Run {
	String brukernavn;
	static boolean logged_in=false;
	static MultipleConnect mc = new MultipleConnect(); 
	static Person bruker;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		loggIn();
	}

	private static void loggIn() {
		while (!(logged_in)){
			System.out.print("Skriv inn brukernavn: ");
			String brukernavn = in.next();
			System.out.print("Skriv inn passord: ");
			String passord = in.next();
			ArrayList<HashMap<String,String>> resultat = mc.get(("select * from person where brukernavn= \"")+ brukernavn +("\"") + ("and passord =")+ ("\"") + passord +("\"") );
			if (!resultat.isEmpty()){
				logged_in=true;
				bruker = new Person(brukernavn);
				System.out.println("Logget inn");
			}
			else System.out.println("feil brukernavn og/eller passord \n \n");
		}
		run();
	}

	private static void run() {
		System.out.println("Velkommen!");
		bruker.visNyeVarsler();
		while (logged_in){
			bruker.VarsleEndringer();
			System.out.print("\nTrykk 1 for � vise kalender, ");
			System.out.print("Trykk 2 for � opprette avtale, ");
			System.out.println("Trykk 3 for � endre og administrere avtale, ");
			System.out.print("Trykk 4 for � vise nye varsler, ");
			System.out.print("Trykk 5 for � endre din deltakerstatus, ");
			System.out.println("Trykk 6 for � se hvem som skal delta i en avtale");
			System.out.print("Trykk 7 for gruppemeny, ");
			System.out.println("Trykk 8 for � logge ut: ");
			System.out.println(": ");
			in.nextLine();
			int valg = in.nextInt();
			switch(valg){
			case 1: bruker.visKalender(); break;
			case 2: bruker.opprettAvtale(); break;
			case 3: bruker.endreAvtale(); break;
			case 4: bruker.visNyeVarsler(); break;
			case 5: bruker.endreDeltakerstatus(); break;
			case 6: bruker.visDeltakere(); break;
			case 7: gruppemeny(); break; 
			case 8: loggUt(); break;
			}
		}
	}

	private static void gruppemeny() {
		System.out.print("\n \n \nTrykk 1 for � opprette gruppe, ");
		System.out.print("Trykk 2 for � endre gruppe, ");
		System.out.println("Trykk 3 for � vise mine grupper, ");
		System.out.println(": ");
		int valg = in.nextInt();
		switch(valg){
		case 1: bruker.opprettGruppe(); break;
		case 2: bruker.endreGruppe(); break;
		case 3: bruker.visGrupper(); break;}	
	}

	private static void loggUt() {
		logged_in=false;
		System.out.println("Du er n� logget ut");
		System.out.println("Trykk 1 for � logge inn");
		int valg=in.nextInt();
		if (valg==1) loggIn();
	}
	

}
