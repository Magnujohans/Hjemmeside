package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import connect.MultipleConnect;

public class Person {
	
	String brukernavn;
	MultipleConnect conn = new MultipleConnect();
	Scanner in = new Scanner(System.in);
	
	public Person(String brukernavn){
		this.brukernavn=brukernavn;	
	}
	
	public void visKalender() { //ENDRET AV SEBASTIAN
		String statement;
		System.out.print("Velg 1 for Œ vise din egen kalender, trykk 2 for Œ vise kollegas kalender: ");
		int valg = in.nextInt();
		switch(valg){
		case 1: System.out.println("Viser kalender for " + brukernavn + "\n\n");
		
				String statementEgneAvtaler = "Select beskrivelse, sted, starttid, sluttid, rom from avtale where eier=" + ("\"") + brukernavn + ("\"")+ "and sluttid>= cast((now()) as date) ORDER BY starttid ASC";
				
				System.out.println("Avtaler opprettet av deg:\n");
				toString1(statementEgneAvtaler);			
				
				String statementInvitert = "select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" from avtale, deltar " +
						"where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\"") + "and not eier="+("\"") + brukernavn + ("\"")
						+ "and not (skjul_i_kalender=1) ORDER BY starttid ASC";
				
				System.out.println("\nAvtaler opprettet av andre:\n");
				toString2(statementInvitert);
				
	
				break;
				
				
		case 2: System.out.println("Brukernavn til kollega:");
				String kollega = in.next();
				System.out.println("Viser kalender for " + kollega + "\n\n\n");
				statement = ("select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" " +
						"from avtale, deltar " + "where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID" +
						" and deltar.brukernavn=" + ("\"") + kollega + ("\"") + "and not skjul_i_kalender=1");
				toString1(statement);
				break;
		default: System.out.println("feil input");
				statement = "";
				break;
		}
	}


	private void toString1(String statement) {
		String langLinje = "_______________________________________________________________________________________________________________________________";
		System.out.println(langLinje);
		System.out.println("|  Starttid:              |  Slutt-tid:              |  Beskrivelse:                  |  Sted:                 |  Rom:        |");
		System.out.println(langLinje);
		
		ArrayList<HashMap<String,String>> egneAvtaler = conn.get(statement);

		for(HashMap<String,String> avtale : egneAvtaler){
			
			String starttid = avtale.get("starttid");
			String sluttid = avtale.get("sluttid");
			String beskrivelse = avtale.get("beskrivelse");
			String sted = avtale.get("sted");
			String rom = avtale.get("rom");
			
			String mellomrom = " ";
			
			if(sted==null){
				sted = "         -   ";
			}
			int stedLengde = sted.length();
			
			if(rom==null){
				rom = "    -   ";
			}
			int romLengde = rom.length();
			
			int differanseBeskrivelse = 30-beskrivelse.length();
			int differanseSted = 20-stedLengde;
			int differanseRom = 10-romLengde;
			
			
			for(int i=0; i<differanseBeskrivelse; i++){
				beskrivelse += mellomrom;
			}
			
			for(int i=0; i<differanseSted; i++){
				sted += mellomrom;
			}
			
			for(int i=0; i<differanseRom; i++){
				rom += mellomrom;
			}
			
			System.out.println("|  " + starttid + "  |  " + sluttid + "   |  " + beskrivelse + "|  "+ sted + "  |  " +  rom + "  |");					
		}
		System.out.println(langLinje);
		
		
		
	}

	private void toString2(String statementInvitert) {
		String langLinje = "___________________________________________________________________________________________________________________________________________________________";
		System.out.println(langLinje);
		System.out.println("|  Starttid:              |  Slutt-tid:              |  Beskrivelse:                  |  Sted:                 |  Rom:        |  Invitert av: |  Status:  |");
		System.out.println(langLinje);
		
		ArrayList<HashMap<String,String>> andreAvtaler = conn.get(statementInvitert);
		for(HashMap<String,String> avtale : andreAvtaler){
			
			String starttid = avtale.get("starttid");
			String sluttid = avtale.get("sluttid");
			String beskrivelse = avtale.get("beskrivelse");
			String sted = avtale.get("sted");
			String rom = avtale.get("rom");
			String eier = avtale.get("eier");
			String status = avtale.get("status");
			
			String mellomrom = " ";

			if(sted==null){
				sted = "         -   ";
			}
			
			if(rom==null){
				rom = "    -   ";
			}
			
			//0 ubesvart, 1 deltar, 2 avslått:
			
			
			if(status.equals("0")){
				status = "Ubesvart";
			}
			else if(status.equals("1")){
				status = "Deltar";
			}
			else if(status.equals("2")){
				status = "Avslått";
			}

			int stedLengde = sted.length();
			int romLengde = rom.length();
			int eierLengde = eier.length();
			int statusLengde = status.length();
			
			int differanseBeskrivelse = 30-beskrivelse.length();
			int differanseSted = 20-stedLengde;
			int differanseRom = 10-romLengde;
			int differanseEier = 11-eierLengde;
			int differanseStatus = 9-statusLengde;
			
			for(int i=0; i<differanseBeskrivelse; i++){
				beskrivelse += mellomrom;
			}
			for(int i=0; i<differanseSted; i++){
				sted += mellomrom;
			}
			for(int i=0; i<differanseRom; i++){
				rom += mellomrom;
			}
			for(int i=0; i<differanseEier; i++){
				eier += mellomrom;
			}
			for(int i=0; i<differanseStatus; i++){
				status += mellomrom;
			}
			
			
			System.out.println("|  " + starttid + "  |  " + sluttid + "   |  " + beskrivelse + "|  "+ sted + "  |   " +  rom + " |  " + eier + "  |  " + status + "|");					

		}
		System.out.println(langLinje + "\n");
		
		
	}
	//viser alle avtaler som ikke er i fortid for valgt person og som ikke er markert som skjult i kalender
	public void visKalender2() {
		String statement;
		System.out.print("Velg 1 for Œ vise din egen kalender, trykk 2 for Œ vise kollegas kalender: ");
		int valg = in.nextInt();
		switch(valg){
		case 1: System.out.println("Viser kalender for " + brukernavn + "\n\n\n");
				statement = ("select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" " +
								"from avtale, deltar " +
								"where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\""));
				break;
		case 2: System.out.println("Brukernavn til kollega:");
				String kollega = in.next();
				System.out.println("Viser kalender for " + kollega + "\n\n\n");
				statement = ("select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" " +
						"from avtale, deltar " +
						"where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + kollega + ("\""));
				break;
		default: System.out.println("feil input");
				statement = "";
				break;
		}
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
	}
	
	public void visNyeVarsler(){ //Hvem ble du invitert av vises som eier, skj¿nner ikke hvorfor
		String statement = ("select starttid, sluttid, sted, beskrivelse,rom, eier AS \"invitert av\" " +
		"from avtale, deltar " +
		"where deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\"") + "and status =" + 0 );
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		int count = avtaler.size();
		System.out.println("\n\nDu har " + count + " nye invitasjoner og ubesvarte avtaler!");
		toString1(statement);
		
	}
	
	
	public void opprettAvtale(){
		in.nextLine();
		System.out.println("Skriv inn navn/beskrivelse for avtalen. Avslutt med enter. \n");
		String beskrivelse = in.nextLine();
		System.out.println("Skriv in starttidspunkt. Format: yyyy-mm-dd tt:mm:ss. Avslutt med enter. \n");
		String starttid = in.nextLine();
		System.out.println("Skriv in slutttidspunkt. Format: yyyy-mm-dd tt:mm:ss. Avslutt med enter. \n");
		String slutttid = in.nextLine();
		//Legg inn tester for riktig input, test format
		new Avtale (starttid, slutttid, beskrivelse, brukernavn);
	}
	
	//lar bruker endre avtale dersom han/hun er eier av avtalen
	public void endreAvtale(){
		String finnMuligeAvtaler = "Select beskrivelse, sted, rom, avtaleID from avtale where eier=" + ("\"") + brukernavn + ("\"");
		System.out.println();
		ArrayList<HashMap<String,String>> avtaler = conn.get(finnMuligeAvtaler);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
		System.out.println();
		System.out.println("Velg avtale du vil endre fra oversikten, skriv inn avtaleID: ");
		int avtaleIDskalEndres = in.nextInt();
		new Avtale(avtaleIDskalEndres, brukernavn);
	}
	
	
	public void endreDeltakerstatus(){ //Endrer kun sin egen her, kun eiere kan endre andres. Gj¿res via endre/administrere avtale
		System.out.println("Status 0 = Ubesvart invitasjon\nStatus 1 = Skal delta\nStatus 2 = Skal IKKE delta\n");
		String statement = ("select beskrivelse, deltar.avtaleID, status from avtale, deltar where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\""));
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
		System.out.println("\nSkriv inn avtaleID for den avtalen du ¿nsker Œ endre deltakerstatus i: ");
		int avtaleID = in.nextInt();
		in.nextLine();
		System.out.println("Ny deltakerstatus: ");
		int nyStatus = in.nextInt();
		String endreStatus = "update deltar set status=" + ("\"") + nyStatus + ("\"") + "where avtaleID=" + ("\"") + avtaleID + ("\"")+  "and brukernavn =" + ("\"") + brukernavn + ("\"") ;
		conn.send(endreStatus);
		if (nyStatus==2){
			sendAvbudvarsel(avtaleID);
			in.nextLine();
			System.out.println("¿nsker du Œ skjule avtalen i kalenderen din? Trykk 1 for ja.");
			int valg = in.nextInt();
			if(valg==1){
				String endreVisning = "update deltar set skjul_i_kalender=1 where avtaleID=" + ("\"") + avtaleID + ("\"")+  "and brukernavn =" + ("\"") + brukernavn + ("\"") ;
				conn.send(endreVisning);
			}
		}
	}

	private void sendAvbudvarsel(int avtaleID) {
		String statementEndre= "update deltar set usett_endring= 2 where avtaleID="+ ("\"") + avtaleID + ("\"") + "and not brukernavn=" + ("\"") + brukernavn + ("\"");
		conn.send(statementEndre);
	}

	public void opprettGruppe() {
		in.nextLine();
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
		visGrupper();
		in.nextLine();
		System.out.println("Skriv inn navn pŒ gruppe du ¿nsker Œ endre");
		String gruppenavn = in.nextLine();
		String hentGruppeID = ("Select gruppeID from gruppe where gruppenavn=" + ("\"") + gruppenavn + ("\""));
		ArrayList<HashMap<String,String>> resultat = conn.get(hentGruppeID);
		if (!resultat.isEmpty()){
			Gruppe eksisterendeGruppe = new Gruppe(gruppenavn, Integer.parseInt((resultat).get(0).get("gruppeID")));
			eksisterendeGruppe.endreGruppe();
			}
	}

	public void visGrupper() {
		String visGrupper = "Select gruppenavn from gruppe, medlem_av where gruppe.gruppeID=medlem_av.gruppeID and brukernavn = "+ ("\"") + brukernavn + ("\"");
		ArrayList<HashMap<String,String>> grupper = conn.get(visGrupper);
		for(HashMap<String,String> gruppe : grupper){
			System.out.println(gruppe.toString());
		}
	}

	public void VarsleEndringer() {
		String endringerIAvtale = "Select beskrivelse from avtale, deltar where avtale.avtaleID = deltar.avtaleID and brukernavn= "+ ("\"") + brukernavn + ("\"") + "and usett_endring=1 and skjul_i_kalender=0";
		ArrayList<HashMap<String,String>> endredeAvtaler = conn.get(endringerIAvtale);
		for(HashMap<String,String> avtale : endredeAvtaler){
			System.out.println( "\nVARSEL: Avtalen \"" + avtale.get("beskrivelse")+ ("\"")+ " har blitt endret, vis kalender for Œ se endringer");
		}
		String avlysninger = "Select beskrivelse, avtale.avtaleID from avtale, deltar where avtale.avtaleID = deltar.avtaleID and brukernavn= "+ ("\"") + brukernavn + ("\"") + "and usett_endring=2";
		ArrayList<HashMap<String,String>> avlysningAvtaler = conn.get(avlysninger);
		for(HashMap<String,String> avtale : avlysningAvtaler){
			System.out.println( "\nVARSEL: Avlysninger i avtalen \"" + avtale.get("beskrivelse")+ ("\". F¿lgende deltakere har avlyst:\n "));
			int avtaleID = Integer.parseInt(avtale.get("avtaleID"));
			String deltakerAvlyst = "select brukernavn from deltar where avtaleID="+("\"") + avtaleID + ("\"") + "and status=2";
			ArrayList<HashMap<String,String>> deltakereAvlyst = conn.get(deltakerAvlyst);
			for(HashMap<String,String> deltaker : deltakereAvlyst){
				System.out.print("-"+deltaker.get("brukernavn")+"-");
			}
			System.out.println(". Vis kalender for Œ se flere endringer i avtalen");
		}
		String markerSett = "Update deltar set usett_endring=0 where brukernavn= "+ ("\"") + brukernavn + ("\"") + "and (usett_endring=1 or usett_endring=2 or usett_endring=3)";
		conn.send(markerSett);
	}

	public void visDeltakere() {
		String finnMuligeAvtaler = "Select beskrivelse, avtale.avtaleID from avtale, deltar where deltar.avtaleID = avtale.avtaleID and brukernavn=" + ("\"") + brukernavn + ("\"");
		System.out.println();
		ArrayList<HashMap<String,String>> avtaler = conn.get(finnMuligeAvtaler);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
		System.out.println("Velg avtale fra oversikten, skriv inn avtaleID: ");
		int avtaleID = in.nextInt();
		String finnDeltakere = "Select brukernavn, status from deltar where avtaleID="+ ("\"") + avtaleID + ("\"");
		ArrayList<HashMap<String,String>> deltakere = conn.get(finnDeltakere);
		for(HashMap<String,String> deltaker : deltakere){
			String deltakernavn=deltaker.get("brukernavn");
			int status = Integer.parseInt(deltaker.get("status"));
			if (status==0){
				System.out.println(deltakernavn + " har ikke svart pŒ innbydelsen.");
			}
			if (status==1){
				System.out.println(deltakernavn + " skal delta.");
			}
			if (status==2){
				System.out.println(deltakernavn + " skal IKKE delta.");
			}
		}
	}

	

}
