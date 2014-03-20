package dbConnection;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;

import appLogic.Employee;

public class DBEmployees {
	Simpleconnect db;

	public DBEmployees(){
		db = new Simpleconnect();
	}

	public ArrayList<Employee> loadEmployees(){
		ArrayList<HashMap<String,String>> posts = db.get("SELECT * FROM Person WHERE Brukertype='Ansatt'");
		ArrayList<Employee> employees = new ArrayList<Employee>();
		for(HashMap<String,String> post : posts){
			int id = Integer.parseInt(post.get("BrukerID"));
			String email = post.get("Epost");
			String name = post.get("Brukernavn");
			try {
				employees.add(new Employee(id, name, email));
			} catch (InvalidNameException e) {
				deleteEmployee(id);
			} catch (InvalidEmailException e) {
				deleteEmployee(id);
			}
		}
		return employees;
	}

	private void deleteEmployee(int id){
		db.send(String.format("DELETE FROM Person WHERE BrukerID = %s", id));
	}
}