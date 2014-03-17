package gui;

import javax.swing.JPanel;

import appLogic.Employee;
import appLogic.Group;
import appLogic.MainLogic;
import appLogic.User;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.TextArea;

public class AddAppointmentPanel extends JPanel {
	public JTextField txtBeskrivelse;
	public Choice chcDeltaker;
	public Choice chcStartdag;
	public Choice chcStartmnd;
	public Choice chcStartaar;
	public Choice chcStarttime;
	public Choice chcStartmin;
	public Choice chcSluttime;
	public Choice chcSluttmin;
	public JButton btnVidere;
	public JButton btnAvbryt;
	public JButton btnLeggTil;
	public ArrayList<User> deltakere = new ArrayList<User>();
	public TextArea txtrIngenDeltakere;


	public void addUser(User u) {
		if (!deltakere.contains(u)) {
			if(deltakere.isEmpty()) {
				txtrIngenDeltakere.setText(""); 
			}
			deltakere.add(u);
			txtrIngenDeltakere.append(u.toString()+ "\n"); 
		}
	}

	/**
	 * Create the panel.
	 */
	public AddAppointmentPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(37dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(26dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("24dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("0dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(19dlu;default)"),}));

		JLabel lblLeggTilNy = new JLabel("Legg til ny avtale");
		lblLeggTilNy.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(lblLeggTilNy, "2, 2, 13, 1");

		JLabel lblBeskrivelse = new JLabel("Beskrivelse");
		add(lblBeskrivelse, "2, 4, right, default");

		txtBeskrivelse = new JTextField();
		add(txtBeskrivelse, "4, 4, 9, 1, fill, default");
		txtBeskrivelse.setColumns(10);

		JLabel lblDeltaker = new JLabel("Deltaker");
		add(lblDeltaker, "2, 6, right, default");

		chcDeltaker = new Choice();
		add(chcDeltaker, "4, 6, 7, 1");

		btnLeggTil = new JButton("Legg til");
		add(btnLeggTil, "12, 6, 3, 1");

		txtrIngenDeltakere = new TextArea();
		add(txtrIngenDeltakere, "4, 8, 11, 5");
		txtrIngenDeltakere.setEditable(false);

		JLabel lblDato = new JLabel("Dato");
		add(lblDato, "2, 14, right, default");

		chcStartdag = new Choice();
		add(chcStartdag, "4, 14");

		chcStartmnd = new Choice();
		add(chcStartmnd, "6, 14");

		chcStartaar = new Choice();
		add(chcStartaar, "8, 14");

		JLabel lblStarttid = new JLabel("Starttid");
		add(lblStarttid, "10, 14, right, default");

		chcStarttime = new Choice();
		add(chcStarttime, "12, 14");

		chcStartmin = new Choice();
		add(chcStartmin, "14, 14");

		JLabel lblSluttid = new JLabel("Sluttid");
		add(lblSluttid, "10, 16, right, default");

		chcSluttime = new Choice();
		add(chcSluttime, "12, 16");

		chcSluttmin = new Choice();
		add(chcSluttmin, "14, 16");

		btnVidere = new JButton("Videre");
		add(btnVidere, "4, 18, 3, 1");

		btnAvbryt = new JButton("Avbryt");
		add(btnAvbryt, "8, 18");

		for(int i=1;i<32;i++){
			chcStartdag.add(String.valueOf(i));
		}

		for(int i=1;i<13;i++){
			chcStartmnd.add(String.valueOf(i));
		}

		for(int i=2013;i<2017;i++){
			chcStartaar.add(String.valueOf(i));
		}

		for(int i=8;i<21;i++){
			chcStarttime.add(String.valueOf(i));
			chcSluttime.add(String.valueOf(i));
		}

		for(int i=0;i<60;i++){
			chcStartmin.add(String.valueOf(i));
			chcSluttmin.add(String.valueOf(i));
		}	
	}

	public void showUsers(){
		chcDeltaker.removeAll();

		for(Employee e : Employee.employees){
			if(e.equals(MainLogic.currentUser)) continue;
			chcDeltaker.add(e.toString());
		}

		for(Group g : Group.groups){
			chcDeltaker.add(g.toString());
		}
	}
}