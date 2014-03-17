package gui;

import java.awt.Choice;
import java.awt.Font;
import java.awt.TextArea;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;

import appLogic.Alarm;
import appLogic.CalendarRow;
import appLogic.MainLogic;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Color;

public class ViewAlarmsPanel extends JPanel {

	public JButton btnBack;
	public JButton btnDeleteAlarm;
	public Choice choice;
	public TextArea textArea;

	/**
	 * Create the panel.
	 */
	public ViewAlarmsPanel() {
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

		JLabel lblKalenderUke = new JLabel("Alarmer");
		lblKalenderUke.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblKalenderUke, "4, 2, left, default");

		textArea = new TextArea();
		add(textArea, "4, 4, 3, 1");
		textArea.setEditable(false);

		choice = new Choice();
		add(choice, "4, 6");
		choice.add("Velg alarm..."); 

		btnDeleteAlarm = new JButton("Slett");
		btnDeleteAlarm.setBackground(Color.RED);
		add(btnDeleteAlarm, "6, 6");

		btnBack = new JButton("Tilbake");
		add(btnBack, "4, 8, 3, 1");


	}

	public void showAlarms() {
		textArea.setText("");		
		for (Alarm alarm : MainLogic.currentUser.getAlarms() ) {			
			textArea.append(alarm.toString()+"\n");					
		}
	}

	public void addChoices() {
		choice.removeAll();
		choice.add("Velg alarm...");
		for (Alarm alarm : MainLogic.currentUser.getAlarms() ) {
			choice.add(alarm.toString()); 
		}
	}
}