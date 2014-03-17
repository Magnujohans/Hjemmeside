package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
 
 
public class RegisterPanel extends JPanel {
        public JTextField firstname;
        public JTextField lastname;
        public JTextField email;
        public JButton btnRegistrer, btnTilbake;
 
        /**
         * Create the panel.
         */
        public RegisterPanel() {
                setLayout(new FormLayout(new ColumnSpec[] {
                		ColumnSpec.decode("69px"),
                		ColumnSpec.decode("78px:grow"),},
                	new RowSpec[] {
                		FormFactory.RELATED_GAP_ROWSPEC,
                		RowSpec.decode("14px"),
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,
                		FormFactory.RELATED_GAP_ROWSPEC,
                		FormFactory.DEFAULT_ROWSPEC,}));
               
                JLabel lblRegistrerBruker = new JLabel("Registrer bruker");
                lblRegistrerBruker.setFont(new Font("Tahoma", Font.PLAIN, 20));
                add(lblRegistrerBruker, "1, 2, 2, 3, center, top");
               
                JLabel lblFornavn = new JLabel("Fornavn");
                lblFornavn.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblFornavn, "1, 6, right, default");
               
                firstname = new JTextField();
                add(firstname, "2, 6, fill, default");
                firstname.setColumns(10);
               
                JLabel lblEtternavn = new JLabel("Etternavn");
                lblEtternavn.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblEtternavn, "1, 8, right, default");
               
                lastname = new JTextField();
                add(lastname, "2, 8, fill, default");
                lastname.setColumns(10);
               
                JLabel lblEpost = new JLabel("Epost");
                lblEpost.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(lblEpost, "1, 10, right, default");
               
                email = new JTextField();
                add(email, "2, 10, fill, default");
                email.setColumns(10);
               
                btnRegistrer = new JButton("Registrer");
                btnRegistrer.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(btnRegistrer, "2, 12");        
                
                btnTilbake = new JButton("Tilbake");
                btnTilbake.setFont(new Font("Tahoma", Font.PLAIN, 14));
                add(btnTilbake, "2, 14");
        }
}