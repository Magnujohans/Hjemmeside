package dbConnection;

import java.util.ArrayList;
import java.util.HashMap;


import appLogic.Appointment;
import appLogic.Alarm;
import appLogic.Employee;
import appLogic.Room;
import exceptions.DateTimeException;
import exceptions.InvalidAlarmException;

import org.joda.time.DateTime;

import appLogic.Room;
public class DBAlarms {
	private Simpleconnect db;

	public DBAlarms(){
		db = new Simpleconnect();
	}

	public void loadAlarms(){
		for(Employee e : Employee.employees){
			int empId = e.getId();
			String sql = String.format("SELECT * FROM Varsel WHERE AnsattID=%s", empId);
			ArrayList<HashMap<String,String>> posts = db.get(sql);

			for(HashMap<String,String> post : posts){
				String label=(post.get("Varselbeskrivelse"));
				int offset=Integer.parseInt(post.get("Varseltidspunkt"));
				int appId = Integer.parseInt(post.get("AvtaleID"));
				Appointment a = e.getAppointment(appId);
				if(a!=null){
					Alarm alarm = new Alarm(label, a, offset);
					try {
						e.addAlarm(alarm);
					} catch (InvalidAlarmException e1) {
						e.removeAlarm(alarm);
						deleteAlarm(empId,appId,offset);
					}
				}
			}
		}
	}
	
	public ArrayList<Alarm> loadPersonalAlarms(Employee emp){
			int empId = emp.getId();
			ArrayList<Alarm> alarms= new ArrayList<Alarm>();
			String sql = String.format("SELECT * FROM Varsel WHERE AnsattID=%s", empId);
			ArrayList<HashMap<String,String>> posts = db.get(sql);

			for(HashMap<String,String> post : posts){
				String label=(post.get("Varselbeskrivelse"));
				int offset=Integer.parseInt(post.get("Varseltidspunkt"));
				int appId = Integer.parseInt(post.get("AvtaleID"));
				Appointment a = emp.getAppointment(appId);
				if(a!=null){
					Alarm alarm = new Alarm(label, a, offset);
					try {
						emp.addAlarm(alarm);
						alarms.add(alarm);
					} catch (InvalidAlarmException e1) {
						emp.removeAlarm(alarm);
						deleteAlarm(empId,appId,offset);
					}
				}
			}
			return alarms;
		}

	public void deleteAlarm(int empId, int appId, int offset){
		db.send(String.format("DELETE FROM Varsel WHERE (AvtaleID = %s AND AnsattID=%s AND Varseltidspunkt=%s)", appId,empId,offset));
	}

	public void addAlarm(int appId, int empId, int offset, String label) {
		db.send(String.format("INSERT INTO Varsel VALUES ('%s','%s','%s','%s')",appId,empId,offset,label));
	}
}