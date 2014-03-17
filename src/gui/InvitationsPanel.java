package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import appLogic.Appointment;
import appLogic.CalendarRow;
import appLogic.MainLogic;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;

import org.joda.time.DateTime;

import java.awt.Choice;
import java.awt.Font;
import java.awt.TextArea;
import java.util.Collections;

public class InvitationsPanel extends JPanel {

	public JButton btnBack;
	public JButton btnChooseAppointment;
	public Choice choice;
	public TextArea textArea;


	/**
	 * Create the panel.
	 */
	public InvitationsPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("104dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("41dlu"),
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




		JLabel lblKalenderUke = new JLabel("Invitasjoner");
		lblKalenderUke.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblKalenderUke, "4, 2, right, default");

		textArea = new TextArea();
		add(textArea, "4, 4, 3, 1");
		textArea.setEditable(false);

		choice = new Choice();
		add(choice, "4, 6");
		choice.add("Velg invitasjon..."); 

		btnChooseAppointment = new JButton("Velg");
		add(btnChooseAppointment, "6, 6");

		btnBack = new JButton("Tilbake");
		add(btnBack, "4, 8, 3, 1");


	}

	public void showInvitations() {
		textArea.setText("");
		Collections.sort(MainLogic.currentUser.getInvitations());
		for (Appointment app : MainLogic.currentUser.getInvitations()) {
			String desc = app.getDescription();
			String room = app.getRoom().toString();
			String start = app.getStart().toString();
			String end = app.getEnd().toString();
			textArea.append(">>> " + desc + " <<<\n"
							+ "Sted: " + room + "\n"
							+ "Dato: " + start.substring(0, 10) + "\n"
							+ "Tid: " + start.subSequence(11, 16) + " - " + end.subSequence(11, 16)
							+ "\n\n");
		}
	}

	public void addChoices() {
		choice.removeAll();
		choice.add("Velg invitasjon...");
		for (Appointment app : MainLogic.currentUser.getInvitations()) {
			String desc = app.getDescription();
			choice.add(desc); 
		}
	}
}