package logic;

import java.util.ArrayList;
import java.util.Scanner;
// må sette opp autoincrement i SQL for varselID og avtaleID 

import connect.MultipleConnect;

public class Avtale {
	
	public static int avtaleIDteller = 15; //
	int avtaleID;
	MultipleConnect conn;
	static Scanner in = new Scanner(System.in);
	
	public Avtale(){
		
	}
	
	public Avtale(String starttid, String sluttid, String beskrivelse, String brukernavn){
		avtaleIDteller++;
		this.avtaleID=avtaleIDteller;
		conn = new MultipleConnect();
		String statementAvtale = "insert into avtale(avtaleID, starttid, sluttid, beskrivelse, rom) values " 
				+ "("+  ("\"")+ avtaleID + ("\",") + ("\"")+starttid
				+ ("\",") +("\"")+ sluttid + ("\",")+ ("\"")+ beskrivelse + ("\"") + ",1)";
		String statementDeltar = "insert into deltar(avtaleID, brukernavn, varselID, status) values" + "("+  ("\"")+ avtaleID + ("\",") + ("\"")+brukernavn
				+ ("\",") + ("\"")+ 1 + ("\"") + ",1)"; //egen status for eier, her satt til 1. Eier blir automatisk lagt til i deltar
		conn.send(statementAvtale);
		conn.send(statementDeltar); //må også opprette ett varsler for eier av avtale
		merInfo();
	}


	public void merInfo() { //lag casene slik at de kan brukes i metoder for å endre, dvs lag egne metoder
		System.out.println("Trykk 1 for å legge til slutttidspunkt, trykk 2 for å reservere rom, trykk 3 for å legge til sted, " +
				"trykk 4 for å invitere deltakere, trykk 5 for å invitere en gruppe, trykk 6 dersom du er ferdig med avtalen: ");
		int valg = 0;
		while (valg!=6){
			System.out.println("Valg: ");
			valg = in.nextInt();
			switch(valg){
			case 1: //; break;
			case 2: //; break;
			case 3: //; break;
			case 4: inviterDeltakere(); break;
			case 5: inviterGruppe(); break;
			case 6: break;
		}
		}
		
	}

	private void inviterGruppe() {
		// TODO Auto-generated method stub
		
	}

	public void inviterDeltakere() { //gi først valg mellom gruppe og enkeltperson. Må opprette varsel for hver deltaker i tillegg
		System.out.println("Avslutt ved å skrive inn 0");
		ArrayList<String> deltakerListe = new ArrayList<String>();
		System.out.println("Inviter: ");
		String deltaker = in.next();
		while (!(deltaker.contentEquals("0"))){
			deltakerListe.add(deltaker);
			System.out.println("Inviter: ");
			deltaker = in.next();
		}
		for (String person: deltakerListe){
			String statementDeltar = "insert into deltar(avtaleID, brukernavn, varselID, status) values" + "("+  ("\"")+ avtaleID + ("\",") + ("\"")+person
					+ ("\",") + ("\"")+ 1 + ("\"") + ",0)"; //egen status for usett invitasjon, her satt til 0, Varsel må endres!
			conn.send(statementDeltar);
		}
		
		
		
	}
	

}
