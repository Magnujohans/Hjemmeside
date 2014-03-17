package dbConnection;

import java.util.ArrayList;
import java.util.HashMap;
import appLogic.*;

import appLogic.Group;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;

public class DBRooms {
	public Simpleconnect db;

	public DBRooms(){
		db = new Simpleconnect();
	}

	public void updateAppointmentRoom(int appId,String roomId){	
		db.send(String.format("UPDATE Booking SET RoomID=%s WHERE AppID=%s",roomId,appId));	
	}

	public void createRoomBooking(int appId, String roomId){
		db.send(String.format("INSERT INTO Booking VALUES ('%s','%s')",appId,roomId));
	}

	public void loadRooms(){
		ArrayList<HashMap<String,String>> posts = db.get("SELECT * FROM Room ");
		for(HashMap<String,String> post : posts){
			String romid = (post.get("RoomID"));
			int size = Integer.parseInt(post.get("Size"));
			new Room(romid, size);
		}
	}
}