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
				System.out.println("logget inn");
			}
			else System.out.println("feil brukernavn og/eller passord \n \n \n");
		}
		run();
	}

	private static void run() { //legg til mulighet for å lage gruppe
		while (logged_in){
			System.out.print("\n \n \nTrykk 1 for å vise kalender, ");
			System.out.print("Trykk 2 for å opprette avtale, ");
			System.out.println("Trykk 3 for å endre avtale, ");
			System.out.print("Trykk 4 for å vise nye varsler, ");
			System.out.print("Trykk 5 for å endre deltakerstatus, ");
			System.out.print("Trykk 6 for gruppemeny, ");
			System.out.println("Trykk 7 for å logge ut: ");
			System.out.println(": ");
			int valg = in.nextInt();
			switch(valg){
			case 1: bruker.visKalender(); break; //ferdig 
			case 2: bruker.opprettAvtale(); break; //tja
			case 3: bruker.endreAvtale(); break; //ikke
			case 4: bruker.visVarsler(); break; //ikke
			case 5: bruker.endreDeltakerStatus(); break; //ikke
			case 6: gruppeMeny(); break; 
			case 7: loggUt(); break;
			}
		}
	}

	private static void gruppeMeny() {
		System.out.print("\n \n \nTrykk 1 for å opprette gruppe, ");
		System.out.print("Trykk 2 for å endre gruppe, ");
		System.out.println("Trykk 3 for å vise mine grupper, ");
		System.out.println(": ");
		int valg = in.nextInt();
		switch(valg){
		case 1: bruker.opprettGruppe(); break;
		case 2: bruker.endreGruppe(); break;
		case 3: bruker.visGrupper(); break;}	
	}

	private static void loggUt() {
		logged_in=false;
		System.out.println("Du er nå logget ut");
		System.out.println("Trykk 1 for å logge inn"); //shortcut...
		int valg=in.nextInt();
		if (valg==1) loggIn();
	}
	

}
