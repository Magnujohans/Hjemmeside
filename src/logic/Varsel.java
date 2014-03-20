package logic;

import java.util.Scanner;

import connect.MultipleConnect;

public class Varsel {
	int varselid;
	int avtaleid;
	String brukernavn;
	String melding;
	int sett;
	String tid;
	MultipleConnect conn;
	static Scanner in = new Scanner(System.in);
	
	
	public Varsel(int varselID, int avtaleID,int sett, String starttid, String melding, String brukernavn){
		conn = new MultipleConnect();
		this.varselid = varselID;
		this.avtaleid = avtaleID;
		this.sett = sett;
		this.tid = starttid;
		this.melding = melding;
		this.brukernavn = brukernavn;
		
	}

	
	public void alarmenErSett(int varselid){
		String statement= (String.format("UPDATE varsel SET sett=%s WHERE varselID=%s", 1, varselid));
		System.out.println(statement);
		conn.send(statement);
	}
	public int getVarselID(){
		return varselid;
	}
	
}
