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
		db.send(String.format("UPDATE Reservasjon SET RomID=%s WHERE AvtaleID=%s",roomId,appId));	
	}

	public void createRoomBooking(int appId, String roomId){
		db.send(String.format("INSERT INTO Reservasjon VALUES ('%s','%s')",appId,roomId));
	}

	public ArrayList<Room> loadRooms(){
		ArrayList<Room> rooms = new ArrayList<Room>();
		ArrayList<HashMap<String,String>> posts = db.get("SELECT * FROM Rom ");
		for(HashMap<String,String> post : posts){
			String romid = (post.get("RomID"));
			int size = Integer.parseInt(post.get("Storrelse"));
			rooms.add(new Room(romid, size));
		}
		return rooms;
	}
}