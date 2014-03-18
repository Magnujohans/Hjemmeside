package databaseCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
	Connection con;
	
	//public DB(){
		//this.con = this.Connect();
	//}

	public static void main(String[] args) {
		Connection conn  =  null;
		try {
		  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/sebasbr_gruppe_50";
		  String user = "magj_database";
		  String pw = "";
		  conn = DriverManager.getConnection(url,user, pw);
		
		  } catch (SQLException ex) {
		    System.out.println("Tilkobling feilet: "+ex.getMessage());
		  } catch (ClassNotFoundException ex) {
		    System.out.println("Feilet under driverlasting: "+ex.getMessage());
		  } catch (Exception e){
			  System.out.println("Noe gikk galt");
		  }
			finally {
				try {
			      if (conn !=  null) conn.close();
			    } catch (SQLException ex) {
			      System.out.println("Epic fail: "+ex.getMessage());
			    }
			  
	}

}
}
