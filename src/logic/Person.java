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
	
	public void visKalender() {
		String statement;
		System.out.print("Velg 1 for å vise din egen kalender, trykk 2 for å vise kollegas kalender: ");
		int valg = in.nextInt();
		switch(valg){
		case 1: System.out.println("Viser kalender for " + brukernavn + "\n\n");
				String statementEgneAvtaler = "Select beskrivelse, sted, starttid, sluttid, rom from avtale where eier=" + ("\"") + brukernavn + ("\"")+ "and sluttid>= cast((now()) as date)";
				ArrayList<HashMap<String,String>> egneAvtaler = conn.get(statementEgneAvtaler);
				System.out.println("Avtaler opprettet av deg:\n");
				for(HashMap<String,String> avtale : egneAvtaler){
					System.out.println(avtale.toString());
				}
				String statementInvitert = "select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" from avtale, deltar " +
						"where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\"") + "and not eier="+("\"") + brukernavn + ("\"")
						+ "and not (skjul_i_kalender=1)";
				ArrayList<HashMap<String,String>> andreAvtaler = conn.get(statementInvitert);
				System.out.println("\nAndre avtaler:\n");
				for(HashMap<String,String> avtale : andreAvtaler){
					System.out.println(avtale.toString());
				}
	
				break;
				
				
		case 2: System.out.println("Brukernavn til kollega:");
				String kollega = in.next();
				System.out.println("Viser kalender for " + kollega + "\n\n\n");
				statement = ("select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" " +
						"from avtale, deltar " + "where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID" +
						" and deltar.brukernavn=" + ("\"") + kollega + ("\"") + "and not skjul_i_kalender=1");
				ArrayList<HashMap<String,String>> kollegasavtaler = conn.get(statement);
				for(HashMap<String,String> avtale : kollegasavtaler){
					System.out.println(avtale.toString());
				}
				break;
		default: System.out.println("feil input");
				statement = "";
				break;
		}
	}

	//viser alle avtaler som ikke er i fortid for valgt person og som ikke er markert som skjult i kalender
	public void visKalender2() {
		String statement;
		System.out.print("Velg 1 for å vise din egen kalender, trykk 2 for å vise kollegas kalender: ");
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
	
	
	public void visAlarmer(){
		ArrayList<Varsel> varsler = new ArrayList<Varsel>();
		
		String Statement = (String.format("SELECT * FROM Varsel WHERE brukernavn=%s AND sett =%s and tid >= cast((now()) as date)", brukernavn, 0));
		ArrayList<HashMap<String,String>> posts = conn.get(Statement);
		for(HashMap<String,String> post : posts){
			String beskrivelse = post.get("melding");
			String tidspunkt = post.get("tid");
			int avtaleid = Integer.parseInt(post.get("avtaleID"));
			int varselid = Integer.parseInt(post.get("varselID"));
			int sett = Integer.parseInt(post.get("sett"));
			varsler.add(new Varsel(varselid,avtaleid, sett, tidspunkt, beskrivelse, brukernavn));
		}
		for(Varsel varsel: varsler){
			System.out.println("ALARM!!!");
			System.out.println(varsel);
			varsel.alarmenErSett(varsel.getVarselID());			
		}
		
		
	}
	
	
	public void visNyeVarsler(){ //Hvem ble du invitert av vises som eier, skjønner ikke hvorfor
		String statement = ("select starttid, sluttid, sted, beskrivelse,rom, eier AS \"invitert av\" " +
		"from avtale, deltar " +
		"where deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\"") + "and status =" + 0 );
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		int count = avtaler.size();
		System.out.println("\n\nDu har " + count + " nye invitasjoner og ubesvarte avtaler!");
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
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
	
	
	public void endreDeltakerstatus(){ //Endrer kun sin egen her, kun eiere kan endre andres. Gjøres via endre/administrere avtale
		System.out.println("Status 0 = Ubesvart invitasjon\nStatus 1 = Skal delta\nStatus 2 = Skal IKKE delta\n");
		String statement = ("select beskrivelse, deltar.avtaleID, status from avtale, deltar where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\""));
		ArrayList<HashMap<String,String>> avtaler = conn.get(statement);
		for(HashMap<String,String> avtale : avtaler){
			System.out.println(avtale.toString());
		}
		System.out.println("\nSkriv inn avtaleID for den avtalen du ønsker å endre deltakerstatus i: ");
		int avtaleID = in.nextInt();
		in.nextLine();
		System.out.println("Ny deltakerstatus: ");
		int nyStatus = in.nextInt();
		String endreStatus = "update deltar set status=" + ("\"") + nyStatus + ("\"") + "where avtaleID=" + ("\"") + avtaleID + ("\"")+  "and brukernavn =" + ("\"") + brukernavn + ("\"") ;
		conn.send(endreStatus);
		if (nyStatus==2){
			sendAvbudvarsel(avtaleID);
			in.nextLine();
			System.out.println("ønsker du å skjule avtalen i kalenderen din? Trykk 1 for ja.");
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
		System.out.println("Skriv inn navn på gruppe du ønsker å endre");
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
			System.out.println( "\nVARSEL: Avtalen \"" + avtale.get("beskrivelse")+ ("\"")+ " har blitt endret, vis kalender for å se endringer");
		}
		String avlysninger = "Select beskrivelse, avtale.avtaleID from avtale, deltar where avtale.avtaleID = deltar.avtaleID and brukernavn= "+ ("\"") + brukernavn + ("\"") + "and usett_endring=2";
		ArrayList<HashMap<String,String>> avlysningAvtaler = conn.get(avlysninger);
		for(HashMap<String,String> avtale : avlysningAvtaler){
			System.out.println( "\nVARSEL: Avlysninger i avtalen \"" + avtale.get("beskrivelse")+ ("\". Følgende deltakere har avlyst:\n "));
			int avtaleID = Integer.parseInt(avtale.get("avtaleID"));
			String deltakerAvlyst = "select brukernavn from deltar where avtaleID="+("\"") + avtaleID + ("\"") + "and status=2";
			ArrayList<HashMap<String,String>> deltakereAvlyst = conn.get(deltakerAvlyst);
			for(HashMap<String,String> deltaker : deltakereAvlyst){
				System.out.print("-"+deltaker.get("brukernavn")+"-");
			}
			System.out.println(". Vis kalender for å se flere endringer i avtalen");
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
				System.out.println(deltakernavn + " har ikke svart på innbydelsen.");
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
