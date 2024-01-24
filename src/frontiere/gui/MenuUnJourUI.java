package frontiere.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import coeur.DonneesApplication;
import controleur.ControlAfficherCalendrier;
import controleur.ControlCreerPlan;
import controleur.ControlDemanderJour;
import controleur.ControlModifierPlan;
import utils.Utils;

public class MenuUnJourUI  extends GUIManager {


	private MenuPrincipalUI menuPrincipalUI;
	private ModifierPlanUI modifierPlanUI;
	private ControlDemanderJour controlDemanderJour;
	private ControlAfficherCalendrier controlAfficherCalendrier;
	private ControlCreerPlan controlCreerPlan;
	private ControlModifierPlan controlModifierPlan;
	
	private JFrame frame;
	private JTextField jourTextField;
	private JLabel titreLabel;
	private JList<String> listePlans;
	private JButton modifPlanButton;
	private JButton supprPlanButton;
	private JButton terminerTacheButton;
	
	private GregorianCalendar jour;
	private int indicePlan;
	
	private boolean isOpen = false;

	/**
	 * Launch the application.
	 */
	public void lancerGUI(GregorianCalendar jour) {
		this.jour = (GregorianCalendar) jour.clone();
		initDynamique();
		
		if(!isOpen) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			isOpen = true;
		}
		else {
			frame.setVisible(true);
		}
	} 

	/**
	 * Create the application.
	 */
	public MenuUnJourUI(ControlDemanderJour controlDemanderJour,
			ControlAfficherCalendrier controlAfficherCalendrier,
			ControlCreerPlan controlCreerPlan,
			ControlModifierPlan controlModifierPlan,
			ModifierPlanUI modifierPlanUI) {
		this.controlDemanderJour = controlDemanderJour;
		this.controlAfficherCalendrier = controlAfficherCalendrier;
		this.controlCreerPlan = controlCreerPlan;
		this.controlModifierPlan = controlModifierPlan;
		this.modifierPlanUI = modifierPlanUI;
		modifierPlanUI.setMenuUnJourUI(this);
		
		initialize();
		ajouterFrame(frame);

	}

	private void initDynamique() {
		titreLabel.setText("Le " + DonneesApplication.DF.format(jour.getTime()));
		  DefaultListModel<String> model = new DefaultListModel<>();
		  List<String> plans =  controlAfficherCalendrier.obtenirNomPlans(jour);
		  if(plans != null) {
			  model.addAll(plans);
		  }
		  else {
			  model.clear();
		  }
		  listePlans.setModel(model);
		  modifPlanButton.setEnabled(false);
		  supprPlanButton.setEnabled(false);
		  terminerTacheButton.setVisible(false);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 557, 700);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		titreLabel = new JLabel();

		titreLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 30));
		titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		titreLabel.setMaximumSize(new Dimension(100, 40));
		titreLabel.setMinimumSize(new Dimension(79, 30));
		
		JPanel menuPanel = new JPanel();
		JPanel finPanel = new JPanel();
		
		jourTextField = new JTextField();


		jourTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jourTextField.setText("");
			}
		});
		jourTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  String textFieldValue = jourTextField.getText();
				  if(controlDemanderJour.verifierDateDonnee(textFieldValue)) {
					  int[] journee = controlDemanderJour.recupererDate(textFieldValue);
					  jour = Utils.createCalendarFromInt(journee);
					  initDynamique();
				  }
				  else {
					  jourTextField.setText("Entrez un jour de la forme Jour/Mois/Annee");
					  jourTextField.setForeground(Color.RED);
				  }
			}
		});
		jourTextField.setText("Entrez un jour");
		jourTextField.setHorizontalAlignment(SwingConstants.CENTER);
		jourTextField.setColumns(10);
		
		JPanel plansPanel = new JPanel();
		
		JPanel panel = new JPanel();
		
		JPanel modifPlanPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
					.addGap(76))
				.addComponent(plansPanel, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifPlanPanel, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(200)
					.addComponent(jourTextField, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
					.addGap(197))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(finPanel, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(plansPanel, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(modifPlanPanel, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jourTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(finPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(36))
		);
		
		modifPlanButton = new JButton("Infos du Plan");


		modifPlanButton.setToolTipText("");
		modifPlanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lancerDescriptionPlanUI();
			}
		});
		modifPlanButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 modifPlanButton.setVisible(true);
		modifPlanPanel.add(modifPlanButton);
		supprPlanButton = new JButton("Supprimer le Plan");


		
		terminerTacheButton = new JButton("Terminer la Tâche");


		terminerTacheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlModifierPlan.terminerTache(jour, indicePlan);
				initDynamique();
			}
		});
		
		supprPlanButton.setToolTipText("");
		supprPlanButton.setEnabled(false);
		supprPlanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlModifierPlan.supprimerPlan(jour, indicePlan);
				initDynamique();
			}
		});
		supprPlanButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifPlanPanel.add(supprPlanButton);
		
		terminerTacheButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		terminerTacheButton.setVisible(false);
		modifPlanPanel.add(terminerTacheButton);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton retourButton = new JButton("<-");

		retourButton.setHorizontalAlignment(SwingConstants.LEFT);
		retourButton.setPreferredSize(new Dimension(20, 20));
		retourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				menuPrincipalUI.lancerGUI(jour);
			}
		});
		panel.add(retourButton);
		plansPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane plansScrollPane = new JScrollPane();
		
		plansScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		plansPanel.add(plansScrollPane);
		listePlans = new JList<>();
		listePlans.setFont(new Font("Tahoma", Font.PLAIN, 17));
		listePlans.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int indice =  listePlans.getSelectedIndex();
				
				if(indice != -1) {
					indicePlan = indice;
					modifPlanButton.setEnabled(true);
					supprPlanButton.setEnabled(true);
					
					if(controlModifierPlan.planIsTache(jour, indicePlan)) {
						terminerTacheButton.setVisible(true);
					} else {
						terminerTacheButton.setVisible(false);

					}
				}
			}
		});
		listePlans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	lancerDescriptionPlanUI();
		        }
			}
		});
		listePlans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		plansScrollPane.setViewportView(listePlans);
		
		JScrollBar scrollBar = new JScrollBar();
		plansScrollPane.setRowHeaderView(scrollBar);
		
		JLabel espacePlansLabel = new JLabel("Liste des plans");


		espacePlansLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		plansScrollPane.setColumnHeaderView(espacePlansLabel);
		
		JButton precButton = new JButton("Jour Précédent");

		precButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jour.add(Calendar.DAY_OF_MONTH, -1);
				GregorianCalendar jourActuel = DonneesApplication.JOUR_ACTUEL;
				if(jour.before(jourActuel)) {
					jour.add(Calendar.DAY_OF_MONTH, 1);
				}
				initDynamique(); 
			}
		});
		precButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		finPanel.add(precButton);
		
		JButton suivButton = new JButton("Jour Suivant");

		suivButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jour.add(Calendar.DAY_OF_MONTH, 1);
				initDynamique(); 
			}
		});
		suivButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		finPanel.add(suivButton);
		
		JButton creeTacheButton = new JButton("Créé une tache");


		creeTacheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String nomTache = JOptionPane.showInputDialog(frame, "Quel est le nom de la tâche ?", null);
				 if(nomTache != null) {
					 int[] journee = Utils.createIntFromCalendar(jour);
						controlCreerPlan.ajouterTache(journee, nomTache);
						initDynamique();
				 }
				
			}
		});
		creeTacheButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuPanel.add(creeTacheButton);
		
		JButton creeEventButton = new JButton("Créé un événement");


		creeEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String nomEvent = JOptionPane.showInputDialog(frame, "Quel est le nom de l'événement ?", null);
				 if(nomEvent != null) {
						int[] journee = Utils.createIntFromCalendar(jour);
						controlCreerPlan.ajouterEvenement(journee, nomEvent);
						initDynamique();
				 }
			}
		});
		creeEventButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuPanel.add(creeEventButton);
		
		frame.getContentPane().setLayout(groupLayout);
	}
	
	protected void lancerDescriptionPlanUI() {
		fermerFenetre();
		menuPrincipalUI.fermerFenetre();
		modifierPlanUI.lancerGUI(jour, indicePlan);
	}

	public void fermerFenetre() {
		frame.setVisible(false);
	}

	public void setMenuPrincipalUI(MenuPrincipalUI menuPrincipalUI) {
		this.menuPrincipalUI = menuPrincipalUI;
		modifierPlanUI.setMenuPrincipalUI(menuPrincipalUI);
	}
}
