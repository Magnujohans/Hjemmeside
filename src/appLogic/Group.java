package appLogic;
import java.util.ArrayList;

import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;


public class Group extends User {
	public static ArrayList<Group> groups = new ArrayList<Group>();
	private String name;
	private ArrayList<Employee> members;

	public Group(int id, String name, String email) throws InvalidEmailException, InvalidNameException{
		super(id, email);
		setGroupName(name);
		members = new ArrayList<Employee>();
		groups.add(this);
	}

	public static Group getGroup(int id){
		for(Group g : groups){
			if(g.getId()==id) return g;
		}
		return null;
	}

	public static Group getGroup(String name){
		for(Group g : groups){
			if(g.getName().equals(name)) return g;
		}
		return null;
	}

	private void setGroupName(String name) throws InvalidNameException{
		if(!isValidName(name)) throw new InvalidNameException();
		else this.name = name;
	}

	private boolean isValidName(String name){
		if(name.length()>30) return false;
		for(int i = 0; i < name.length(); i++){
			if(!Character.isLetterOrDigit(name.charAt(i))) return false;
		}
		return true;
	}

	public ArrayList<Employee> getMembers(){
		return members;
	}

	public void addMember(Employee employee) {
		members.add(employee);
	}

	public void removeMember(Employee employee) {
		members.remove(employee);
	}

	public String getName(){
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}