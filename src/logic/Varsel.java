package logic;

import java.util.Scanner;

import connect.MultipleConnect;

public class Varsel {
	
	int avtaleID;
	String brukernavn;
	String melding;
	int type;
	String tid;
	MultipleConnect conn;
	static Scanner in = new Scanner(System.in);
	
	public Varsel(int avtaleID, String starttid, int type, String melding, String brukernavn){
		conn = new MultipleConnect();
		String statementAvtale = "insert into varsel(avtaleID, type, tid, melding, brukernavn) values " 
				+ "("+  ("\"")+ avtaleID + ("\",") + ("\"")+ type
				+ ("\",") +("\"")+ starttid + ("\",")+ ("\"")+ melding + ("\",") + ("\"") + brukernavn + ("\")");
		System.out.println(statementAvtale);
		conn.send(statementAvtale);
	}

	public static void main(String[] args) {
		Varsel varsel = new Varsel(36, "2017-03-03 14:45:00", 1,  "hallo på do", "perarne");
	}
}
