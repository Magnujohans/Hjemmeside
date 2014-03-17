package appLogic;

import org.joda.time.DateTime;

public class Alarm {
	private final DateTime time;
	private String label;
	private final Appointment appointment;
	private final int offset;

	public Alarm(String label, Appointment appointment,int offset) {
		this.appointment=appointment;
		this.time = this.appointment.getStart().minusMinutes(offset);
		this.offset=offset;
		setLabel(label);
	}

	public int getOffset(){
		return this.offset;
	}

	public String getLabel() {
		return this.label;
	}

	public DateTime getTime() {
		return this.time;
	}

	public Appointment getAppointment(){
		return this.appointment;
	}

	/* sorger for at label ikke blir lenger enn 100 tegn */
	public void setLabel(String label) {
		this.label = ( label.length() < 100 ? label : label.substring(0, 99) ); 
	}

	public void fireAlarm() {
		//TODO: "Noe"
	}


	public boolean isEqual(Alarm other) {
		return (this.appointment.equals(other.appointment) && this.getOffset()==other.getOffset() ? true : false);
	}

	public String toString(){
		int hour = time.getHourOfDay();
		String h = hour > 9 ? String.valueOf(hour) : "0"+String.valueOf(hour);
		int mins = time.getMinuteOfHour();
		String m = mins > 9 ? String.valueOf(mins) : "0"+String.valueOf(mins);
		return String.format("%s:%s - %s (%s)",h,m,label,getAppointment().getDescription());
	}
}
