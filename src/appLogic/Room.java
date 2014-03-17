package appLogic;
import java.util.ArrayList;

import org.joda.time.DateTime;

import exceptions.DateTimeException;
import exceptions.RoomBookedException;


public class Room implements AppointmentListener{
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	private final String id;
	private final int size;
	private Calendar room_calendar;

	public Room(String id, int size) {
		this.size = size;
		this.id = id;
		this.room_calendar = new Calendar();
		rooms.add(this); 
	}


	@Override
	public String toString() {
		return id;

	}


	public String getId() {
		return id;
	}

	public int getSize() {
		return this.size;
	}

	public Calendar getCalendar() {
		return this.room_calendar;
	}

	/* -- endret input til calendarRow --
	 * gar gjennom alle innslagene i rommets kalender og sjekker om row
	 * krasjer med noen av de andre
	 */
	public boolean isBooked(CalendarRow row) {
		for (CalendarRow other : room_calendar)
			if(row.isOverlapping(other))
				return true;
		return false;
	}

	public static ArrayList<Room> getFreeRooms(DateTime start, DateTime end, int minSize) throws DateTimeException {
		ArrayList<Room> freeRooms = new ArrayList<Room>();
		CalendarRow time_slot = new CalendarRow(start, end, null);
		for (Room room : rooms) {
			if (!room.isBooked(time_slot) && room.getSize() >= minSize) {
				freeRooms.add(room);
			}
		}
		return freeRooms;
	}

	public static Room getRoom(String id){
		for(Room r : rooms){
			if(r.getId().equals(id)) return r;
		}
		return null;
	}

	@Override
	public void appointmentCreated(Appointment appointment) throws DateTimeException {
		room_calendar.addAppointment(appointment.getStart(), appointment.getEnd(), appointment);
	}


	@Override
	public void startChanged(Appointment appointment, DateTime start) throws DateTimeException, RoomBookedException {
		CalendarRow row = room_calendar.findCalendarRow(appointment);
		if(!row.equals(null)){
			DateTime end = row.getEnd();
			if(isBooked(new CalendarRow(start,end,null))) throw new RoomBookedException();
			else row.setStart(start);
		}
	}

	@Override
	public void endChanged(Appointment appointment, DateTime end) throws DateTimeException, RoomBookedException {
		CalendarRow row = room_calendar.findCalendarRow(appointment);
		if(!row.equals(null)){
			DateTime start = row.getStart();
			if(isBooked(new CalendarRow(start,end,null))) throw new RoomBookedException();
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
		room_calendar.removeCalendarRow(appointment);	
	}
}