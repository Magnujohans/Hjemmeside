package connect;

import java.util.ArrayList;
import java.util.HashMap;

import logic.Avtale;

public class RunConnect {

	public static void main(String[] args) {
//		MultipleConnect my = new MultipleConnect();
//		ArrayList<HashMap<String,String>> resultat = my.get("select brukernavn from person");
//		for(HashMap<String,String> post : resultat){
//			System.out.println(post.get("brukernavn"));
		
//		}
		
		String statementGruppe = "insert into gruppe(gruppeID, gruppenavn) values " 
				+ "("+  ("\"")+ 13 + ("\",") + ("\"")+"kulegruppa" + ("\"");
		
		System.out.println(statementGruppe);
				
	}
}

