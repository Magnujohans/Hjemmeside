package gui;


import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.text.View;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;

import exceptions.DateTimeException;
import appLogic.Alarm;
import appLogic.Appointment;
import appLogic.CalendarRow;
import appLogic.Employee;
import appLogic.Group;
import appLogic.MainLogic;
import appLogic.Room;
import appLogic.User;

public class MainFrame extends JFrame {

	private MainLogic main; 
	private HomePanel home;
	private RegisterPanel register;
	private LogInPanel login;
	private LoggedInPanel loggedin;
	private ViewAppointmentPanel viewapp;
	private AddAppointmentPanel addapp;
	private EditAppointmentPanel editapp;
	private CalendarPanel viewcal; 
	private AddAlarmPanel addalarm;
	private InvitationsPanel viewinv;
	private ViewInvitationPanel viewappinv;
	private ViewAlarmsPanel viewalarms; 
	private RoomPanel viewrooms; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		main = new MainLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 320);
		home = new HomePanel();
		register = new RegisterPanel();
		loggedin = new LoggedInPanel();
		login = new LogInPanel();
		addapp = new AddAppointmentPanel();
		viewapp = new ViewAppointmentPanel();
		editapp = new EditAppointmentPanel();
		viewcal = new CalendarPanel();
		addalarm = new AddAlarmPanel(); 
		viewinv = new InvitationsPanel();
		viewappinv = new ViewInvitationPanel();
		viewalarms = new ViewAlarmsPanel(); 
		viewrooms = new RoomPanel(null, null, null, null);	

		setContentPane(home);
		home.revalidate();

		//Knapper til hjempanelet
		home.btnLoggInn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login.showUsers();
				setContentPane(login);
				login.revalidate();
			}
		});

		//Knapper til registrering av bruker
		register.btnRegistrer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    String name = register.firstname.getText() +" "+ register.lastname.getText();
                    String email = register.email.getText();
                    /*
                     * createEmployee(name, email)
                     */
            }
		});

		register.btnTilbake.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(home);
                    home.revalidate();
            }
		});


		//Knapper for innlogging
		login.btnTilbake.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(home);
                    home.revalidate();
            }
		});

		login.btnLoggInn.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent event) {
                    if (login.choice.getSelectedItem() != "Velg bruker...") {
                    	setContentPane(loggedin);
	                    loggedin.revalidate();
	                    main.logInEmployee(Employee.getEmployee(login.choice.getSelectedItem()));
	                    loggedin.loggedInAsField.setText("Logget inn som: " + MainLogic.currentUser.toString()); 
                    }
            }
		});

		//Knapper for innlogget bruker
		loggedin.btnLoggUt.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(home);
                    home.revalidate();
                    main.logInEmployee(null); 
            }
		});

		loggedin.btnNyAvtale.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    		addapp.showUsers();
                    setContentPane(addapp);
                    addapp.revalidate();
            }
		});

		loggedin.btnVisKalender.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(viewcal);
                    viewcal.revalidate();
                    viewcal.showWeek(viewcal.currWeek);
                    viewcal.addChoices(); 
            }
		});

		loggedin.btnVisInvitasjoner.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setContentPane(viewinv);
				viewinv.revalidate();
				viewinv.showInvitations();
				viewinv.addChoices();
			}
		});


		/*
		 * START LEGG INN AVTALE
		 */
		addapp.btnAvbryt.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(loggedin);
                    loggedin.revalidate();
            }
		});

		addapp.btnLeggTil.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String name = addapp.chcDeltaker.getSelectedItem();
				int numOfNames = name.split(" ").length;
				if(numOfNames>1) addapp.addUser(Employee.getEmployee(addapp.chcDeltaker.getSelectedItem()));
				else addapp.addUser(Group.getGroup(addapp.chcDeltaker.getSelectedItem()));
			}
		});

		addapp.btnVidere.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				/*
				 * SJEKK DENNE (IF) trenger nok ikke sjekk for tallene
				 */
				if(addapp.chcStartaar.getSelectedItem().equals(" ")
						|| addapp.chcStartmnd.equals(" ") || addapp.chcStartdag.getSelectedItem().equals(" ")
						|| addapp.chcStarttime.equals(" ") || addapp.chcStartmin.equals(" ")
						|| addapp.chcSluttime.equals(" ") || addapp.chcSluttmin.equals(" ")
						|| addapp.txtBeskrivelse.equals(" ") || addapp.deltakere.isEmpty()
						|| addapp.txtBeskrivelse.getText().equals("")) 
				{
					//Ikke opprett avtale
				} else {
					String desc = addapp.txtBeskrivelse.getText();
					int year = Integer.parseInt(addapp.chcStartaar.getSelectedItem());
					int month = Integer.parseInt(addapp.chcStartmnd.getSelectedItem());
					int day = Integer.parseInt(addapp.chcStartdag.getSelectedItem());
					int hourStart = Integer.parseInt(addapp.chcStarttime.getSelectedItem());
					int minStart = Integer.parseInt(addapp.chcStartmin.getSelectedItem());
					int hourEnd = Integer.parseInt(addapp.chcSluttime.getSelectedItem());
					int minEnd = Integer.parseInt(addapp.chcSluttmin.getSelectedItem());
					ArrayList<User> participants = addapp.deltakere;
					DateTime start = new DateTime(year, month, day, hourStart, minStart, 0);
					DateTime end = new DateTime(year, month, day, hourEnd, minEnd, 0);
					viewrooms.description = desc;
					viewrooms.inStart = start;
					viewrooms.inEnd = end;
					viewrooms.participants = participants;
					viewrooms.editedAppointment = false;
					viewrooms.showAvailableRooms();
					setContentPane(viewrooms);
	                viewrooms.revalidate();
				}
			}
		});
		/*
		 * END LEGG INN AVTALE
		 */
		//Knapper for valg av rom
		viewrooms.btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(viewrooms.editedAppointment){
                    setContentPane(editapp);
                    editapp.revalidate();
            	} else{
            		setContentPane(addapp);
            		addapp.revalidate();
            	}
            }
		});

		viewrooms.btnChooseRoom.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    Room room = Room.getRoom(viewrooms.choice.getSelectedItem());
                    if(!viewrooms.editedAppointment)
                    	main.createAppointment(viewrooms.description, room, viewrooms.participants, viewrooms.inStart, viewrooms.inEnd);
                    else{
                    	Appointment old = editapp.appointment;
                    	main.cancelAppointment(old);
                    	viewrooms.participants.remove(old.getLeader());
                    	main.createAppointment(viewrooms.description, room, viewrooms.participants, viewrooms.inStart, viewrooms.inEnd, old.getLeader());
                    }
            		System.out.println("Avtale opprettet!");
            		setContentPane(loggedin);
                    loggedin.revalidate();
            }
		});



		//Knapper for visning av avtale
		viewapp.btnTilbake.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(viewcal);
                    viewcal.revalidate();
            }
		});

		viewapp.btnDecline.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    	String description = viewapp.descriptionField.getText();
		    	Appointment app = MainLogic.currentUser.getAppointment(description);
		    	if(MainLogic.currentUser.equals(app.getLeader())){
		    		main.cancelAppointment(app);
		    	}
		    	else main.declineAppointment(app);
		    	System.out.println("Meldt av");
		    	setContentPane(loggedin);
                loggedin.revalidate();
            }
		});

		viewapp.btnLeggTilAlarm.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    	addalarm.currentAppointment = MainLogic.currentUser.getAppointment(viewapp.descriptionField.getText());
		    	setContentPane(addalarm);
                addalarm.revalidate();
            }
		});

		 viewapp.btnVisAlarmer.addMouseListener(new MouseAdapter() {
         	@Override
         	public void mouseClicked(MouseEvent e) {
         		viewalarms.showAlarms();
         		viewalarms.addChoices();
         		setContentPane(viewalarms);
         		viewalarms.revalidate();
         	}
         });

        viewapp.btnEndreAvtale.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		editapp.loadParticipants();
        		editapp.showInvited();
        		editapp.showUsers();
        		setContentPane(editapp);
        		editapp.revalidate();
        	}
        });

		//Knapper for opprettelse av alarm
		addalarm.btnAvbryt.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(viewapp);
                    viewapp.revalidate();
            }
		});

		addalarm.btnLeggTil.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    		if (addalarm.txtLabel.getText().equals("") || 
		    				(addalarm.chcHours.getSelectedItem().equals("0") && addalarm.chcMinutes.getSelectedItem().equals("0")))
		    		{
		    			System.out.println("Mangler info for å opprette alarm!");	
		    		} else {
			    		String label = addalarm.txtLabel.getText();
			    		int hoursToMin = 60 * Integer.parseInt(addalarm.chcHours.getSelectedItem());
			    		int minutes = Integer.parseInt(addalarm.chcMinutes.getSelectedItem());
			    		int offset = hoursToMin + minutes;
			    		main.addAlarm(addalarm.currentAppointment,offset,label);
						setContentPane(viewapp);
			            viewapp.revalidate();
		    		}
            }
		});

		//Knapper for visning av alarmer		
		viewalarms.btnBack.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(loggedin);
                    loggedin.revalidate();
            }
		});

		viewalarms.btnDeleteAlarm.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    	String choice = viewalarms.choice.getSelectedItem();
		    	if(!choice.equals("Velg alarm...")){  		
		    		main.deleteAlarm(MainLogic.currentUser.getAlarm(choice));
		    		viewalarms.showAlarms();
		    		viewalarms.revalidate();
		    	}   
            }
		});
		//Knapper for visning av kalender
		viewcal.btnBack.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
                    setContentPane(loggedin);
                    loggedin.revalidate();
            }
		});

		viewcal.btnNextWeek.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    		viewcal.incrementWeek();
		    		viewcal.lblWeek.setText("" + viewcal.currWeekView); 
		    		viewcal.showWeek(viewcal.currWeekView);
            }
		});

		viewcal.btnPrevWeek.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewcal.decrementWeek();
				viewcal.lblWeek.setText("" + viewcal.currWeekView);
				viewcal.showWeek(viewcal.currWeekView);
			}
		});

		viewcal.btnChooseAppointment.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!viewcal.choice.getSelectedItem().equals("Velg avtale...")) {
					viewapp.showAppointment(viewcal.choice.getSelectedItem());
					editapp.appointment = MainLogic.currentUser.getAppointment(viewcal.choice.getSelectedItem());
					setContentPane(viewapp);
                    viewapp.revalidate();
				}
			}
		});


		//Knapper for visning av invitasjoner
		viewinv.btnBack.addMouseListener(new MouseAdapter() {
				    @Override
		            public void mouseClicked(MouseEvent e) {
		                    setContentPane(loggedin);
		                    loggedin.revalidate();
		            }
				});
		viewinv.btnChooseAppointment.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!viewinv.choice.getSelectedItem().equals("Velg invitasjon...")) {
					viewappinv.showInvitation(viewinv.choice.getSelectedItem());
					setContentPane(viewappinv);
                    viewappinv.revalidate();
				}
			}
		});

		//Knapper for visning av én invitasjon
		viewappinv.btnTilbake.addMouseListener(new MouseAdapter() {
				    @Override
		            public void mouseClicked(MouseEvent e) {
		                    setContentPane(viewinv);
		                    viewinv.revalidate();
		            }
				});

		viewappinv.btnAccept.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    	String description = viewappinv.descriptionField.getText();
		    	Appointment invitation = MainLogic.currentUser.getInvitation(description);	
		    	main.acceptAppointment(invitation);
		    	System.out.println("Invitasjon godtatt");
		    	setContentPane(loggedin);
                loggedin.revalidate();
            }
		});

		viewappinv.btnDecline.addMouseListener(new MouseAdapter() {
		    @Override
            public void mouseClicked(MouseEvent e) {
		    	String description = viewappinv.descriptionField.getText();
		    	Appointment invitation = MainLogic.currentUser.getInvitation(description);	
		    	main.declineAppointment(invitation);
		    	System.out.println("Invitasjon avslått");
		    	setContentPane(loggedin);
                loggedin.revalidate();
            }
		});

		//Endre avtale
		editapp.btnVidere.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
				if(editapp.chcStartaar.getSelectedItem().equals(" ")
						|| editapp.chcStartmnd.equals(" ") || editapp.chcStartdag.getSelectedItem().equals(" ")
						|| editapp.chcStarttime.equals(" ") || editapp.chcStartmin.equals(" ")
						|| editapp.chcSluttime.equals(" ") || editapp.chcSluttmin.equals(" ")
						|| editapp.txtBeskrivelse.equals(" ") || editapp.deltakere.isEmpty()
						|| editapp.txtBeskrivelse.getText().equals("")) 
				{
					//Ikke opprett avtale
				} else {
					String desc = editapp.txtBeskrivelse.getText();
					int year = Integer.parseInt(editapp.chcStartaar.getSelectedItem());
					int month = Integer.parseInt(editapp.chcStartmnd.getSelectedItem());
					int day = Integer.parseInt(editapp.chcStartdag.getSelectedItem());
					int hourStart = Integer.parseInt(editapp.chcStarttime.getSelectedItem());
					int minStart = Integer.parseInt(editapp.chcStartmin.getSelectedItem());
					int hourEnd = Integer.parseInt(editapp.chcSluttime.getSelectedItem());
					int minEnd = Integer.parseInt(editapp.chcSluttmin.getSelectedItem());
					ArrayList<User> participants = editapp.deltakere;
					DateTime start = new DateTime(year, month, day, hourStart, minStart, 0);
					DateTime end = new DateTime(year, month, day, hourEnd, minEnd, 0);
					viewrooms.description = desc;
					viewrooms.inStart = start;
					viewrooms.inEnd = end;
					viewrooms.participants = participants;
					viewrooms.editedAppointment = true;	
					Appointment old = editapp.appointment;
					try {
						if(old.getRoom().isBooked(new CalendarRow(start,end,null))) viewrooms.showAvailableRooms(old.getRoom());
						else viewrooms.showAvailableRooms();
					} catch (DateTimeException e1) {
						viewrooms.showAvailableRooms();
					}
					setContentPane(viewrooms);
	                viewrooms.revalidate();
				}
		}
		});

		editapp.btnAvbryt.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
				setContentPane(viewapp);
				viewapp.revalidate();
			}
		});

		editapp.btnLeggTil.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
			editapp.addUser(Employee.getEmployee(editapp.chcLeggTilDeltaker.getSelectedItem()));
			editapp.showUsers();
			editapp.showInvited();
			editapp.revalidate();
			}
		});

		editapp.btnFjern.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
			editapp.removeUser(Employee.getEmployee(editapp.chcFjernDeltaker.getSelectedItem()));
			editapp.showUsers();
			editapp.showInvited();
			editapp.revalidate();
			}
		});
	}

}