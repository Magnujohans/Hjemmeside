package appLogic;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import exceptions.InvalidAlarmException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;

public class Employee extends User{
	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	private String firstname;
	private String lastname;
	private ArrayList<Alarm> alarms;


	public Employee(int id, String name, String email) throws InvalidNameException, InvalidEmailException{
		super(id, email);
		setEmployeeName(name);
		alarms = new ArrayList<Alarm>();
		employees.add(this);
	}

	public static Employee getEmployee(int id){
		for(Employee e : employees){
			if(e.getId()==id) return e;
		}
		return null;
	}

	public static Employee getEmployee(String name){
		for(Employee e : employees){
			if(e.toString().equals(name)) return e;
		}
		return null;
	}

	private void setEmployeeName(String name) throws InvalidNameException{
		if(!isValidName(name)) throw new InvalidNameException();
		else{
			String[] fullname = name.split(" ");
			firstname = fullname[0];
			lastname = "";
			for(int i = 1; i<fullname.length; i++){
				lastname+=fullname[i];
				lastname+=" ";
			}
			lastname.trim();
		}	
	}

	public String getFirstName(){
		return firstname;
	}

	public String getLastname(){
		return lastname;
	}

	public ArrayList<Alarm> getAlarms(){
		return alarms;
	}

	public void addAlarm(Alarm alarm) throws InvalidAlarmException{
		if(!isValidAlarm(alarm)) throw new InvalidAlarmException();
		else alarms.add(alarm);
	}

	public void removeAlarm(Alarm alarm){
		if(alarms.contains(alarm)) alarms.remove(alarm);
	}

	public boolean isValidAlarm(Alarm alarm){
		if(alarms.contains(alarm)) return false;
		for(Alarm a : alarms){
			if(a.isEqual(alarm)) return false;
		}
		return true;
	}

	private boolean isValidName(String name){
		if(name.length()>30) return false;
		String[] fullname = name.split(" ");
		if(fullname.length>=2){
			for(String s : fullname){
				s.trim();
				for(int i = 0; i < s.length(); i++){
					if(!Character.isLetter(s.charAt(i))) return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean containsAlarm(Alarm other) {
		for (Alarm a : alarms) {
			if (a.isEqual(other)) {return true;}
		}
		return false; 
	}

	public void removeAlarm(String description) {
		int removeThis = -1;
		for (int i = 0; i < alarms.size(); i++) {
			if (alarms.get(i).getAppointment().getDescription().equals(description)) {
				removeThis = i;
			}
		}
		if (removeThis > -1 ) {alarms.remove(removeThis);}
	}

	public Alarm getAlarm(String alarmtext){
		for(Alarm a : alarms){
			if(a.toString().equals(alarmtext)) return a;
		}
		return null;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
}