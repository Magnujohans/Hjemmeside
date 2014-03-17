package gui;

import javax.swing.JPanel;

import appLogic.Appointment;
import appLogic.MainLogic;
import appLogic.User;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JToggleButton;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
 
public class ViewAppointmentPanel extends JPanel {
        public JTextField roomField;
        public JTextField endField;
        public JTextField startField;
        public JTextArea descriptionField;
        public JButton btnTilbake;
        public JButton btnDecline;
        public JLabel lblAvtale;
        public JButton btnLeggTilAlarm;
        public TextArea participantsField;
        public JButton btnVisAlarmer;
        public JButton btnEndreAvtale;

 
        /**
         * Create the panel.
         */
        public ViewAppointmentPanel() {
                setLayout(new FormLayout(new ColumnSpec[] {
                		FormFactory.RELATED_GAP_COLSPEC,
                		FormFactory.DEFAULT_COLSPEC,
                		FormFactory.RELATED_GAP_COLSPEC,
                		ColumnSpec.decode("85dlu:grow"),
                		FormFactory.RELATED_GAP_COLSPEC,
                		FormFactory.DEFAULT_COLSPEC,
                		FormFactory.RELATED_GAP_COLSPEC,
                		ColumnSpec.decode("max(6dlu;default)"),},
                	new RowSpec[] {
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		RowSpec.decode("max(15dlu;default)"),
                		FormFactory.RELATED_GAP_ROWSPEC,
                		RowSpec.decode("61dlu"),
                		FormFactory.RELATED_GAP_ROWSPEC,
                		RowSpec.decode("max(7dlu;default)"),
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		RowSpec.decode("max(0dlu;default):grow"),}));
               
                lblAvtale = new JLabel("Avtale");
                lblAvtale.setFont(new Font("Tahoma", Font.PLAIN, 20));
                add(lblAvtale, "2, 1, 5, 3, left, default");
               
                JLabel lblStart = new JLabel("Start");
                lblStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblStart, "2, 4, right, default");
               
                startField = new JTextField();
                startField.setBackground(Color.WHITE);
                startField.setEditable(false);
                add(startField, "4, 4, fill, default");
                startField.setColumns(10);
                
                btnLeggTilAlarm = new JButton("Legg til alarm");
                add(btnLeggTilAlarm, "6, 4");
               
                JLabel lblSlutt = new JLabel("Slutt");
                lblSlutt.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblSlutt, "2, 6, right, default");
               
                endField = new JTextField();
                endField.setBackground(Color.WHITE);
                endField.setEditable(false);
                add(endField, "4, 6, fill, default");
                endField.setColumns(10);
                
                btnVisAlarmer = new JButton("Vis alarmer");
                add(btnVisAlarmer, "6, 6");
               
                JLabel lblRom = new JLabel("Rom");
                lblRom.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblRom, "2, 8, right, default");
               
                roomField = new JTextField();
                roomField.setBackground(Color.WHITE);
                roomField.setEditable(false);
                add(roomField, "4, 8, fill, default");
                roomField.setColumns(10);
               
                JLabel lblBeskrivelse = new JLabel("Beskrivelse");
                lblBeskrivelse.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblBeskrivelse, "2, 10, right, default");
               
                descriptionField = new JTextArea();
                descriptionField.setEditable(false);
                add(descriptionField, "4, 10, fill, fill");
                
                btnEndreAvtale = new JButton("Endre avtale");
                add(btnEndreAvtale, "6, 10");
               
                JLabel lblDeltakere = new JLabel("Deltakere");
                lblDeltakere.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblDeltakere, "2, 12, right, default");
                
                participantsField = new TextArea();
                add(participantsField, "4, 12");
                participantsField.setEditable(false);
               
                btnTilbake = new JButton("Tilbake");
                btnTilbake.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(btnTilbake, "4, 16");
                
                 btnDecline = new JButton("Meld av");
                 btnDecline.setBackground(Color.RED);
                 btnDecline.setFont(new Font("Tahoma", Font.PLAIN, 14));
                 add(btnDecline, "6, 16");
        }
        
        public void showAppointment(String description) {
        	Appointment appointment = MainLogic.currentUser.getAppointment(description); 
        	roomField.setText(appointment.getRoom().toString());
        	descriptionField.setText(appointment.getDescription());
        	String start = appointment.getStart().toString();
        	startField.setText(start.substring(0,10) + "\t\t" + start.substring(11, 16));
        	String end = appointment.getEnd().toString();
        	endField.setText(end.substring(0,10) + "\t\t" + end.substring(11,16));
        	participantsField.setText("");
        	for (User u : appointment.getParticipants().keySet()) {
        		Boolean bool = appointment.getParticipants().get(u);
        		String status = "invitert";
        		if (bool != null) status = (bool ? "godtatt" : "avslaatt");
        		if(u.equals(appointment.getLeader())) status = "leder";
        		participantsField.append(u.toString() + " [" + status + "]\n");
        	}
        }
        
        public void showInvitation(String description) {
        	Appointment appointment = MainLogic.currentUser.getInvitation(description); 
        	roomField.setText(appointment.getRoom().toString());
        	descriptionField.setText(appointment.getDescription());
        	String start = appointment.getStart().toString();
        	startField.setText(start.substring(0,10) + "\t\t" + start.substring(11, 16));
        	String end = appointment.getEnd().toString();
        	endField.setText(end.substring(0,10) + "\t\t" + end.substring(11,16));
        	participantsField.setText("");
        	for (User u : appointment.getParticipants().keySet()) {
        		Boolean bool = appointment.getParticipants().get(u);
        		String status = "invitert";
        		if (bool != null)
        			status = (bool ? "godtatt" : "avslått");
        		participantsField.append(u.toString() + " [" + status + "]\n");
        	}
        }
}