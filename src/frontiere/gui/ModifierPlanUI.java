package frontiere.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controleur.ControlAfficherCalendrier;
import controleur.ControlDemanderJour;
import controleur.ControlModifierPlan;
import controleur.ControlModifierSousTache;
import utils.Utils;

public class ModifierPlanUI  extends GUIManager{


	private MenuPrincipalUI menuPrincipalUI;
	private MenuUnJourUI menuUnJourUI;
	private ControlDemanderJour controlDemanderJour;
	private ControlAfficherCalendrier controlAfficherCalendrier;
	private ControlModifierPlan controlModifierPlan;
	private ControlModifierSousTache controlModifierSousTache;
	
	private JFrame frame;
	private JLabel titreLabel;
	private JButton supprPlanButton;
	private JButton terminerTacheButton;
	private JButton creerSousTacheButton;
	private JTextArea nomPlanTextArea;
	private JTextArea datePlanTextArea;
	private JTextArea heurePlanTextArea;
	private JTextArea infosSupPlanTextArea;
	private JList<String> listeSousTaches;
	private JButton deselectionButton;
	
	JScrollPane descriptionScrollPane;
	
	private GregorianCalendar jour;
	private boolean planSelectionne = true;
	private int indicePlan;
	private int indiceSousTache;
	
	private boolean nomChange = false;
	private boolean dateChange = false;
	private boolean heureChange = false;
	private boolean infoSupChange = false;
	private boolean isOpen = false;

	


	
	/**
	 * Launch the application.
	 */
	public void lancerGUI(GregorianCalendar jour, int indicePlan) {
		this.jour = (GregorianCalendar) jour.clone();
		this.indicePlan = indicePlan;
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
	public ModifierPlanUI(ControlDemanderJour controlDemanderJour,
			ControlAfficherCalendrier controlAfficherCalendrier,
			ControlModifierPlan controlModifierPlan,
			ControlModifierSousTache controlModofierSousTache) {
		this.controlDemanderJour = controlDemanderJour;
		this.controlAfficherCalendrier = controlAfficherCalendrier;
		this.controlModifierPlan = controlModifierPlan;
		this.controlModifierSousTache = controlModofierSousTache;
		initialize();
		ajouterFrame(frame);

	}

	private void initDynamique() {
		
		planSelectionne = true;
		
		if(controlModifierPlan.planIsTache(jour, indicePlan)) {
			descriptionScrollPane.setVisible(true);
			terminerTacheButton.setVisible(true);
			creerSousTacheButton.setVisible(true);
			 deselectionButton.setVisible(true);
			 deselectionButton.setEnabled(false);
			 terminerTacheButton.setText("Terminer la Tâche");
			initListeSousTache();
		}
		else {
			 descriptionScrollPane.setVisible(false);
			 terminerTacheButton.setVisible(false);
			 creerSousTacheButton.setVisible(false);
			 deselectionButton.setVisible(false);

		}
		supprPlanButton.setText("Supprimer le Plan");
		datePlanTextArea.setEnabled(true);

		initInfosPlan();
	}
	
	private void initInfosPlan() {
		
		
		
		if(planSelectionne) {
			titreLabel.setText(controlAfficherCalendrier.obtenirCategoriePlan(jour, indicePlan));
			nomPlanTextArea.setText(controlAfficherCalendrier.obtenirNomPlan(jour, indicePlan));
			datePlanTextArea.setText(controlAfficherCalendrier.obtenirDatePlan(jour, indicePlan));
			heurePlanTextArea.setText(controlAfficherCalendrier.obtenirHeurePlan(jour, indicePlan));
			infosSupPlanTextArea.setText(controlAfficherCalendrier.obtenirInfoSupPlan(jour, indicePlan));
		}
		else {
			titreLabel.setText("Sous Tâche " + controlAfficherCalendrier.obtenirTerminaisonSousTache(jour, indicePlan, indiceSousTache));

			nomPlanTextArea.setText(controlAfficherCalendrier.obtenirNomSousTache(jour, indicePlan, indiceSousTache));
			heurePlanTextArea.setText(controlAfficherCalendrier.obtenirHeureSousTache(jour, indicePlan, indiceSousTache));
			infosSupPlanTextArea.setText(controlAfficherCalendrier.obtenirInfoSupSousTache(jour, indicePlan, indiceSousTache));
		}
		
		nomChange = false;
		dateChange = false;
		heureChange = false;
		infoSupChange = false;
	}
	
	protected void initListeSousTache() {
		DefaultListModel<String> model = new DefaultListModel<>();
		List<String> plans = controlAfficherCalendrier.obtenirDescSousTaches(jour, indicePlan);

		if(plans != null) {
			model.addAll(plans);
		}
		else {
			model.clear();
		}
		listeSousTaches.setModel(model);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 640);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		titreLabel = new JLabel();

		titreLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 30));
		titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		titreLabel.setMaximumSize(new Dimension(100, 40));
		titreLabel.setMinimumSize(new Dimension(79, 30));
		
		JPanel plansPanel = new JPanel();
		
		JPanel panel = new JPanel();
		
		JPanel modifPlanPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
					.addGap(133))
				.addComponent(plansPanel, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifPlanPanel, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
					.addGap(22)
					.addComponent(plansPanel, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(modifPlanPanel, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
					.addGap(129))
		);
		
		supprPlanButton = new JButton("Supprimer le Plan");

		supprPlanButton.setToolTipText("");
		supprPlanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(planSelectionne) {
					controlModifierPlan.supprimerPlan(jour, indicePlan);
					menuUnJourUI.lancerGUI(jour);
					frame.setVisible(false);
				}
				else {
					controlModifierSousTache.supprimerSousTache(jour, indicePlan, indiceSousTache);
					initDynamique();
				}

			}
		});
		
		deselectionButton = new JButton("Retour à la Tâche");

		deselectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initDynamique();
			}
		});
		deselectionButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		deselectionButton.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		modifPlanPanel.add(deselectionButton);
		supprPlanButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifPlanPanel.add(supprPlanButton);
		
		terminerTacheButton = new JButton("Terminer la Tâche");
		terminerTacheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(planSelectionne) {
					controlModifierPlan.terminerTache(jour, indicePlan);
					menuUnJourUI.lancerGUI(jour);
					frame.setVisible(false);
				}
				else {
					controlModifierSousTache.terminerSousTache(jour, indicePlan, indiceSousTache);
					titreLabel.setText("Sous Tâche " + controlAfficherCalendrier.obtenirTerminaisonSousTache(jour, indicePlan, indiceSousTache));
					initListeSousTache();
				}
			}
		});
		terminerTacheButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifPlanPanel.add(terminerTacheButton);

		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton retourButton = new JButton("<-");
		retourButton.setHorizontalAlignment(SwingConstants.LEFT);
		retourButton.setPreferredSize(new Dimension(20, 20));
		retourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuUnJourUI.lancerGUI(jour);
				frame.setVisible(false);
			}
		});
		panel.add(retourButton);
		
		JButton menuButton = new JButton("Menu");
		menuButton.setMargin(new Insets(0, 0, 0, 0));


		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPrincipalUI.lancerGUI(jour);
				frame.setVisible(false);
			}
		});
		panel.add(menuButton);
		
		listeSousTaches = new JList<>();
		listeSousTaches.setFont(new Font("Tahoma", Font.PLAIN, 17));
		listeSousTaches.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int indice = listeSousTaches.getSelectedIndex();
				
				if(indice != -1) {
					indiceSousTache = indice;
					selectionnerSousTache();
				}
			}
		});
		listeSousTaches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		descriptionScrollPane = new JScrollPane();
		descriptionScrollPane.setVisible(false);
		
		JButton enregistrerButton = new JButton("Enregistrer");


		enregistrerButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		enregistrerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean  modif = false;
				if (planSelectionne) {
					modif = enregistrerModifPlan();
				}
				else {
					enregistrerModifSousTache();
					initListeSousTache();
					listeSousTaches.setSelectedIndex(indiceSousTache);
				}
				if (modif) {
					initInfosPlan();
				}
			}
		});
		
		JPanel buttonModifPanel  = new JPanel();


		buttonModifPanel.add(enregistrerButton);

		
		creerSousTacheButton = new JButton("Créé Sous-Tâche");


		buttonModifPanel.add(creerSousTacheButton);
		creerSousTacheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomSousTache = JOptionPane.showInputDialog(frame, "Quel est le nom de la sous-tâche ?", null);
				if(nomSousTache != null) {
					controlModifierPlan.ajouterSousTache(jour, indicePlan, nomSousTache);
					initListeSousTache();
				}
			}
		});
		
		
		creerSousTacheButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel selectionLabel = new JLabel("Sous Tâches");


		selectionLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descriptionScrollPane.setColumnHeaderView(selectionLabel);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		descriptionScrollPane.setRowHeaderView(scrollBar_1);
		
		descriptionScrollPane.setViewportView(listeSousTaches);
		
		infosSupPlanTextArea = new JTextArea();
	

		infosSupPlanTextArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				infoSupChange = true;
			}

		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        infoSupChange = true;
		    }
	
			@Override
			public void changedUpdate(DocumentEvent e) {
				infoSupChange = true;
			}
		});
		
		
		nomPlanTextArea = new JTextArea();
	

		nomPlanTextArea.getDocument().addDocumentListener(new DocumentListener() {
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	nomChange = true;
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	nomChange = true;
	        }

			@Override
			public void changedUpdate(DocumentEvent e) {
	        	nomChange = true;
			}
	    });
		
		datePlanTextArea = new JTextArea();


		datePlanTextArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
	        public void removeUpdate(DocumentEvent e) {
	        	dateChange = true;
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	dateChange = true;
	        }

			@Override
			public void changedUpdate(DocumentEvent e) {
				dateChange = true;
			}
	    });
		heurePlanTextArea = new JTextArea();


		heurePlanTextArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
	        public void removeUpdate(DocumentEvent e) {
	        	heureChange = true;
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	heureChange = true;
	        }

			@Override
			public void changedUpdate(DocumentEvent e) {
				heureChange = true;
			}
	    });
		
		JPanel modifierPlanPanel = new JPanel();
		
		JLabel nomPlanLabel = new JLabel("Nom :");


		
		JLabel datePlanLabel = new JLabel("Date :");


		
		JLabel heurePlanLabel = new JLabel("Heure :");


		
		JLabel infosSupLabel = new JLabel("Informations Supplémentaires :");



		GroupLayout gl_modifierPlanPanel = new GroupLayout(modifierPlanPanel);
		gl_modifierPlanPanel.setHorizontalGroup(
			gl_modifierPlanPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_modifierPlanPanel.createSequentialGroup()
					.addGap(56)
					.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(infosSupPlanTextArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
						.addComponent(infosSupLabel, Alignment.LEADING)
						.addGroup(Alignment.LEADING, gl_modifierPlanPanel.createSequentialGroup()
							.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(datePlanLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(nomPlanLabel, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
								.addComponent(heurePlanLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(heurePlanTextArea, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
								.addComponent(datePlanTextArea, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
								.addComponent(nomPlanTextArea, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))))
					.addGap(53))
		);
		gl_modifierPlanPanel.setVerticalGroup(
			gl_modifierPlanPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modifierPlanPanel.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nomPlanLabel)
						.addComponent(nomPlanTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(datePlanLabel)
						.addComponent(datePlanTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_modifierPlanPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(heurePlanLabel)
						.addComponent(heurePlanTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infosSupLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infosSupPlanTextArea, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addContainerGap())
		);
		modifierPlanPanel.setLayout(gl_modifierPlanPanel);
		GroupLayout gl_plansPanel = new GroupLayout(plansPanel);
		gl_plansPanel.setHorizontalGroup(
			gl_plansPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(modifierPlanPanel, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
				.addComponent(buttonModifPanel, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
				.addComponent(descriptionScrollPane, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
		);
		gl_plansPanel.setVerticalGroup(
			gl_plansPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plansPanel.createSequentialGroup()
					.addGap(1)
					.addComponent(modifierPlanPanel, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonModifPanel, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(descriptionScrollPane, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addContainerGap())
		);
		plansPanel.setLayout(gl_plansPanel);
		
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public boolean enregistrerModifPlan() {
		boolean init = false;
		if(nomChange) {
			String nouveauNom = nomPlanTextArea.getText();
			controlModifierPlan.modifierNomPlan(jour, indicePlan, nouveauNom);
			init = true;
		}
		if(heureChange) {
			String nouvelleHeure = heurePlanTextArea.getText();
			if(controlDemanderJour.verifierHeureDonnee(nouvelleHeure)) {
				  int[] heure = controlDemanderJour.recupererHeure(nouvelleHeure);
				  indicePlan = controlModifierPlan.modifierHeurePlan(jour, indicePlan, heure);
				  init = true;
			  }
		}
		if(infoSupChange) {
			String nouvellesInfos = infosSupPlanTextArea.getText();
			controlModifierPlan.modifierInfoSupPlan(jour, indicePlan, nouvellesInfos);
			init = true;
		}
		if(dateChange) {
			String nouvelleDate = datePlanTextArea.getText();
			if(controlDemanderJour.verifierDateDonnee(nouvelleDate)) {
				  int[] journee = controlDemanderJour.recupererDate(nouvelleDate);
				  GregorianCalendar nouvJour = Utils.createCalendarFromInt(journee);
				  indicePlan = controlModifierPlan.modifierDate(jour, indicePlan, nouvJour);
				  jour = nouvJour;
				  init = true;
			  }
		}
		return init;
	}
	
	public boolean enregistrerModifSousTache() {
		boolean init = false;
		if(nomChange) {
			String nouveauNom = nomPlanTextArea.getText();
			controlModifierSousTache.modifierNom(jour, indicePlan, indiceSousTache, nouveauNom);
			init = true;
		}
		if(heureChange) {
			String nouvelleHeure = heurePlanTextArea.getText();
			if(controlDemanderJour.verifierHeureDonnee(nouvelleHeure)) {
				  int[] heure = controlDemanderJour.recupererHeure(nouvelleHeure);
				  indiceSousTache = controlModifierSousTache.modifierHeure(jour, indicePlan, indiceSousTache, heure);
				  init = true;
			  }
		}
		if(infoSupChange) {
			String nouvellesInfos = infosSupPlanTextArea.getText();
			controlModifierSousTache.modifierInfoSup(jour, indicePlan, indiceSousTache, nouvellesInfos);
			init = true;
		}
		return init;
	}
	
	public void selectionnerSousTache() {
		deselectionButton.setEnabled(true);
		datePlanTextArea.setEnabled(false);
		planSelectionne = false;
		creerSousTacheButton.setVisible(false);
		supprPlanButton.setText("Supprimer la Sous-Tache");
		terminerTacheButton.setText("Terminer la Sous-Tache");
		initInfosPlan();
	}
	
	
	public void fermerFenetre() {
		frame.setVisible(false);
	}

	public void setMenuPrincipalUI(MenuPrincipalUI menuPrincipalUI) {
		this.menuPrincipalUI = menuPrincipalUI;
	}

	public void setMenuUnJourUI(MenuUnJourUI menuUnJourUI) {
		this.menuUnJourUI = menuUnJourUI;
		
	}
}
