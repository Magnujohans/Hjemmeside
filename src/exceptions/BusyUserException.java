package exceptions;

public class BusyUserException extends Exception{
	public BusyUserException(){}
	public BusyUserException(String message){
		super(message);
	}
}