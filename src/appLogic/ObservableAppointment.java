package appLogic;

import org.joda.time.DateTime;

import exceptions.DateTimeException;
import exceptions.RoomBookedException;

public interface ObservableAppointment {

	public void addParticipant(User user);

	public void removeParticipant(User user);

	public void fireDescriptionChanged();

	public void fireRoomChanged();

	public void fireParticipantDeclined(User user);

	public void fireEndChanged() throws DateTimeException, RoomBookedException;

	void fireStartChanged() throws DateTimeException, RoomBookedException;

	void fireAppointmentCancelled();

	void fireAppointmentCreated() throws DateTimeException;	
}