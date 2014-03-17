package exceptions;

public class InvalidAlarmException extends Exception{
	public InvalidAlarmException(){}
	public InvalidAlarmException(String message){
		super(message);
	}
}