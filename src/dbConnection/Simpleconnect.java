package dbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Simpleconnect {
	String dbUrl = "jdbc:mysql://mysql.stud.ntnu.no/sebasbr_gruppe_50";
	String dbClass = "com.mysql.jdbc.Driver";
	String brukernavn="sebasbr_database";
	String passord= "heisann";
	//her trenger man sitt eget passord


	public Simpleconnect(){
	}

	//Skal noe sendes til db, bruk sql kommando som message
	public void send(String message){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl,brukernavn,passord);
			Statement stmt = con.createStatement();

			stmt.executeUpdate(message);

			System.out.println("Sendt");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}	
	}


	//Skal du hente bruk get, med sql kommando
	public ArrayList<HashMap<String,String>> get(String message){
		ArrayList<HashMap<String,String>> posts = new ArrayList<HashMap<String,String>>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl,brukernavn,passord);
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(message);

			ResultSetMetaData rsmd = rs.getMetaData();

			int noc = rsmd.getColumnCount();

			while (rs.next()) {	
				HashMap<String,String> post = new HashMap<String, String>();
				for (int i = 1; i <= noc; i++) {
					String colName = String.valueOf(rsmd.getColumnName(i));
					post.put(colName, rs.getString(i));
				}
				posts.add(post);
			}

			con.close();
			return posts;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet rs(String message){
		ResultSet rs=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl,brukernavn,passord);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(message);
			stmt.addBatch(message);


			con.close();
			return rs;

			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				return rs;
			}

			catch(SQLException e) {
				e.printStackTrace();
				return rs;
			}

	}

	public ResultSetMetaData rsmd(String message){
		ResultSetMetaData rsmd=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl,brukernavn,passord);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(message);
			stmt.addBatch(message);
			rsmd = rs.getMetaData();

			con.close();
			return rsmd;

			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				return rsmd;
			}

			catch(SQLException e) {
				e.printStackTrace();
				return rsmd;
			}	
	}
}