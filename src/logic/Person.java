package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import connect.MultipleConnect;

public class Person {
	
	String brukernavn;
	MultipleConnect conn;
	Scanner in = new Scanner(System.in);
	
	public Person(String brukernavn){
		this.brukernavn=brukernavn;
		this.conn=new MultipleConnect();	
	}

	public void visKalender() { //mangler å legge til egen status, helst med tekst, tall går fint
		String statement;
		System.out.print("Velg 1 for å vise din egen kalender, trykk 2 for å vise kollegas kalender: ");
		int valg = in.nextInt();
		switch(valg){
		case 1: System.out.println("Viser kalender for " + brukernavn);
				statement = ("select starttid, sluttid, sted, beskrivelse,rom " +
								"from avtale, deltar " +
								"where deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\""));
				break;
		case 2: System.out.println("Brukernavn til kollega:");
				String kollega = in.next();
				System.out.println("Viser kalender for " + kollega + "\n\n\n");
				statement = ("select starttid, sted, beskrivelse,rom " +
							"from avtale, deltar " +
							"where deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + kollega + ("\""));
				break;
		default: System.out.println("feil input");
				statement = ""; //shortcut....
				break;
		}
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
	}
	
	
	public void opprettAvtale(){
		System.out.println("Skriv inn navn/beskrivelse for avtalen. Avslutt med enter. \n");
		String beskrivelse = in.nextLine();
		System.out.println("Skriv in starttidspunkt. Format: yyyy-mm-dd tt:mm:ss. Avslutt med enter. \n");
		String starttid = in.nextLine();
		System.out.println("Skriv in slutttidspunkt. Format: yyyy-mm-dd tt:mm:ss. Avslutt med enter. \n");
		String slutttid = in.nextLine();
		//Legg inn tester for riktig input, test format
		Avtale avtale = new Avtale (starttid, slutttid, beskrivelse, brukernavn);
	}
	
	public void endreAvtale(){ //utnytt gamle metoder
		
	}
	
	public void visVarsler(){
		
	}
	public void visNyeVarsler(){
		System.out.println("Viser nye avtaler for " + brukernavn);
		String statement = ("select starttid, sluttid, sted, beskrivelse,rom " +
		"from avtale, deltar " +
		"where deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\"") + "and status =" + 0 );
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
	}
		
	}
	
	public void endreDeltakerStatus(){
		
	}

	public void opprettGruppe() {
		System.out.println("Skriv inn gruppenavn: ");
		String gruppenavn = in.nextLine();
		ArrayList<String> deltakerliste = new ArrayList<String>();
		System.out.println("skriv inn brukernavn til personer som skal legges til. Avslutt med 0.");
		System.out.println("Legg til: ");
		String deltaker = in.next();
		while (!(deltaker.contentEquals("0"))){
			deltakerliste.add(deltaker);
			System.out.println("Legg til: ");
			deltaker = in.next();
		}
		new Gruppe(gruppenavn, deltakerliste);
	}

	public void endreGruppe() {
		// metoder for å legge til og fjerne deltakere
		
	}

	public void visGrupper() {
		// TODO Auto-generated method stub
		
	}

	

}
