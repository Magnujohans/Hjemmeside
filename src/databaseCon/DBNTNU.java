package databaseCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBNTNU {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Connection con  =  null;
		try {
		  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/sebasbr_gruppe_50";
		  String user = "magj_database";
		  String pw = "";
		  Connection conn = DriverManager.getConnection(url,user, pw);
		  System.out.println("Tilkoblingen fungerte.");
		  
		  ArrayList<String> usernames = new ArrayList<String>();
		  Statement statement = conn.createStatement();
			ResultSet resultset = statement.executeQuery("select * from person;");
			while (resultset.next()) {
				String username = resultset.getString("brukernavn");
				usernames.add(username);
			}	
		System.out.println(usernames);
		
		  } catch (SQLException ex) {
		    System.out.println("Tilkobling feilet: "+ex.getMessage());
		  } catch (ClassNotFoundException ex) {
		    System.out.println("Feilet under driverlasting: "+ex.getMessage());
		  } catch (Exception e){
			  System.out.println("Noe gikk galt");
		  }
			finally {
		    try {
		      if (con !=  null) con.close();
		    } catch (SQLException ex) {
		      System.out.println("Epic fail: "+ex.getMessage());
		    }
		  }
		
	}
}

/*	public ArrayList<String> getUserNames() {
		try {
			Connection con = DriverManager.getConnection(host + "?user=" + uName + "&password=" + uPass);
			Statement statement = con.createStatement();
			ResultSet resultset = statement.executeQuery("select * from person;");
			while (resultset.next()) {
				String username = resultset.getString("brukernavn");
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usernames;

	
	
	}
*/

