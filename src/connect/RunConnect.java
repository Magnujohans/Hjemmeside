package connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import logic.Avtale;

public class RunConnect {
	
	Scanner in = new Scanner(System.in);
	private String brukernavn;
	MultipleConnect conn = new MultipleConnect();

	public static void main(String[] args) {
//		MultipleConnect my = new MultipleConnect();
//		ArrayList<HashMap<String,String>> resultat = my.get("select brukernavn from person");
//		for(HashMap<String,String> post : resultat){
//			System.out.println(post.get("brukernavn"));
		
		
	}


//viser alle avtaler som ikke er i fortid for valgt person // mŒ legge inn sŒ det vises hvilke avtaler du har opprettet selv
	public void visKalender() {
		String statement;
		System.out.print("Velg 1 for Œ vise din egen kalender, trykk 2 for Œ vise kollegas kalender: ");
		int valg = in.nextInt();
		switch(valg){
		case 1: System.out.println("Viser kalender for " + brukernavn + "\n\n");
				String statementEgneAvtaler = "Select beskrivelse, sted, starttid, sluttid, rom from avtale where eier=" + ("\"") + brukernavn + ("\"")+ "and sluttid>= cast((now()) as date";
				ArrayList<HashMap<String,String>> egneAvtaler = conn.get(statementEgneAvtaler);
				System.out.println("Avtaler opprettet av deg:\n");
				for(HashMap<String,String> avtale : egneAvtaler){
					System.out.println(avtale.toString());
				}
				String statementInvitert = "select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" from avtale, deltar " +
						"where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + brukernavn + ("\"") + "and not eier="+("\"") + brukernavn + ("\"");
				ArrayList<HashMap<String,String>> andreAvtaler = conn.get(statementInvitert);
				System.out.println("Andre avtaler:\n");
				for(HashMap<String,String> avtale : andreAvtaler){
					System.out.println(avtale.toString());
				}
	
				break;
				
				
		case 2: System.out.println("Brukernavn til kollega:");
				String kollega = in.next();
				System.out.println("Viser kalender for " + kollega + "\n\n\n");
				statement = ("select starttid, sluttid, sted, beskrivelse,rom, status, eier as \"invitert av\" " +
						"from avtale, deltar " +
						"where sluttid>= cast((now()) as date) and deltar.avtaleID=avtale.avtaleID and deltar.brukernavn=" + ("\"") + kollega + ("\""));
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
}