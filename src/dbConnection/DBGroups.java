package dbConnection;

import java.util.ArrayList;
import java.util.HashMap;
 
import appLogic.Employee;
import appLogic.Group;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;

public class DBGroups {
	Simpleconnect db;

	public DBGroups(){
		db = new Simpleconnect();
	}

	public ArrayList<Group> loadGroups(){
		ArrayList<Group> groups = new ArrayList<Group>();
		ArrayList<HashMap<String,String>> posts = db.get("SELECT * FROM Person WHERE Brukertype='Group'");
		for(HashMap<String,String> post : posts){
			int id = Integer.parseInt(post.get("BrukerID"));
			String email = post.get("Epost");
			String name = post.get("Brukernavn");
			try {
				
				Group g = new Group(id, name, email);
				groups.add(g);
				loadGroupMembers(g);
			} catch (InvalidNameException e) {
				deleteGroup(id);
			} catch (InvalidEmailException e) {
				deleteGroup(id);
			}
		}
		return groups;
	}

	private void loadGroupMembers(Group g){
		int groupId = g.getId();
		String query = String.format("SELECT (BrukerID) FROM Person, medlem_av WHERE BrukerID=EmpID AND GroupID=%s",groupId);
		ArrayList<HashMap<String,String>> posts = db.get(query);
		for(HashMap<String,String> post : posts){
			int empId = Integer.parseInt(post.get("BrukerID"));
			Employee e = Employee.getEmployee(empId);
			if(e != null) g.addMember(e);
		}
	}

	private void deleteGroup(int id){
		db.send(String.format("DELETE FROM Person WHERE BrukerID = %s", id));
	}
}