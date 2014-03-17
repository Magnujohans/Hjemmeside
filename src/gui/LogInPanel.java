package gui;

import javax.swing.JPanel;

import appLogic.Employee;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Choice;
 
 
public class LogInPanel extends JPanel {
        public JPasswordField passwordField;
        public JButton btnLoggInn;
        public JButton btnTilbake;
        public Choice choice;
        JLabel lblPassord;
 
        /**
         * Create the panel.
         */
        public LogInPanel() {
                setLayout(new FormLayout(new ColumnSpec[] {
                		FormFactory.RELATED_GAP_COLSPEC,
                		FormFactory.DEFAULT_COLSPEC,
                		FormFactory.RELATED_GAP_COLSPEC,
                		ColumnSpec.decode("max(160dlu;default):grow"),},
                	new RowSpec[] {
                		FormFactory.RELATED_GAP_ROWSPEC,
                		RowSpec.decode("max(22dlu;default)"),
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,}));
               
                JLabel lblInnlogging = new JLabel("Innlogging");
                lblInnlogging.setFont(new Font("Tahoma", Font.PLAIN, 20));
                add(lblInnlogging, "2, 2, 3, 1, center, default");
               
                JLabel lblBruker = new JLabel("Bruker");
                lblBruker.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblBruker, "2, 4, right, default");
               
                choice = new Choice();
                add(choice, "4, 4");
                choice.add("Velg bruker...");
                /*
                 * HENT INN BRUKERE ///choice.getSelectedItem();
                 */
               
                lblPassord = new JLabel("Passord");
                lblPassord.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblPassord, "2, 6, right, default");
               
                passwordField = new JPasswordField();
                add(passwordField, "4, 6, fill, default");
               
                btnLoggInn = new JButton("Logg inn");
                btnLoggInn.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(btnLoggInn, "4, 8");
               
                btnTilbake = new JButton("Tilbake");
                btnTilbake.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(btnTilbake, "4, 10");
        }
        
    	public void showUsers(){
    		choice.removeAll();
    		
    		for(Employee e : Employee.employees){
    			choice.add(e.toString());
    		}
    	}
}