package databaseCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Client {

	DB db;
	Connection con;
	String PersonID;
	
	boolean login = false;
	
	public void Login(String username, String pass) throws SQLException{
		this.db = new DB();
		this.con = db.con;
		con.setAutoCommit(false);
		
		String loginString = "Select brukernavn, Passord from person where brukernavn = ? and Passord = ?;";
		PreparedStatement statement = con.prepareStatement(loginString);
		statement.setString(1, username);
		statement.setString(2, pass);
		ResultSet resultset = statement.executeQuery();
		con.commit();
		if (resultset.next()) {
			this.PersonID = username;
			this.login = true;
			System.out.println("Successfully logged in");
		}	
		else{
			System.out.println("Feil brukernavn eller passord");
		}
		
		
	}
	public void addAppointment(String startD, String sluttD, String startT, String sluttT, int varighet,String sted, String Beskrivelse) throws SQLException{
		con.setAutoCommit(false);
		
		String appoint = "insert into avtale(startdato,sluttdato,starttidspunkt,slutttidspunkt,varighet,sted,beskrivelse) values(?,?,?,?,?,?,?);";
		PreparedStatement statement = con.prepareStatement(appoint);
		statement.setString(1, startD);
		statement.setString(2,sluttD);
		statement.setString(3, startT);
		statement.setString(4, sluttT);
		statement.setInt(5, varighet);
		statement.setString(6, sted);
		statement.setString(7, Beskrivelse);
		statement.executeUpdate();
		con.commit();
		
	}
	
	
	
	
	public static void main(String[] args) {
		Client client = new Client();
		try{
			client.Login("1234","qwerty");
			client.addAppointment("2014-11-11", "2014-11-11", "20:15:00", "20:50:00", 0, "", "");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	

}
