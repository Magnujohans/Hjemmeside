package appLogic;
import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.DateTime;

import exceptions.DateTimeException;


public class Calendar implements Iterable<CalendarRow>{

	/*
	 * 
	 * 
	 *   Ferdig 15.03.2013
	 * 	 (sannsynligvis)
	 * 
	 * 
	 */


	private ArrayList<CalendarRow> calendar;

	public Calendar() {
		calendar = new ArrayList<CalendarRow>();
	}

	public ArrayList<CalendarRow> getCalendar() {
		return calendar;
	}

	public void addCalendarRow(CalendarRow cr) {
		if(findCalendarRow(cr.getAppointment())==null) calendar.add(cr);
	}

	/* returnerer alle calendarRow som har starttid tilsvarende weekNumber */
	public ArrayList<CalendarRow> getWeekCalendar(int weekNumber) {
		ArrayList<CalendarRow> week = new ArrayList<CalendarRow>();
		for (CalendarRow row: calendar) {
			if (row.getStart().getWeekOfWeekyear() == weekNumber) {
				week.add(row);
			}
		}
		return week;
	}

	/* oppretter ny CalendarRow og legger inn i calendar */
	public void addAppointment(DateTime start, DateTime end, Appointment appointment) throws DateTimeException {
		if (end.isBefore(start)) {throw new DateTimeException("end is before start"); }
		if(findCalendarRow(appointment)==null) calendar.add(new CalendarRow(start, end, appointment)); 
	}

	/* fjerner CalendarRow som er assosiert med gitt appointment */
	public void removeCalendarRow(Appointment appointment) {
		int removeThis = -1;
		for (int i = 0; i < calendar.size(); i++) {
			if (calendar.get(i).getAppointment().equals(appointment)){
				removeThis = i;
			}
		}
		if (removeThis > -1) {calendar.remove(removeThis);}
	}

	/* returnerer CalendarRow som har gitt start, ellers null */
	public CalendarRow findCalendarRow(DateTime start) {
		for (CalendarRow row : calendar) {
			if (row.getStart().equals(start)) {
				return row; 
			}
		}
		return null;
	}

	/* returnerer CalendarRow som har gitt appointment, ellers null */
	public CalendarRow findCalendarRow(Appointment appointment) {
		for (CalendarRow row : calendar) {
			if (row.getAppointment().equals(appointment)) {
				return row; 
			}
		}
		return null;
	}

	@Override
	public Iterator<CalendarRow> iterator() {
		return calendar.iterator();
	}

}