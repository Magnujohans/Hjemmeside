package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import connect.MultipleConnect;

public class Avtale {
	int avtaleID;
	String beskrivelse, brukernavn, starttid, sluttid;
	MultipleConnect conn = new MultipleConnect();
	static Scanner in = new Scanner(System.in);

	//konstruktør for eksisterende avtaler, brukes for å endre avtaler, henter feltene fra databasen
	public Avtale(int avtaleID, String brukernavn){
		this.avtaleID=avtaleID;
		this.brukernavn=brukernavn;
		String hentBeskrivelse = "Select beskrivelse from avtale where avtaleID=" + ("\"") + avtaleID + ("\"");
		String hentStarttid = "Select starttid from avtale where avtaleID=" + ("\"") + avtaleID + ("\"");
		String hentSluttid = "Select sluttid from avtale where avtaleID = "+ ("\"") + avtaleID + ("\"");
		this.beskrivelse=conn.get(hentBeskrivelse).get(0).get("beskrivelse");
		this.starttid=conn.get(hentStarttid).get(0).get("starttid");
		this.sluttid=conn.get(hentSluttid).get(0).get("sluttid");
		endreAvtale();
		
	}


	//konstruktør for nye avtaler, setter felter og kaller metoder for å legge til avtalen i databasen og for å legge til mer informasjon
	public Avtale(String starttid, String sluttid, String beskrivelse, String brukernavn){
		this.beskrivelse=beskrivelse;
		this.brukernavn=brukernavn;
		this.starttid=starttid;
		this.sluttid=sluttid;
		avtaleTilDB();
		leggTilAvtaleinfo();
	}

	//Sender avtale til databasen
	private void avtaleTilDB() {
		String statementAvtale = "insert into avtale(starttid, sluttid, beskrivelse, eier) values " 
				+ "("+ ("\"")+starttid
				+ ("\",") +("\"")+ sluttid + ("\",")+ ("\"")+ beskrivelse + ("\",") + ("\"") + brukernavn + ("\")") ;
		conn.send(statementAvtale);
		String hentAvtaleID = "select avtaleID from avtale where eier=" + ("\"") + brukernavn + ("\"") + "and starttid = " + ("\"") + starttid + ("\"");
		avtaleID = Integer.parseInt(conn.get(hentAvtaleID).get(0).get("avtaleID"));
		String statementVarsel= ("insert into varsel(avtaleID, tid, melding, brukernavn) values" + "(\"" + avtaleID + ("\",") 
				+ ("\"") +starttid + ("\",") + ("\"") + beskrivelse + " starter nå på rom: " + 1 + ("\",") + ("\"") + brukernavn + ("\")"));
		conn.send(statementVarsel);
		String hentVarselID = "select varselID from varsel where avtaleID=" + ("\"") + avtaleID + ("\"") + "and brukernavn = " + ("\"") + brukernavn + ("\"");
		int varselID = Integer.parseInt(conn.get(hentVarselID).get(0).get("varselID"));
		String statementDeltar = "insert into deltar(avtaleID, brukernavn, varselID, status) values" + "("+  ("\"")+ avtaleID + ("\",") + ("\"")+brukernavn
				+ ("\",") + ("\"")+ varselID + ("\"") + ",1)"; //
		conn.send(statementDeltar); 
	}

	private void endreAvtale() {
		int valg = 8;
		while (valg!=0){
			System.out.println("\nTrykk 1 for å endre tidspunkt, 2 for å endre beskrivelse, 3 for å endre rom, " +
					"4 for å endre sted,\n5 for å invitere nye deltakere, 6 for å endre deltakerstatus til inviterte deltakere, \n7 for å slette avtale. Trykk 0 når du er ferdig.");
			System.out.println("Valg: ");
			valg = in.nextInt();
			switch(valg){
			case 1: endreTidspunkt(); break; 
			case 2: endreBeskrivelse(); break;
			case 3: //endreRom(); break; må lages
			case 4: endreSted(); break;
			case 5: endreDeltakere(); break;
			case 6: endreAndresDeltakerstatus(); break;
			case 7: slettAvtale();
			case 0: break;
			}
		}

	}
	
	private void markerEndringIAvtale(int type){
		String statementEndre;
		if (type==1){
			statementEndre = "update deltar set usett_endring= 1 where avtaleID="+ ("\"") + avtaleID + ("\"") + "and not brukernavn=" + ("\"") + brukernavn + ("\"") +
					"and usett_endring = 0";
		}
		else {
			statementEndre = "update deltar set usett_endring= 2 where avtaleID="+ ("\"") + avtaleID + ("\"") + "and not brukernavn=" + ("\"") + brukernavn + ("\"");
		}
		conn.send(statementEndre);
	}

	private void slettAvtale() {
		String statementSlett = "Delete from avtale where avtaleID=" + ("\"") + avtaleID + ("\"");
		conn.send(statementSlett);
		System.out.println("Avtalen ble slettet");
	}

	private void endreAndresDeltakerstatus() {
		String finnDeltakere = "Select brukernavn, status from deltar where avtaleID= "+ ("\"") + avtaleID + ("\"");
		System.out.println("Status 0 = Ubesvart invitasjon\nStatus 1 = Skal delta\nStatus 2 = Skal IKKE delta\n");
		ArrayList<HashMap<String,String>> deltakere = conn.get(finnDeltakere);
		for(HashMap<String,String> deltaker : deltakere){
			System.out.println(deltaker.toString());
		}
		in.nextLine();
		System.out.println("Skriv inn brukernavn for deltaker du vil endre status for: ");
		String valgtDeltaker = in.nextLine();
		System.out.println("Ny deltakerstatus: ");
		int nyStatus = in.nextInt();
		String endreStatus = "update deltar set status=" + ("\"") + nyStatus + ("\"") + "where avtaleID=" + ("\"") + avtaleID + ("\"")+  "and brukernavn =" + ("\"") + valgtDeltaker + ("\"") ;
		conn.send(endreStatus);
		if (nyStatus==2){
			markerEndringIAvtale(2);
		}
	}


	private void endreSted() {
		leggTilSted();
		markerEndringIAvtale(1);
	}


	private void endreDeltakere() {
		System.out.println("Trykk 1 for å legge til enkeltpersoner, 2 for å legge til en gruppe");
		int valg = in.nextInt();
		if(valg==1){
			inviterDeltakere();
		}
		if(valg==2){
			inviterGruppe();
		}
		else System.out.println("Feil input");
	}


	private void endreBeskrivelse() {
		in.nextLine();
		System.out.println("Skriv inn ny beskrivelse:");
		String endretBeskrivelse = in.nextLine();
		String nyBeskrivelse = "update avtale set beskrivelse ="+ ("\"") + endretBeskrivelse + ("\"") + " where avtaleID=" + ("\"") + avtaleID + ("\"");
		conn.send(nyBeskrivelse);
		markerEndringIAvtale(1);
	}


	private void endreTidspunkt() {
		in.nextLine();
		System.out.println("Skriv inn starttidspunkt. Format: yyyy-mm-dd tt:mm:ss. ");
		String nyStart = in.nextLine();
		in.nextLine();
		System.out.println("Skriv inn slutttidspunkt. Format: yyyy-mm-dd tt:mm:ss.");
		String nySlutt = in.nextLine();
		String nyTidspunkt = "update avtale set starttid =" + ("\"") + nyStart + ("\", ") + "sluttid= " 
				+ ("\"") + nySlutt + ("\"") + "where avtaleID=" + ("\"") + avtaleID + ("\"");
		conn.send(nyTidspunkt);
		markerEndringIAvtale(1);
		//sjekk om rommet er ledig
	
	}


	//Metode for å legge til ytterligere informasjon i avtalen
	public void leggTilAvtaleinfo() {
		int valg = 6;
		while (valg!=0){
			System.out.println("Trykk 1 for å reservere rom, trykk 2 for å legge til sted, " +
					"trykk 3 for å invitere deltakere, trykk 4 for å invitere en gruppe, trykk 5 for å endre avtalen, trykk 0 dersom du er ferdig: ");
			System.out.println("Valg: ");
			valg = in.nextInt();
			switch(valg){
			case 1: reserverRom(); break;
			case 2: leggTilSted(); break;
			case 3: inviterDeltakere(); break;
			case 4: inviterGruppe(); break;
			case 5: endreAvtale(); break;
			case 0: break;
			}
		}
	}

	private void reserverRom() {
		new Rom(avtaleID);
	}


	//Legger til sted, oppdaterer DB
	private void leggTilSted() {
		in.nextLine();
		System.out.println("Skriv inn sted for avtalen: ");
		String sted = in.nextLine();
		String statement = "update avtale set sted=" + ("\"") + sted + ("\"") + "where avtaleID=" + ("\"") + avtaleID + ("\"");
		conn.send(statement);
	}

	//Inviterer en eller flere deltakere, oppdaterer DB, hvis deltaker ikke eksisterer blir den ikke invitert //mailmulighet
	private void inviterDeltakere() {
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
			if (!(isInvitert(person)))
				inviter(person);
		}

	}

	//Inviterer alle medlemmer av em gruppe som ikke er invitert fra før, oppdaterer DB, gir feilmelding dersom gruppe ikke finnes	
	private void inviterGruppe() {
		in.nextLine();
		System.out.println("Navn på gruppen du vil invitere: ");
		String gruppenavn = in.nextLine();
		String hentGruppeID = ("Select gruppeID from gruppe where gruppenavn=" + ("\"") + gruppenavn + ("\""));
		ArrayList<HashMap<String,String>> resultat = conn.get(hentGruppeID);
		if (!resultat.isEmpty()){
			int gruppeID = Integer.parseInt((resultat).get(0).get("gruppeID"));
			String statement = "select brukernavn from medlem_av where gruppeID = " + ("\"") + gruppeID + ("\"");
			ArrayList<HashMap<String,String>>treff = conn.get(statement);
			for (HashMap<String,String> medlemmer:treff){
				for (String medlem: medlemmer.values()){
					if (!(isInvitert(medlem))) inviter(medlem);
				}	
			}
		}
		else System.out.println("Gruppen finnes ikke");

	}

	//sjekker om deltaker er lagt til i avtalen
	private boolean isInvitert(String medlem) {
		String statement = "select brukernavn from deltar where avtaleID=" + ("\"") + avtaleID + ("\"");
		ArrayList<HashMap<String,String>>invitert = conn.get(statement);
		for (HashMap<String,String> treff: invitert ){
			
			if (treff.containsValue(medlem)) return true;
		}
		return false;
	}

	//Gjør DBoppdateringen for inviterDeltakere og inviterGruppe
	private void inviter(String person) {
		String statementVarsel = ("insert into varsel(avtaleID, tid, melding, brukernavn) values" + "(\"" + avtaleID + ("\",") + "" 
				+ ("\"") +starttid + ("\",") + ("\"") + beskrivelse + " starter nå på rom: " + 1 + ("\",") + ("\"") + person + ("\")"));
		conn.send(statementVarsel);
		String hentVarselID = "select varselID from varsel where avtaleID=" + ("\"") + avtaleID + ("\"") + "and brukernavn = " + ("\"") + person + ("\"");
		int varselID = Integer.parseInt(conn.get(hentVarselID).get(0).get("varselID")); 
		String statementDeltar = "insert into deltar(avtaleID, brukernavn, varselID, status) values" + "("+  ("\"")+ avtaleID + ("\",") + ("\"")+person
				+ ("\",") + ("\"")+ varselID + ("\"") + ",0)"; //egen status for usett invitasjon, her satt til 0, Varsel må endres!
		conn.send(statementDeltar);
	}


}
