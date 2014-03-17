package appLogic;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import exceptions.BusyUserException;
import exceptions.DateTimeException;
import exceptions.InvalidEmailException;
import exceptions.RoomBookedException;

public class User implements AppointmentListener {
	private final int id;
	private String email;
	private Calendar calendar;  
	private ArrayList<Appointment> invitations;
	//Disse brukes for Œ validere strenger
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public User(int id, String email) throws InvalidEmailException{
		setEmail(email);
		this.id = id;
		calendar = new Calendar();
		invitations = new ArrayList<Appointment>();
	}

	public Appointment getAppointment(String description) {
		for(CalendarRow cr : calendar){
			if(cr.getAppointment().getDescription().equals(description)) return cr.getAppointment();
		}
		return null;
	}

	public Appointment getInvitation(String description) {
		for(Appointment app : invitations){
			if(app.getDescription().equals(description)) return app;
		}
		return null;
	}

	public void addInvitation(Appointment a){
		if(!invitations.contains(a)) invitations.add(a);
	}

	public Appointment getAppointment(int id) {
		for(CalendarRow cr : calendar){
			if(cr.getAppointment().getId()==id) return cr.getAppointment();
		}
		return null;
	}

	private void setEmail(String email) throws InvalidEmailException{
		if(!isValidEmail(email)) throw new InvalidEmailException();
		else this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public ArrayList<Appointment> getInvitations () {
		return this.invitations;
	}

	public int getId(){
		return id;
	}

	public void acceptAppointment(Appointment appointment) throws BusyUserException {
		if(appointment.getParticipantStatus(this)!=(Boolean)true){
			try {
				if(isBusy(new CalendarRow(appointment.getStart(),appointment.getEnd(),null)))
					throw new BusyUserException();
				appointment.setParticipantStaus(this, true);
			} catch (DateTimeException e1) { }
			if(invitations.contains(appointment)) invitations.remove(appointment);
			try {
				calendar.addAppointment(appointment.getStart(), appointment.getEnd(), appointment);
			} catch (DateTimeException e) {}
		}
	}

	public void declineAppointment(Appointment appointment) {
		if(appointment.getParticipantStatus(this)==(Boolean)true){
			calendar.removeCalendarRow(appointment);
		}
		appointment.setParticipantStaus(this, false);
		if(invitations.contains(appointment)) invitations.remove(appointment);
	}

	private boolean isValidEmail(String email){
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches() && email.length()<=40;
	}

	public boolean isBusy(CalendarRow row) {
		for (CalendarRow other : calendar)
			if(row.isOverlapping(other))
				return true;
		return false;
	}

	@Override
	public void appointmentCreated(Appointment appointment) throws DateTimeException, BusyUserException {
		if(isBusy(new CalendarRow(appointment.getStart(),appointment.getEnd(),null))) throw new BusyUserException();
		invitations.add(appointment);
	}

	@Override
	public void startChanged(Appointment appointment, DateTime start) throws DateTimeException, BusyUserException {
		CalendarRow row = calendar.findCalendarRow(appointment);
		if(!row.equals(null)){
			DateTime end = row.getEnd();
			if(isBusy(new CalendarRow(start,end,null))) throw new BusyUserException();
			else row.setStart(start);
		}
	}

	@Override
	public void endChanged(Appointment appointment, DateTime end) throws DateTimeException, BusyUserException {
		CalendarRow row = calendar.findCalendarRow(appointment);
		if(!row.equals(null)){
			DateTime start = row.getStart();
			if(isBusy(new CalendarRow(start,end,null))) throw new BusyUserException();
			else row.setEnd(end);
		}
	}

	@Override
	public void descriptionChanged(Appointment appointment) {
		// TODO Auto-generated method stub
	}


	@Override
	public void roomChanged(Appointment appointment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void participantDeclined(Appointment appointment, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void appointmentCancelled(Appointment appointment) {
		calendar.removeCalendarRow(appointment);
	}
}