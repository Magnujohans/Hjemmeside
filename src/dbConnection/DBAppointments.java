package dbConnection;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import appLogic.Appointment;
import appLogic.CalendarRow;
import appLogic.Employee;
import appLogic.Group;
import appLogic.Room;
import appLogic.User;
import dbConnection.*;
import exceptions.BusyUserException;
import exceptions.DateTimeException;
import exceptions.RoomBookedException;
import exceptions.RoomSizeException;


public class DBAppointments {

	Simpleconnect db = new Simpleconnect();

	public int getAppointmentId(Employee leader, DateTime start){
		int leaderId = leader.getId();
		String startTime = dateTimeToString(start);
		String sql = String.format(String.format("SELECT AvtaleID FROM Avtale WHERE OpprettetID='%s' AND StartTid='%s'",leaderId,startTime));
		int appId = Integer.parseInt(db.get(sql).get(0).get("AvtaleID"));
		return appId;
	}

	private String dateTimeToString(DateTime dt){
		return String.format("%s-%s-%s %s:%s:00",
				dt.getYear(), dt.getMonthOfYear(),dt.getDayOfMonth(),dt.getHourOfDay(),dt.getMinuteOfHour());
	}

	public void createParticipant(Appointment a, User u){
		int aId = a.getId();
		int uId = u.getId();
		db.send(String.format("INSERT INTO Invitasjon VALUES ('%s', '%s', '2')",uId,aId));
	}

	public void deleteParticipant(Appointment a, User u) {
		int aId = a.getId();
		int uId = u.getId();
		db.send(String.format("DELETE FROM Invitasjon WHERE (AvtaleID = '%s' AND UserID = '%s')", aId, uId));
	}

	public void createAppiontment(Appointment a) {
		DateTime start = a.getStart();
		DateTime end = a.getEnd();
		String startTime = dateTimeToString(start);
		String endTime = dateTimeToString(end);
		String desc = a.getDescription();
		int lId = a.getLeader().getId();
		db.send(String.format("INSERT INTO Avtale (StartTid,SluttTid,Avtalebeskrivelse,OpprettetID) VALUES ('%s', '%s','%s', '%s')",startTime, endTime, desc, lId));
	}

	public void updateUserStatus (boolean status, Appointment a, User u) {
		int aId = a.getId();
		int uId = u.getId();
		String s = "2";
		if(status==(Boolean)true) s = "1";
		if(status==(Boolean)false) s = "0";
		db.send(String.format("UPDATE Invitasjon SET AvtaleStatus = %s WHERE (AvtaleID = '%s' AND UserID = '%s')", s, aId, uId));
	}

	public void updateAppDesc(Appointment a, String desc) {
		int aId = a.getId();
		db.send(String.format("UPDATE Avtale SET Avtalebeskrivelse = %s WHERE AvtaleID = '%s'", desc, aId));
	}

	public void updateAppointmentStart(Appointment a, DateTime start) {
		int aId = a.getId();
		db.send(String.format("UPDATE Avtale SET StartTid = %s WHERE AvtaleID = '%s'", start, aId));
	}

	public void updateAppointmentEnd(Appointment a, DateTime end) {
		int aId = a.getId();
		db.send(String.format("UPDATE Avtale SET SluttTid = %s WHERE AvtaleID = '%s'", end, aId));
	}

	public void deleteAppointment(Appointment a) {
		int aId = a.getId();
		db.send(String.format("DELETE FROM Avtale WHERE AvtaleID = '%s'", aId));
	}

	private DateTime toDateTime(String datetime){
		String[] temp = datetime.split(" ");
		String[] date = temp[0].split("-");
		String[] time = temp[1].split(":");
		ArrayList<Integer> dt = new ArrayList<Integer>();
		for(int i=0;i<3;i++){
			dt.add(Integer.parseInt(date[i]));
		}
		for(int i=0;i<2;i++){
			dt.add(Integer.parseInt(time[i]));
		}
		return new DateTime(dt.get(0),dt.get(1),dt.get(2),dt.get(3),dt.get(4),0);
	}

	private ArrayList<User> loadAppParticipants(int appId){
		String sql = String.format("SELECT * FROM Invitasjon WHERE AvtaleID=%s",appId);
		ArrayList<HashMap<String,String>> posts = db.get(sql);
		ArrayList<User> participants = new ArrayList<User>();
		for(HashMap<String,String> post : posts){
			int userId = Integer.parseInt(post.get("BrukerID"));
			sql = String.format("SELECT Brukertype FROM Person WHERE BrukerID=%s",userId);
			String type = db.get(sql).get(0).get("Brukertype");

			participants.add((type.equals("Ansatt") ? Employee.getEmployee(userId) : Group.getGroup(userId)));
		}
		return participants;
	}

	private Room loadAppRoom(int appId){
		String sql = String.format("SELECT RomID FROM Reservasjon WHERE AvtaleID=%s",appId);
		ArrayList<HashMap<String,String>> posts = db.get(sql);
		String roomId = posts.get(0).get("RomID");
		return Room.getRoom(roomId);
	}


	public ArrayList<Appointment> loadAppointments(){
		String sql = "SELECT * FROM Avtale";
		ArrayList<HashMap<String,String>> posts =db.get(sql);
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		for(HashMap<String,String> post : posts){
			int appId = Integer.parseInt(post.get("AvtaleID"));
			System.out.println(appId);
			DateTime start = toDateTime(post.get("StartTid").substring(0,16));
			DateTime end = toDateTime(post.get("SluttTid").substring(0,16));
			String description = post.get("Avtalebeskrivelse");
			int leaderId = Integer.parseInt(post.get("OpprettetID"));

			Employee leader = Employee.getEmployee(leaderId);
			Room room = loadAppRoom(appId);
			ArrayList<User> participants = loadAppParticipants(appId);

			try {
				Appointment a = new Appointment(appId, description, room, leader, participants, start, end);
				room.getCalendar().addAppointment(start, end, a);
				loadParticipantsStatus(a);
				appointments.add(a);
			} catch (DateTimeException e) {
				deleteAppointment(appId);
			} catch (RoomBookedException e) {
				deleteAppointment(appId);
			} catch (RoomSizeException e) {
				deleteAppointment(appId);
			} catch (BusyUserException e){
				deleteAppointment(appId);
			}
		}
		System.out.println("Appointments loaded.");
		return appointments;
	}

	private void loadParticipantsStatus(Appointment a){
		int appId = a.getId();
		String sql = String.format("SELECT * FROM Invitasjon WHERE AvtaleID=%s",appId);
		ArrayList<HashMap<String,String>> posts =db.get(sql);
		for(HashMap<String,String> post : posts){
			int userId = Integer.parseInt(post.get("BrukerID"));
			sql =String.format("SELECT Brukertype FROM Person WHERE BrukerID=%s",userId);
			String type = db.get(sql).get(0).get("Brukertype");

			User u = (type.equals("Ansatt") ? Employee.getEmployee(userId) : Group.getGroup(userId));

			if(post.get("Avtalestatus").equals("0")) u.declineAppointment(a);
			else if(post.get("Avtalestatus").equals("1")){
				try {
					u.acceptAppointment(a);
				} catch (BusyUserException e) {
					u.declineAppointment(a);
					updateUserStatus(false, a, u);
				}
			}
			else u.addInvitation(a);
		}
	}

	private void deleteAppointment(int id){
		db.send(String.format("DELETE FROM Avtale WHERE AvtaleID = %s", id));
	}
}