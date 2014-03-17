package exceptions;

public class RoomSizeException extends Exception{
	public RoomSizeException(){}
	public RoomSizeException(String message){
		super(message);
	}
}