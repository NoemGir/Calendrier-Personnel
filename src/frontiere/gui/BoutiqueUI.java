package frontiere.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import coeur.DonneesApplication;
import controleur.ControlAcheterProduit;
import controleur.ControlAfficherBoutique;
import controleur.ControlAfficherInfosPerso;

public class BoutiqueUI extends GUIManager{

	private JFrame frame;
	private ControlAfficherBoutique controlAfficherBoutique;
	private ControlAcheterProduit controlAcheterProduit;
	private ControlAfficherInfosPerso controlAfficherInfosPerso;
	private MenuPrincipalUI menuPrincipalUI;
	private InfosPersoUI infoPersoUI;
	private JButton acheterButton;
	
	private JLabel starsLabel;
	private JList<String> listBoutique;
	private JButton retourBoutiqueButton;
	private JLabel listeLabel;
	private JTextPane descriptionPane;
	private JLabel titreLabel;
	
	private boolean isOpen = false;
	private boolean boutiqueSelectionnee = false;
	private boolean produitSelectionne = false;
	private boolean pageProduits;

	private String nomBoutiqueSelectionnee;
	private String nomProduitSelectionne;

		
	/**
	 * Launch the application.
	 */
	public void lancerGUI() {
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
		initDynamique();
	} 

	/**
	 * Create the application.
	 */
	public BoutiqueUI(ControlAcheterProduit controlAcheterProduit, 
			InfosPersoUI infoPersoUI, ControlAfficherBoutique controlAfficherBoutique,
			ControlAfficherInfosPerso controlAfficherInfosPerso) {
		this.controlAfficherBoutique = controlAfficherBoutique;
		this.infoPersoUI = infoPersoUI;
		this.controlAcheterProduit = controlAcheterProduit;
		this.controlAfficherInfosPerso = controlAfficherInfosPerso;

		initialize();
		ajouterFrame(frame);
	}
	

	public void initDynamique() {
		starsLabel.setText(controlAfficherInfosPerso.getNbStars() + " Stars");
		acheterButton.setBackground(new Color(238, 238 ,238));
		initDescription();
	}
	
	public void initDescription() {
		if(produitSelectionne) {
			acheterButton.setEnabled(true);
			acheterButton.setText("Acheter (" + controlAcheterProduit.prixProduit(nomBoutiqueSelectionnee, nomProduitSelectionne)+ " stars)");
			if(controlAcheterProduit.verifierArgent(nomBoutiqueSelectionnee, nomProduitSelectionne)) {
				acheterButton.setBackground(Color.green);
			}
			else {
				acheterButton.setBackground(Color.red);
			}
		} else {
			
			if(boutiqueSelectionnee){
				initDescriptionProduits();
			}
			else {
				initDescriptionBoutique();
			}
			acheterButton.setText("Acheter");
			acheterButton.setBackground(new Color(238, 238, 238));
			acheterButton.setEnabled(false);
		}		
	}
	
	public void initDescriptionProduits() {
		
		DefaultListModel<String> model = new DefaultListModel<>();
		List<String> produits = controlAfficherBoutique.getMapProduits().get(nomBoutiqueSelectionnee);

		if(produits != null) {
			model.addAll(produits);
		}
		else {
			model.clear();
		}
		listBoutique.setModel(model);
		retourBoutiqueButton.setText("Retour aux boutiques");
		listeLabel.setText("Liste des produits");
		titreLabel.setText(nomBoutiqueSelectionnee);

	}
	
	public void initDescriptionBoutique() {
		
		
		DefaultListModel<String> model = new DefaultListModel<>();
		Set<String> boutiques = controlAfficherBoutique.getMapProduits().keySet();

		if(boutiques != null) {
			
			model.addAll(boutiques);
		}
		else {
			model.clear();
		}
		listBoutique.setModel(model);
		
		retourBoutiqueButton.setText("Voir les produits");
		listeLabel.setText("Boutiques disponibles");
		titreLabel.setText("Boutique");
		
		descriptionPane.setText("Selectionnez une boutique");
		retourBoutiqueButton.setEnabled(false);
		nomBoutiqueSelectionnee = null;
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 766, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton menuButton = new JButton("Menu");
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				menuPrincipalUI.lancerGUI(DonneesApplication.JOUR_ACTUEL);
			}
		});
		menuButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		titreLabel = new JLabel("Boutique");
		titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titreLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 30));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
		JPanel infosPersoPanel = new JPanel();
		
		JPanel starsPanel = new JPanel();
		
		listeLabel = new JLabel();
		listeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setColumnHeaderView(listeLabel);

		
		listBoutique = new JList<>();
		listBoutique.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            changerVue();
		        }
			}
		});
		listBoutique.setFont(new Font("Tahoma", Font.PLAIN, 17));
		listBoutique.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String nom = listBoutique.getSelectedValue();
				if(nom != null && !nom.equals(nomBoutiqueSelectionnee)) {
					if(pageProduits) {
						produitSelectionne = true;
						nomProduitSelectionne = nom;	
						descriptionPane.setText(controlAfficherBoutique.getPresentationProduitUI(nomBoutiqueSelectionnee, nomProduitSelectionne));
						initDynamique();
					}
					else {
						produitSelectionne = false;
						boutiqueSelectionnee = true;
						nomBoutiqueSelectionnee = nom;
						descriptionPane.setText(controlAfficherBoutique.getDescriptionBoutique(nomBoutiqueSelectionnee));
						retourBoutiqueButton.setEnabled(true);
					}
				}
			}
		});
		scrollPane.setViewportView(listBoutique);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
		
		starsLabel = new JLabel("NB Stars");
		starsLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 22));
		starsPanel.add(starsLabel);
		
		JButton infoPersoButton = new JButton("<html> Infos <br> Perso </html>");
		infoPersoButton.setMargin(new Insets(0, 0, 0, 0));
		infoPersoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				infoPersoUI.lancerGUI();
			}
		});
		infoPersoButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		descriptionPane = new JTextPane();
		descriptionPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descriptionPane.setEditable(false);
		descriptionPane.setContentType("text/html");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menuButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infoPersoButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(titreLabel, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
					.addGap(105))
				.addComponent(starsPanel, GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(162)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
					.addGap(169))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(46)
					.addComponent(descriptionPane, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
					.addGap(70))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(infosPersoPanel, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(titreLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(infoPersoButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(menuButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)))
					.addGap(2)
					.addComponent(starsPanel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infosPersoPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(descriptionPane, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(45, Short.MAX_VALUE))
		);
		retourBoutiqueButton = new JButton();
		infosPersoPanel.add(retourBoutiqueButton);
		
		retourBoutiqueButton.setEnabled(false);
		retourBoutiqueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changerVue();
			}
		});
		retourBoutiqueButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		acheterButton = new JButton("Acheter");
		infosPersoPanel.add(acheterButton);
		acheterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controlAcheterProduit.acheterProduit(nomBoutiqueSelectionnee, nomProduitSelectionne)) {
					produitSelectionne = false;
					initDynamique();
					descriptionPane.setText("Le produit " + nomBoutiqueSelectionnee + " à bien été acheté !\r\nRetrouvez le dans vos boutiques personnelles");
				}
			}
		});
		
		acheterButton.setEnabled(false);
		acheterButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().setLayout(groupLayout);
	}
	public void changerVue() {
		if(pageProduits) {
			produitSelectionne = false;
			boutiqueSelectionnee = false;
			pageProduits = false;
		}
		else {
			pageProduits = true;
		}
		initDescription();
	}
	public void setMenuPrincipalUI(MenuPrincipalUI menuPrincipalUI) {
		this.menuPrincipalUI = menuPrincipalUI;
	}
}
