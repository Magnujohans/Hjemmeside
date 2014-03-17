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

	public void loadGroups(){
		ArrayList<HashMap<String,String>> posts = db.get("SELECT * FROM CalendarUser WHERE UType='Group'");
		for(HashMap<String,String> post : posts){
			int id = Integer.parseInt(post.get("UserID"));
			String email = post.get("Email");
			String name = post.get("UName");
			try {
				Group g = new Group(id, name, email);
				loadGroupMembers(g);
			} catch (InvalidNameException e) {
				deleteGroup(id);
			} catch (InvalidEmailException e) {
				deleteGroup(id);
			}
		}
	}

	private void loadGroupMembers(Group g){
		int groupId = g.getId();
		String query = String.format("SELECT (UserID) FROM CalendarUser, Groupmember WHERE UserID=EmpID AND GroupID=%s",groupId);
		ArrayList<HashMap<String,String>> posts = db.get(query);
		for(HashMap<String,String> post : posts){
			int empId = Integer.parseInt(post.get("UserID"));
			Employee e = Employee.getEmployee(empId);
			if(e != null) g.addMember(e);
		}
	}

	private void deleteGroup(int id){
		db.send(String.format("DELETE FROM CalendarUser WHERE UserID = %s", id));
	}
}