package frontiere.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import coeur.DonneesApplication;
import controleur.ControlCommunicationOcaml;
import controleur.ControlDemanderJour;
import controleur.ControlGestionDonnees;
import utils.Utils;

public class MenuPrincipalUI extends GUIManager{

	private GregorianCalendar jourActuel = (GregorianCalendar) DonneesApplication.JOUR_ACTUEL.clone();
	private JFrame frame;
	private JTextField jourTextField;
	private GregorianCalendar jour;
	private JButton jour1Button;
	private JButton jour2Button;
	private JButton jour3Button;
	private JButton jour4Button;
	private JButton jour5Button;
	private JButton jour6Button;
	private JButton jour7Button;
	
	private MenuUnJourUI menuUnJourUI;
	private InfosPersoUI infosPersoUI;
	private BoutiqueUI boutiqueUI;
	private TachesPerduesUI tachesPerduesUI;
	
	private ControlDemanderJour controlDemanderJour;
	private ControlCommunicationOcaml controlCommunicationOcaml;
	
	private boolean isOpen = false;
	
	
	/**
	 * Launch the application.
	 */
	public void lancerGUI(GregorianCalendar jour) {
		this.jour = (GregorianCalendar) jour.clone();
		initialiser_semaine();
		if(!isOpen) {
			
			List<String> tachesPerdues = controlCommunicationOcaml.recupererTachesRetirees();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(!tachesPerdues.isEmpty()) {
						tachesPerduesUI.lancerGUI(tachesPerdues);
						frame.setVisible(false);
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
	 * @param controlCommunicationOcaml 
	 * @param controlActiverProduit 
	 */
	public MenuPrincipalUI(ControlDemanderJour controlDemanderJour, MenuUnJourUI menuUnJourUI,
			InfosPersoUI infosPersoUI, BoutiqueUI boutiqueUI, ControlGestionDonnees controlGestionDonnees,
			ControlCommunicationOcaml controlCommunicationOcaml, TachesPerduesUI tachesPerduesUI) {
		this.controlDemanderJour = controlDemanderJour;
		this.menuUnJourUI = menuUnJourUI;
		this.infosPersoUI = infosPersoUI;
		this.boutiqueUI = boutiqueUI;
		this.controlCommunicationOcaml = controlCommunicationOcaml;
		this.tachesPerduesUI = tachesPerduesUI;
		infosPersoUI.setMenuPrincipalUI(this);
		infosPersoUI.setBoutiqueUI(boutiqueUI);
		menuUnJourUI.setMenuPrincipalUI(this);
		boutiqueUI.setMenuPrincipalUI(this);
		tachesPerduesUI.setMenuPrincipalUI(this);
		initialize();
		ajouterFrame(frame);
		setControlGestionDonnees(controlGestionDonnees);
	}
	
	private void initialiser_semaine() {
		GregorianCalendar jourAffichage = (GregorianCalendar) jour.clone();
		jour1Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));
		jourAffichage.add(Calendar.DAY_OF_MONTH, 1);
		jour2Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));
		jourAffichage.add(Calendar.DAY_OF_MONTH, 1);
		jour3Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));
		jourAffichage.add(Calendar.DAY_OF_MONTH, 1);
		jour4Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));
		jourAffichage.add(Calendar.DAY_OF_MONTH, 1);
		jour5Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));
		jourAffichage.add(Calendar.DAY_OF_MONTH, 1);
		jour6Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));
		jourAffichage.add(Calendar.DAY_OF_MONTH, 1);
		jour7Button.setText(DonneesApplication.DF.format(jourAffichage.getTime()));

		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 614);
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		JLabel titreLabel = new JLabel("Menu Principal");
		titreLabel.setBackground(Color.RED);

		titreLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 30));
		titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		titreLabel.setMaximumSize(new Dimension(100, 40));
		titreLabel.setMinimumSize(new Dimension(79, 30));
		
		JPanel menuPanel = new JPanel();
		
		JLayeredPane layeredPane = new JLayeredPane();
		
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
					  frame.setVisible(false);
					  menuUnJourUI.lancerGUI(Utils.createCalendarFromInt(journee));
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
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
				.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
				.addComponent(layeredPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(197)
					.addComponent(jourTextField, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
					.addGap(200))
				.addComponent(finPanel, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(titreLabel, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addComponent(jourTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(finPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(102))
		);
		
		JButton precButton = new JButton("Semaine précédente");
		precButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				jour.add(Calendar.DATE, -7);
				if (jour.getTime().before(jourActuel.getTime())) {
					jour = (GregorianCalendar) jourActuel.clone();
					
				}
				initialiser_semaine();
			}
		});
		precButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		finPanel.add(precButton);
		
		JButton suivButton = new JButton("Semaine Suivante");

		suivButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jour.add(Calendar.DATE, 7);
				initialiser_semaine();
			}
		});
		suivButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		finPanel.add(suivButton);
		GridBagLayout gbl_layeredPane = new GridBagLayout();
		gbl_layeredPane.columnWidths = new int[] {573};
		gbl_layeredPane.rowHeights = new int[]{275, 0};
		gbl_layeredPane.columnWeights = new double[]{0.0};
		gbl_layeredPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		layeredPane.setLayout(gbl_layeredPane);
		
		JPanel semainePanel = new JPanel();
		layeredPane.setLayer(semainePanel, 1);
		GridBagConstraints gbc_semainePanel = new GridBagConstraints();
		gbc_semainePanel.fill = GridBagConstraints.BOTH;
		gbc_semainePanel.gridx = 0;
		gbc_semainePanel.gridy = 0;
		layeredPane.add(semainePanel, gbc_semainePanel);
		GridBagLayout gbl_semainePanel = new GridBagLayout();
		gbl_semainePanel.columnWidths = new int[]{85, 85, 85, 0};
		gbl_semainePanel.rowHeights = new int[]{21, 0, 0, 0};
		gbl_semainePanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_semainePanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		semainePanel.setLayout(gbl_semainePanel);
		
		jour1Button = new JButton();
		jour1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lancerMenuUnJour((GregorianCalendar) jour.clone());
			}
		});
		jour1Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		GridBagConstraints gbc_jour1Button = new GridBagConstraints();
		gbc_jour1Button.fill = GridBagConstraints.BOTH;
		gbc_jour1Button.weighty = 5.0;
		gbc_jour1Button.weightx = 5.0;
		gbc_jour1Button.anchor = GridBagConstraints.NORTHWEST;
		gbc_jour1Button.insets = new Insets(0, 0, 5, 5);
		gbc_jour1Button.gridx = 0;
		gbc_jour1Button.gridy = 0;
		semainePanel.add(jour1Button, gbc_jour1Button);
		
		jour2Button = new JButton();
		jour2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar nouvJour =(GregorianCalendar) jour.clone();
				nouvJour.add(Calendar.DAY_OF_MONTH, 1);
				lancerMenuUnJour(nouvJour);
			}
		});
		jour2Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_jour2Button = new GridBagConstraints();
		gbc_jour2Button.fill = GridBagConstraints.BOTH;
		gbc_jour2Button.anchor = GridBagConstraints.NORTHWEST;
		gbc_jour2Button.insets = new Insets(0, 0, 5, 5);
		gbc_jour2Button.gridx = 1;
		gbc_jour2Button.gridy = 0;
		semainePanel.add(jour2Button, gbc_jour2Button);
		
		jour3Button = new JButton();

		jour3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar nouvJour =(GregorianCalendar) jour.clone();
				nouvJour.add(Calendar.DAY_OF_MONTH, 2);
				lancerMenuUnJour(nouvJour);			}
		});
		jour3Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_jour3Button = new GridBagConstraints();
		gbc_jour3Button.fill = GridBagConstraints.BOTH;
		gbc_jour3Button.anchor = GridBagConstraints.NORTHWEST;
		gbc_jour3Button.insets = new Insets(0, 0, 5, 0);
		gbc_jour3Button.gridx = 2;
		gbc_jour3Button.gridy = 0;
		semainePanel.add(jour3Button, gbc_jour3Button);
		
		jour4Button = new JButton();

		jour4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar nouvJour =(GregorianCalendar) jour.clone();
				nouvJour.add(Calendar.DAY_OF_MONTH, 3);
				lancerMenuUnJour(nouvJour);			}
		});
		jour4Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_jour4Button = new GridBagConstraints();
		gbc_jour4Button.fill = GridBagConstraints.BOTH;
		gbc_jour4Button.weighty = 5.0;
		gbc_jour4Button.weightx = 5.0;
		gbc_jour4Button.anchor = GridBagConstraints.NORTHWEST;
		gbc_jour4Button.insets = new Insets(0, 0, 5, 5);
		gbc_jour4Button.gridx = 0;
		gbc_jour4Button.gridy = 1;
		semainePanel.add(jour4Button, gbc_jour4Button);
		
		jour5Button = new JButton();

		jour5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar nouvJour =(GregorianCalendar) jour.clone();
				nouvJour.add(Calendar.DAY_OF_MONTH, 4);
				lancerMenuUnJour(nouvJour);
				
			}
		});
		jour5Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_jour5Button = new GridBagConstraints();
		gbc_jour5Button.fill = GridBagConstraints.BOTH;
		gbc_jour5Button.weightx = 5.0;
		gbc_jour5Button.weighty = 5.0;
		gbc_jour5Button.anchor = GridBagConstraints.NORTHWEST;
		gbc_jour5Button.insets = new Insets(0, 0, 5, 5);
		gbc_jour5Button.gridx = 1;
		gbc_jour5Button.gridy = 1;
		semainePanel.add(jour5Button, gbc_jour5Button);
		
		jour6Button = new JButton();

		jour6Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar nouvJour =(GregorianCalendar) jour.clone();
				nouvJour.add(Calendar.DAY_OF_MONTH, 5);
				lancerMenuUnJour(nouvJour);
			}
		});
		jour6Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_jour6Button = new GridBagConstraints();
		gbc_jour6Button.fill = GridBagConstraints.BOTH;
		gbc_jour6Button.weighty = 5.0;
		gbc_jour6Button.weightx = 5.0;
		gbc_jour6Button.insets = new Insets(0, 0, 5, 0);
		gbc_jour6Button.anchor = GridBagConstraints.NORTHWEST;
		gbc_jour6Button.gridx = 2;
		gbc_jour6Button.gridy = 1;
		semainePanel.add(jour6Button, gbc_jour6Button);
		
		jour7Button = new JButton();

		jour7Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar nouvJour =(GregorianCalendar) jour.clone();
				nouvJour.add(Calendar.DAY_OF_MONTH, 7);
				lancerMenuUnJour(nouvJour);
			}
		});
		jour7Button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.weighty = 5.0;
		gbc_btnNewButton.weightx = 5.0;
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		semainePanel.add(jour7Button, gbc_btnNewButton);
		
		JButton infoPersoButton = new JButton("Mon Compte");
		infoPersoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infosPersoUI.lancerGUI();
				fermerFenetre();
			}
		});

		infoPersoButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuPanel.add(infoPersoButton);
		
		JButton boutiqueButton = new JButton("Boutique");
		boutiqueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boutiqueUI.lancerGUI();
				fermerFenetre();
			}
		});

		boutiqueButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuPanel.add(boutiqueButton);
		
		JButton helpButton = new JButton("Help");

		helpButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuPanel.add(helpButton);
		frame.getContentPane().setLayout(groupLayout);
	}
	

	
	private void lancerMenuUnJour(GregorianCalendar jour) {
	//	frame.setVisible(false);
		//menuUnJourUI.fermerFenetre();
		menuUnJourUI.lancerGUI( jour);
	}
	
	public void fermerFenetre() {
		frame.setVisible(false);
	}
}
