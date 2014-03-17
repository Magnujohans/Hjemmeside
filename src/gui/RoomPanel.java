package gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;

import appLogic.Appointment;
import appLogic.Room;
import appLogic.User;
import appLogic.Employee;
import appLogic.Group;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import exceptions.DateTimeException;

public class RoomPanel extends JPanel {

	public JButton btnBack;
	public JButton btnChooseRoom;
	public Choice choice;
	public TextArea textArea;
	public String description;
	public DateTime inStart;
	public DateTime inEnd; 
	public ArrayList<User> participants; 
	public boolean editedAppointment = false;

	/**
	 * Create the panel.
	 */
	public RoomPanel(String description, DateTime start, DateTime end, ArrayList<User> participants) {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("104dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("39dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(118dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		this.description = description;
		this.inStart = start;
		this.inEnd = end; 
		this.participants = participants; 

		JLabel lblKalenderUke = new JLabel("Velg rom");
		lblKalenderUke.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblKalenderUke, "4, 2, left, default");

		textArea = new TextArea();
		add(textArea, "4, 4, 3, 1");
		textArea.setEditable(false);

		choice = new Choice();
		add(choice, "4, 6");

		btnChooseRoom = new JButton("Velg");
		btnChooseRoom.setBackground(Color.GREEN);
		add(btnChooseRoom, "6, 6");

		btnBack = new JButton("Tilbake");
		add(btnBack, "4, 8, 3, 1");


	}

	public void showAvailableRooms(Room currentRoom) {
		textArea.setText("");
		choice.removeAll();
		choice.add("Velg rom...");
		ArrayList<Room> freeRooms = null; 
		try {
			freeRooms = Room.getFreeRooms(inStart, inEnd, getEmployeeCount());
			freeRooms.add(currentRoom);
			System.out.println("Rom lastet inn vellykket");
		} catch (DateTimeException e) {
			System.out.println("Noe gikk galt");
		}
		for (Room room : freeRooms) {
			//Vis i tekstområde
			String id = room.getId();
			String size = String.valueOf(room.getSize());			
			textArea.append("Rom: " + id + "\nStorrelse: " + size + "\n\n");
			//legg til valg
			choice.add(id); 
		}
	}

	public void showAvailableRooms() {
		textArea.setText("");
		choice.removeAll();
		choice.add("Velg rom...");
		ArrayList<Room> freeRooms = null; 
		try {
			freeRooms = Room.getFreeRooms(inStart, inEnd, getEmployeeCount());
			System.out.println("Rom lastet inn vellykket");
		} catch (DateTimeException e) {
			System.out.println("Noe gikk galt");
		}
		for (Room room : freeRooms) {
			//Vis i tekstområde
			String id = room.getId();
			String size = String.valueOf(room.getSize());			
			textArea.append("Rom: " + id + "\nStorrelse: " + size + "\n\n");
			//legg til valg
			choice.add(id); 
		}
	}

	private int getEmployeeCount(){
		int count = 0;
		for(User u : participants){
			if(u instanceof Employee) count+=1;
			else{
				Group g = (Group)u;
				count+=g.getMembers().size();
			}

		}
		//+1 for leder
		if(editedAppointment) return count;
		else return count+1;
	}
}