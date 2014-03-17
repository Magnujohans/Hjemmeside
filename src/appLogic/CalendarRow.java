package appLogic;
import java.util.ArrayList;

import org.joda.time.DateTime;

import exceptions.DateTimeException;


public class CalendarRow implements Comparable<CalendarRow>{

	/*
	 * 
	 * 
	 *   Ferdig 14.03.2013
	 * 	 (sannsynligvis)
	 * 
	 * 
	 */


	private DateTime start;
	private DateTime end;
	private final Appointment appointment;
//	private ArrayList<Alarm> alarms;

	public CalendarRow(DateTime start, DateTime end, Appointment appointment) throws DateTimeException {
		if(!start.isBefore(end)) {throw new DateTimeException("start has to be before end"); }
		this.start = start;
		this.end = end;
		this.appointment = appointment;
//		this.alarms = new ArrayList<Alarm>();
	}

	public DateTime getStart() {
		return start;
	}

	/* start ma vaere for end */
	public void setStart(DateTime start) throws DateTimeException {
		if (!start.isBefore(this.end)) {throw new DateTimeException("start was set after end"); }
		this.start = start;
	}

	public DateTime getEnd() {
		return end;
	}

	/* end ma vaere etter start */
	public void setEnd(DateTime end) throws DateTimeException {
		if (!end.isAfter(this.start)) {throw new DateTimeException("end was set before start"); }
		this.end = end;
	}

	public Appointment getAppointment() {
		return appointment;
	}

//	public ArrayList<Alarm> getAlarms() {
//		return alarms;
//	}

	/* sjekker om to calendarRow overlapper i tid */
	public boolean isOverlapping(CalendarRow other) {
		//hvis start eller end for other faller innenfor this
		if(this.start.equals(other.start) || this.end.equals(other.end)) return true;
		if (this.start.isBefore(other.start) && this.end.isAfter(other.start)) {return true;}
		if (this.start.isBefore(other.end) && this.end.isAfter(other.end)) {return true;}
		//hvis start eller end for this faller innenfor other
		if (other.start.isBefore(this.start) && other.end.isAfter(this.start)) {return true;}
		if (other.start.isBefore(this.end) && other.end.isAfter(this.end)) {return true;}
		return false;
	}

	/* sorterer calendarRow pa starttid*/
	@Override
	public int compareTo(CalendarRow other) {
		if (this.start.isBefore(other.start)) {return -1;}
		if (this.start.isAfter(other.start)) {return 1;}
		return 0; 
	}




}