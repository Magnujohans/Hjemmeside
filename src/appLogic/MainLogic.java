package appLogic;

import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;

import dbConnection.DBAlarms;
import dbConnection.DBAppointments;
import dbConnection.DBEmployees;
import dbConnection.DBGroups;
import dbConnection.DBRooms;

import exceptions.BusyUserException;
import exceptions.DateTimeException;
import exceptions.InvalidAlarmException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.RoomBookedException;
import exceptions.RoomSizeException;

import appLogic.Appointment;
import appLogic.Employee;
import appLogic.Group;
import appLogic.Room;
import appLogic.User;

public class MainLogic{
	/* I tillegg til alle funksjonene som nevnes underveis
	 * trenger vi funskjoner for å:
	 * - Laste inn alle ansatte
	 * - Laste inn alle grupper
	 * - Laste inn alle appointments og sette statusen til hver bruker i appointmenten lik det som er i AppInvitation
	 * - Laste inn alle rom
	 * - Last inn kalenderene til hver bruker (der hvor brukeren har godtatt avtalen)
	 * - Laste inn kalenderen for hvert rom
	 * - Laste inn alle alarmer for hver ansatt
	 * - Dersom noe ugyldig lastes inn fra databasen (exceptions ved opprettelse av objektet), slett det fra databasen.
	 */
	public static Employee currentUser;
	public boolean loggedIn;
	private DBAlarms dbalarms;
	private DBAppointments dbapps;
	private DBEmployees dbemps;
	private DBGroups dbgroups;
	private DBRooms dbrooms;

	//Hjelpemetode for Â hente ut employee fra gitt streng	
	public MainLogic(){
		currentUser = null;
		loggedIn = false;
		dbalarms = new DBAlarms();
		dbapps = new DBAppointments();
		dbemps = new DBEmployees();
		dbgroups = new DBGroups();
		dbrooms = new DBRooms();
		loadDatabase(); 
	}

	private void loadDatabase(){
		dbemps.loadEmployees();
		dbgroups.loadGroups();
		dbrooms.loadRooms();
		dbapps.loadAppointments();
		dbalarms.loadAlarms();
	}

	public void logInEmployee(Employee e){
		currentUser = e;
	}

	private ArrayList<User> participantsAsEmployees(ArrayList<User> participants){
		ArrayList<User> employees = new ArrayList<User>();
		for(User u : participants){
			if(u instanceof Employee) employees.add(u);
			else{
				Group g = (Group)u;
				for(Employee e : g.getMembers()){
					employees.add(e);
				}
			}
		}
		return employees;
	}

	public void createAppointment(String description, Room room, ArrayList<User> participants, DateTime start, DateTime end){
		try {
			ArrayList<User> employees = participantsAsEmployees(participants);
			Appointment a = new Appointment(description, room, currentUser, employees, start, end);
			dbapps.createAppiontment(a);
			int appId = dbapps.getAppointmentId(currentUser, start);
			a.setId(appId);	
			for(User u : employees){
				addParticipant(a, u);
			}
			room.appointmentCreated(a);
			dbrooms.createRoomBooking(appId, room.getId());
			System.out.println("Avtale opprettet!");
		} catch (DateTimeException e) {
			System.out.println("Ugyldig tidsrom");
		} catch (RoomBookedException e) {
			System.out.println("Rommet er allerede booket.");
		} catch (RoomSizeException e) {
			System.out.println("Rommet er ikke stort nok.");
		} catch (BusyUserException e) {
			System.out.println("Du er ikke ledig i dette tidsrommet.");
		}	
	}

	public void createAppointment(String description, Room room, ArrayList<User> participants, DateTime start, DateTime end, Employee leader){
		try {
			ArrayList<User> employees = participantsAsEmployees(participants);
			Appointment a = new Appointment(description, room, leader, employees, start, end);
			dbapps.createAppiontment(a);
			int appId = dbapps.getAppointmentId(leader, start);
			a.setId(appId);	
			for(User u : employees){
				addParticipant(a, u);
			}
			room.appointmentCreated(a);
			dbrooms.createRoomBooking(appId, room.getId());
			System.out.println("Avtale opprettet!");
		} catch (DateTimeException e) {
			System.out.println("Ugyldig tidsrom");
		} catch (RoomBookedException e) {
			System.out.println("Rommet er allerede booket.");
		} catch (RoomSizeException e) {
			System.out.println("Rommet er ikke stort nok.");
		} catch (BusyUserException e) {
			System.out.println("Du er ikke ledig i dette tidsrommet.");
		}	
	}

	private void addParticipant(Appointment a, User u){
		a.addParticipant(u);
		dbapps.createParticipant(a, u);
	}

	public void acceptAppointment(Appointment a){
		try {
			currentUser.acceptAppointment(a);
			dbapps.updateUserStatus(true, a, currentUser);
		} catch (BusyUserException e) {
			System.out.println("Du kan ikke godta denne invitasjonen fordi du er opptatt på dette tidspunktet.");
			declineAppointment(a);
		}
	}

	public void declineAppointment(Appointment a){
		if(!currentUser.equals(a.getLeader())){
			currentUser.declineAppointment(a);
			dbapps.updateUserStatus(false, a, currentUser);
		}
	}

	public void cancelAppointment(Appointment a){
		a.fireAppointmentCancelled();
		dbapps.deleteAppointment(a);
	}

	public void addAlarm(Appointment a, int offset, String label){
		try {
			currentUser.addAlarm(new Alarm(label,a,offset));
			dbalarms.addAlarm(a.getId(), currentUser.getId(), offset, label);
			System.out.println("Alarm lagt til!");
		} catch (InvalidAlarmException e) {
			System.out.println("Ugyldig alarm for denne avtalen.");
		}

	}

	public void deleteAlarm(Alarm a){
		currentUser.removeAlarm(a);
		dbalarms.deleteAlarm(currentUser.getId(),a.getAppointment().getId(),a.getOffset());
	}
	
	public static void main(String[] args) {
		
	}
}