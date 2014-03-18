package logic;

import java.util.ArrayList;

import connect.MultipleConnect;

public class Gruppe {
	int gruppeID; //må fikse teller
	String gruppenavn;
	MultipleConnect conn;
	
	public Gruppe(String gruppenavn, ArrayList<String> deltakerliste){
		conn = new MultipleConnect();
		this.gruppenavn=gruppenavn;
		this.gruppeID = 1;
		String statementGruppe = "insert into gruppe(gruppeID, gruppenavn) values " 
				+ "("+  ("\"")+ gruppeID + ("\",") + ("\"")+gruppenavn + ("\"") + ")";
		conn.send(statementGruppe);
		for(String deltaker:deltakerliste){
			String statementMedlem_av = "insert into medlem_av(gruppeID, brukernavn) values" + "("+  ("\"")+ gruppeID + ("\",") + ("\"")+deltaker+ ("\"")+")";
			conn.send(statementMedlem_av);
		}
	}

}
