package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import connect.MultipleConnect;

public class Gruppe {
	int gruppeID;
	String gruppenavn;
	MultipleConnect conn = new MultipleConnect();
	Scanner in = new Scanner(System.in);
	
	//konstruktør som brukes for å få tilgang til å endre eksisterende grupper
	public Gruppe (String gruppenavn, int gruppeID){
		this.gruppenavn=gruppenavn;
		this.gruppeID=gruppeID;
	}
	
	//Konstruktør for nye grupper
	public Gruppe(String gruppenavn, ArrayList<String> deltakerliste){
		this.gruppenavn=gruppenavn;
		String statementGruppe = "insert into gruppe(gruppenavn) values ("+ ("\"")+gruppenavn + ("\"") + ")";
		conn.send(statementGruppe);
		String hentGruppeID = ("Select gruppeID from gruppe where gruppenavn=" + ("\"") + gruppenavn + ("\""));
		ArrayList<HashMap<String,String>> resultat = conn.get(hentGruppeID);
		this.gruppeID = Integer.parseInt((resultat).get(0).get("gruppeID"));
		for(String deltaker:deltakerliste){
			String statementMedlem_av = "insert into medlem_av(gruppeID, brukernavn) values" + "("+  ("\"")+ gruppeID + ("\",") + ("\"")+deltaker+ ("\"")+")";
			conn.send(statementMedlem_av);
		}
	}
	
	public void endreGruppe(){
		System.out.println("Nåværende deltakere i gruppen:");
		String hentMedlemmer = "select brukernavn from medlem_av where gruppeID=" + ("\"") + gruppeID + ("\"");
		ArrayList<HashMap<String,String>> gruppemedlemmer = conn.get(hentMedlemmer);
		ArrayList<String> deltakerliste = new ArrayList<String>();
		for(HashMap<String,String> gruppemedlem : gruppemedlemmer){
			deltakerliste.add(gruppemedlem.get("brukernavn"));
			System.out.println(gruppemedlem.get("brukernavn"));
		}
		in.nextLine();
		System.out.println("Trykk 1 for å legge til deltakere, trykk 2 for å fjerne deltakere");
		int valg = in.nextInt();
		if (valg==1){
			leggTilDeltakere(deltakerliste);
		}
		if (valg==2){
			slettDeltakere(deltakerliste);
		}

}

	private void slettDeltakere(ArrayList<String> invitertTidligere) {
		System.out.println("Avslutt ved å skrive inn 0");
		ArrayList<String> deltakerliste = new ArrayList<String>();
		System.out.println("Slett: ");
		String deltaker = in.next();
		while (!(deltaker.contentEquals("0"))){
			deltakerliste.add(deltaker);
			System.out.println("Slett: ");
			deltaker = in.next();}
		for(String deltar:deltakerliste){
			if(invitertTidligere.contains(deltar)){
				String statementSlett = "delete from medlem_av where brukernavn=" + ("\"") + deltar + ("\"") + "and gruppeID ="+ ("\"") + gruppeID + ("\"") ;
				conn.send(statementSlett);
			}
		}
		
	}

	private void leggTilDeltakere(ArrayList<String> invitertTidligere) {
		System.out.println("Avslutt ved å skrive inn 0");
		ArrayList<String> deltakerliste = new ArrayList<String>();
		System.out.println("Inviter: ");
		String deltaker = in.next();
		while (!(deltaker.contentEquals("0"))){
			deltakerliste.add(deltaker);
			System.out.println("Inviter: ");
			deltaker = in.next();}
		for(String deltar:deltakerliste){
			if(!invitertTidligere.contains(deltar)){
				String statementMedlem_av = "insert into medlem_av(gruppeID, brukernavn) values" + "("+  ("\"")+ gruppeID + ("\",") + ("\"")+deltar+ ("\"")+")";
				conn.send(statementMedlem_av);
			}
		}
		
	}


}
