package exceptions;

public class RoomBookedException extends Exception{
	public RoomBookedException(){}
	public RoomBookedException(String message){
		super(message);
	}
}