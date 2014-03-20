package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import connect.MultipleConnect;



public class Rom {
	MultipleConnect conn = new MultipleConnect();
	Scanner in = new Scanner(System.in);
	int avtaleID;
	int antallDeltakere;
	long starttid;
	long sluttid;
	
	public Rom (int avtaleID){
		String statement = "select starttid,sluttid from avtale where avtaleID=" + "\"" + avtaleID + "\"";
		String starttidString = conn.get(statement).get(0).get("starttid");
		String sluttidString = conn.get(statement).get(0).get("sluttid");
		starttid = tidTilTall(starttidString);
		sluttid = tidTilTall(sluttidString);
		String Deltakere = "select brukernavn from deltar where avtaleID= "+ "\"" + avtaleID + "\"";
		antallDeltakere = conn.get(Deltakere).size();
		finnLedigRom();
	}
	
	
	private void finnLedigRom() {
		String statement = "select romID from rom";
		ArrayList<HashMap<String,String>> rom = conn.get(statement);
		ArrayList<Integer> ledig = new ArrayList<Integer>();
		for (HashMap<String,String> romID: rom){
			int romIdInt = Integer.parseInt(romID.get("romID"));
			if (isLedig(romIdInt)) ledig.add(romIdInt);
		}
		for (int romnummer:ledig){
			if (!isRiktigSt�rrelse(romnummer)) ledig.remove(romnummer);
		}
		System.out.println(ledig);
	}


	//m� testes, tror virker, kj�rer uten feil, problemer med long
	public boolean isLedig(int romID){
		ArrayList<ArrayList<Long>> opptatt = hentOpptatttid(romID);
		for (ArrayList<Long>intervall: opptatt){
			long opptattStart = intervall.get(0);
			long opptattSlutt = intervall.get(1);
			if (isEtter(starttid, opptattStart) && (isEtter(opptattSlutt,starttid))) return false;
			if (isEtter(sluttid, opptattStart) && (isEtter(opptattSlutt,sluttid))) return false;
		}
		return true;
	}
	
	private ArrayList<ArrayList<Long>> hentOpptatttid(int romID) {
		String hentOpptatttid = "Select starttid, sluttid from avtale where rom= " + romID;
		ArrayList<HashMap<String,String>> tider = conn.get(hentOpptatttid);
		ArrayList<ArrayList<Long>> opptatt = new ArrayList<ArrayList<Long>>();
		for(HashMap<String,String>tid:tider){
			long starttid = tidTilTall(tid.get("starttid"));
			long sluttid = tidTilTall(tid.get("sluttid"));
			ArrayList<Long> mini = new ArrayList<Long>();
			mini.add(starttid);
			mini.add(sluttid);
			opptatt.add(mini);
		}
		return opptatt;	
	}

	private long tidTilTall(String tid){
		tid = tid.replaceAll("-", "");
		tid = tid.replace(":", "");
		tid=tid.replaceAll("\\s+", "");
		tid = tid.replace(".", "");
		long tidInt = Long.parseLong(tid);
		return tidInt;
	}
	
	private boolean isEtter(long tid1, long tid2){ //returnerer true om tid 1 er etter tid 2
		return (tid1-tid2)>0;
	}
	
	public boolean isRiktigSt�rrelse(int romID){
		String hentSt�rrelse = "select st�rrelse from rom where romID=" + romID;
		int st�rrelse = Integer.parseInt(conn.get(hentSt�rrelse).get(0).get("st�rrelse"));
		return (st�rrelse>=antallDeltakere);
	}

}
