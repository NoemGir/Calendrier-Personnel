package frontiere.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import coeur.DonneesApplication;
import controleur.ControlActiverProduit;
import controleur.ControlAfficherCalendrier;
import controleur.ControlAfficherInfosPerso;
import controleur.ControlModifierProduit;

public class InfosPersoUI extends GUIManager {

	private static JFrame frame;
	private ControlAfficherInfosPerso controlAfficherInfosPerso;
	private ControlModifierProduit controlModifierProduit;
	private ControlAfficherCalendrier controlAfficherCalendrier;
	private ControlActiverProduit controlActiverProduit;
	private MenuPrincipalUI menuPrincipalUI;
	private BoutiqueUI boutiqueUI;
	private static FlashCardUI flashCardUI;
	
	private JLabel starsLabel;
	private JTree tropheesTree;
	private JTree tachesTree;
	private JSplitPane splitPane;
	private JTextPane statisticData;
	private JTree boutiqueTree;
	private JPanel modifProduitPanel;
	private JTextPane descriptionPane;
	private JPanel modifProduitButtonsPanel;
	private JToggleButton activerProduitToggleButton;
	private JButton modifierProduitButton;
	private JButton resetButton;
	private static JButton flashCardButton;
	
	private boolean isOpen = false;
	private boolean produitWindow = false;
	private boolean boutiqueSelectionnee;
	private String nomBoutiqueSelectionnee;
	private static String nomProduitSelectionne;
	
	static {
		flashCardButton = new JButton("Flash Cards");
		flashCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flashCardUI.lancerGUI();
				frame.setVisible(false);
			}
		});
		flashCardButton.setVisible(false);
		flashCardButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
	}
		
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
	public InfosPersoUI(ControlAfficherInfosPerso controlAfficherInfosPerso,
			ControlAfficherCalendrier controlAfficherCalendrier, 
			ControlModifierProduit controlModifierProduit,
			ControlActiverProduit controlActiverProduit,
			FlashCardUI flashCardUI) {
		InfosPersoUI.flashCardUI = flashCardUI;
		this.controlActiverProduit = controlActiverProduit;
		this.controlAfficherInfosPerso = controlAfficherInfosPerso;
		this.controlAfficherCalendrier = controlAfficherCalendrier;
		this.controlModifierProduit = controlModifierProduit;
		flashCardUI.setInfoPersoUI(this);
		initialize();
		ajouterFrame(frame);
	}
	
	public void initDynamique() {
		starsLabel.setText(controlAfficherInfosPerso.getNbStars() + " Stars");
		initTaches();
		initTrophees();
		initData();
		initBoutiquePerso();
		splitPane.setDividerLocation(300);

	}
	
	public void initTaches() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Jours");
		
		for(GregorianCalendar jour : controlAfficherCalendrier.obtenirJoursEnregistres()) {
			DefaultMutableTreeNode modelJour = new DefaultMutableTreeNode(DonneesApplication.DF.format(jour.getTime()));
			Map<String,List<String>> tachesDuJour = controlAfficherCalendrier.obtenirNomTaches(jour);
			for(String tache : tachesDuJour.keySet()) {
				DefaultMutableTreeNode modelPlan = new DefaultMutableTreeNode(tache);
				for(String sousTache : tachesDuJour.get(tache)) {
					DefaultMutableTreeNode modelSousTaches = new DefaultMutableTreeNode(sousTache);
					modelPlan.add(modelSousTaches);
				}
				modelJour.add(modelPlan);
			}
			root.add(modelJour);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		tachesTree.setModel(model);
		
		tachesTree.expandPath(new TreePath(root.getPath()));

	}
	
	public void initTrophees() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Jours");
		
		for(GregorianCalendar jour : controlAfficherInfosPerso.obtenirJoursEnregistresTrophees()) {
			DefaultMutableTreeNode modelJour = new DefaultMutableTreeNode(DonneesApplication.DF.format(jour.getTime()));
			Map<String,List<String>> tachesDuJour = controlAfficherInfosPerso.obtenirNomTrophees(jour);

			for(String tache : tachesDuJour.keySet()) {
				DefaultMutableTreeNode modelPlan = new DefaultMutableTreeNode(tache);
				for(String sousTache : tachesDuJour.get(tache)) {
					DefaultMutableTreeNode modelSousTaches = new DefaultMutableTreeNode(sousTache);
					modelPlan.add(modelSousTaches);
				}
				modelJour.add(modelPlan);
			}
			root.add(modelJour);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		tropheesTree.setModel(model);
		
		tropheesTree.expandPath(new TreePath(root.getPath()));

	}
	
	public void initData() {
		String[] infos = controlAfficherInfosPerso.getInfos();
		String nbPlanTotal = infos[1];
		String nbTacheTotal = infos[2];
		String nbTacheSimple = infos[3];
		String nbEvent = infos[4];
		String nbTrophees = infos[5];
		String nbStarsTotal = infos[6];
		statisticData.setText("\r\n\r\n"+ nbStarsTotal + "\r\n"
				+ nbPlanTotal + "\r\n"
				+ nbTacheTotal + "\r\n"
				+ nbTacheSimple + "\r\n"
				+ nbEvent + "\r\n"
				+ nbTrophees);
	}
	
	public void initBoutiquePerso() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Boutiques");
		Map<String, List<String>> mapBoutique = controlAfficherInfosPerso.getMapProduitsPossedes();
		for(String nomBoutique : mapBoutique.keySet() ) {
			DefaultMutableTreeNode modelBoutique = new DefaultMutableTreeNode(nomBoutique);
			for(String produit : mapBoutique.get(nomBoutique)) {
				
				DefaultMutableTreeNode modelProduit = new DefaultMutableTreeNode(produit);
				modelBoutique.add(modelProduit);
			}
			root.add(modelBoutique);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		boutiqueTree.setModel(model);
		
		boutiqueTree.expandPath(new TreePath(root.getPath()));
		modifProduitPanel.setVisible(false);
		produitWindow = false;
		boutiqueSelectionnee = false;
	}
	
	public void initDescription() {
		modifProduitPanel.setVisible(true);
		if(boutiqueSelectionnee) {
			initDescriptionBoutique();
		}
		else {
			initDescriptionProduit();
		}
	}
	
	public void initDescriptionProduit() {
		activerProduitToggleButton.setVisible(true);
		modifProduitButtonsPanel.setVisible(true);
		
		if (controlActiverProduit.produitActive(nomBoutiqueSelectionnee, nomProduitSelectionne)) {
			activerProduitToggleButton.setText("Desactiver");
			activerProduitToggleButton.setSelected(true);

		}
		else {
			activerProduitToggleButton.setText("Activer");
			activerProduitToggleButton.setSelected(false);
		}
		
		if(controlModifierProduit.produitModifiable(nomBoutiqueSelectionnee)) {
			modifierProduitButton.setVisible(true);
			resetButton.setVisible(true);
		}
		else {
			modifierProduitButton.setVisible(false);
			resetButton.setVisible(false);
		}

		descriptionPane.setText(controlAfficherInfosPerso.getDescriptionProduit(nomBoutiqueSelectionnee, nomProduitSelectionne));
	}
	
	public void initDescriptionBoutique() {
		modifProduitButtonsPanel.setVisible(false);
		activerProduitToggleButton.setVisible(false);
		descriptionPane.setText(controlAfficherInfosPerso.getDescriptionBoutique(nomBoutiqueSelectionnee));
	}
	
	private TreePath recupererChemin(String nomProduit) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) boutiqueTree.getModel().getRoot();
		Enumeration<TreeNode> e = root.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node =  (DefaultMutableTreeNode) e.nextElement();
		    if (node.toString().equals(nomProduit)) {
		        return new TreePath(node.getPath());
		    }
		}
		return null;
	}
	
	public void initEtRetourAuProduit() {

		String nomProduit = nomProduitSelectionne;
		initBoutiquePerso();
		TreePath chemin = recupererChemin(nomProduit);
		if(chemin != null) {
			boutiqueTree.setSelectionPath(chemin);
		}		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setMaximumSize(new Dimension(1000, 1000));
		frame.getContentPane().setMaximumSize(new Dimension(1000, 1000));
		frame.setTitle("Page d'informations personnelles");
		frame.setBounds(100, 100, 740, 640);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JPanel presentationPanel = new JPanel();
		
		JLabel titreLabel = new JLabel();
		titreLabel.setText("Informations Personnelles");
		titreLabel.setMinimumSize(new Dimension(79, 30));
		titreLabel.setMaximumSize(new Dimension(100, 40));
		titreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titreLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 30));
		
		JButton menuButton = new JButton("Menu");
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPrincipalUI.lancerGUI(DonneesApplication.JOUR_ACTUEL);
				frame.setVisible(false);
			}
		});
		
		JLayeredPane layeredPane = new JLayeredPane();
		JPanel statisticPanel = new JPanel();
		layeredPane.setLayer(statisticPanel, 1);
		JPanel produitsPanel = new JPanel();
		produitsPanel.setVisible(false);


		JButton changerFenetreButton = new JButton("Mes produits");
		changerFenetreButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		changerFenetreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (produitWindow) {
				
					produitsPanel.setVisible(false);
					statisticPanel.setVisible(true);
					produitWindow = false;
					changerFenetreButton.setText("Mes produits");
				}
				else {
					
					produitsPanel.setVisible(true);
					statisticPanel.setVisible(false);
					produitWindow = true;
					changerFenetreButton.setText("Mes statistiques");
				}
			}
		});
		GroupLayout gl_presentationPanel = new GroupLayout(presentationPanel);
		gl_presentationPanel.setHorizontalGroup(
			gl_presentationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_presentationPanel.createSequentialGroup()
					.addGroup(gl_presentationPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_presentationPanel.createSequentialGroup()
							.addGap(4)
							.addComponent(menuButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
						.addGroup(gl_presentationPanel.createSequentialGroup()
							.addGap(294)
							.addComponent(changerFenetreButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(195)))
					.addGap(103))
		);
		gl_presentationPanel.setVerticalGroup(
			gl_presentationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_presentationPanel.createSequentialGroup()
					.addGroup(gl_presentationPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_presentationPanel.createSequentialGroup()
							.addGap(29)
							.addComponent(titreLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(changerFenetreButton))
						.addComponent(menuButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		presentationPanel.setLayout(gl_presentationPanel);
		
		JLabel lblNewLabel_3 = new JLabel("NB Stars");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(0, 129, 711, 13);
		GridBagLayout gbl_layeredPane = new GridBagLayout();
		gbl_layeredPane.columnWidths = new int[] {711};
		gbl_layeredPane.rowHeights = new int[] {452};
		gbl_layeredPane.columnWeights = new double[]{1.0};
		gbl_layeredPane.rowWeights = new double[]{1.0};
		layeredPane.setLayout(gbl_layeredPane);
		
		splitPane = new JSplitPane();
		
		
		layeredPane.setLayer(statisticPanel, 1);
		statisticPanel.setVisible(true);
		
		JScrollPane tropheesScrollPane = new JScrollPane();
		splitPane.setLeftComponent(tropheesScrollPane);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		tropheesScrollPane.setRowHeaderView(scrollBar_1);
		
		JLabel lblNewLabel_1 = new JLabel("Liste des Trophées");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tropheesScrollPane.setColumnHeaderView(lblNewLabel_1);
		
		tropheesTree = new JTree();
		tropheesTree.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tropheesScrollPane.setViewportView(tropheesTree);
		
		JScrollPane tacheScrollPane = new JScrollPane();
		splitPane.setRightComponent(tacheScrollPane);
		

		JScrollBar scrollBar = new JScrollBar();
		tacheScrollPane.setRowHeaderView(scrollBar);
		
		JLabel lblNewLabel_2 = new JLabel("Tâches à effectuer");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tacheScrollPane.setColumnHeaderView(lblNewLabel_2);
		
		tachesTree = new JTree();
		tachesTree.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tacheScrollPane.setViewportView(tachesTree);
		
		JTextPane statisticText = new JTextPane();
		statisticText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		statisticText.setText("Statistiques :\r\n\r\n\t- Nombre de stars total obtenues :\r\n\t- Nombre total de plans :\r\n\t- Nombre total de tâches à effectuer :\r\n\t- Nombre de tâches (sans les sous-tâches) :\r\n\t- Nombre total d'évènement :\r\n\t- Nombre total de trophées :");
		
		statisticData = new JTextPane();
		statisticData.setFont(new Font("Tahoma", Font.PLAIN, 15));

		GroupLayout gl_statisticPanel = new GroupLayout(statisticPanel);
		gl_statisticPanel.setHorizontalGroup(
			gl_statisticPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_statisticPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_statisticPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
						.addGroup(gl_statisticPanel.createSequentialGroup()
							.addComponent(statisticText, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(statisticData, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_statisticPanel.setVerticalGroup(
			gl_statisticPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_statisticPanel.createSequentialGroup()
					.addGroup(gl_statisticPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(statisticData, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(statisticText, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
					.addGap(69))
		);
		statisticPanel.setLayout(gl_statisticPanel);
		GridBagConstraints gbc_statisticPanel = new GridBagConstraints();
		gbc_statisticPanel.fill = GridBagConstraints.BOTH;
		gbc_statisticPanel.gridx = 0;
		gbc_statisticPanel.gridy = 0;
		
		layeredPane.setLayer(produitsPanel, 3);
		
		modifProduitPanel = new JPanel();
		modifProduitPanel.setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel optionsPanel = new JPanel();
		GroupLayout gl_produitsPanel = new GroupLayout(produitsPanel);
		gl_produitsPanel.setHorizontalGroup(
			gl_produitsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_produitsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(modifProduitPanel, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(optionsPanel, GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
		);
		gl_produitsPanel.setVerticalGroup(
			gl_produitsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_produitsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_produitsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_produitsPanel.createSequentialGroup()
							.addComponent(modifProduitPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(gl_produitsPanel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(86))
		);
		
		descriptionPane = new JTextPane();
		descriptionPane.setContentType("text/html");
		StyledDocument doc = descriptionPane.getStyledDocument ();
		SimpleAttributeSet center = new SimpleAttributeSet ();
		StyleConstants.setAlignment (center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes (0, doc.getLength (), center, false);

		descriptionPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descriptionPane.setText("Desciption de la boutique / du Produit");
		
		modifProduitButtonsPanel = new JPanel();
		GroupLayout gl_modifProduitPanel = new GroupLayout(modifProduitPanel);
		gl_modifProduitPanel.setHorizontalGroup(
			gl_modifProduitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modifProduitPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifProduitButtonsPanel, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
				.addGroup(gl_modifProduitPanel.createSequentialGroup()
					.addGap(16)
					.addComponent(descriptionPane, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addGap(10))
		);
		gl_modifProduitPanel.setVerticalGroup(
			gl_modifProduitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_modifProduitPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(descriptionPane, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(modifProduitButtonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(13))
		);
		
		modifierProduitButton = new JButton("Modifier");
		modifierProduitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> nomModifications = controlModifierProduit.getNomsModification(nomBoutiqueSelectionnee, nomProduitSelectionne);
				List<Object> typesModifications = controlModifierProduit.getTypesModification(nomBoutiqueSelectionnee, nomProduitSelectionne);
				int indice = 0;
				for(String nomModif : nomModifications) {	
					Object composant  = typesModifications.get(indice);
					
					Object[] params = {nomModif, composant};
					 int input = JOptionPane.showOptionDialog( null,  params, "Modification du produit", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.PLAIN_MESSAGE,  null, null, null );
					if (input == JOptionPane.YES_OPTION) {
						if(composant instanceof JColorChooser colorChooser) {
							Color color = colorChooser.getColor();
							if(controlModifierProduit.modifierProduit(nomBoutiqueSelectionnee, nomProduitSelectionne, nomModif, color)) {
								initEtRetourAuProduit();
							}
						}
						else if(composant instanceof JTextField textField){
							String texte = textField.getText();
							if(controlModifierProduit.modifierProduit(nomBoutiqueSelectionnee, nomProduitSelectionne, texte)) {
								initEtRetourAuProduit();
							}
						}
					}
				    indice++;	
				}
			}
		});
		
		activerProduitToggleButton = new JToggleButton();
		activerProduitToggleButton.setBackground(Color.WHITE);
		modifProduitButtonsPanel.add(activerProduitToggleButton);
		activerProduitToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlActiverProduit.changerActivation(nomBoutiqueSelectionnee, nomProduitSelectionne);
				if (controlActiverProduit.produitActive(nomBoutiqueSelectionnee, nomProduitSelectionne)) {
					activerProduitToggleButton.setText("Desactiver");
				}
				else {
					activerProduitToggleButton.setText("Activer");
				}
			}
		});
		
		activerProduitToggleButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifProduitButtonsPanel.add(modifierProduitButton);
		modifierProduitButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		resetButton = new JButton("Reset modifications");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlModifierProduit.reinitialiserProduit(nomBoutiqueSelectionnee, nomProduitSelectionne);
				initEtRetourAuProduit();			
			}
		});
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifProduitButtonsPanel.add(resetButton);
		modifProduitPanel.setLayout(gl_modifProduitPanel);
		
		JButton boutiqueButton = new JButton("Acceder à la boutique");
		boutiqueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				boutiqueUI.lancerGUI();
			}
		});
		boutiqueButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		optionsPanel.add(boutiqueButton);
		
		optionsPanel.add(flashCardButton);
		
		JScrollBar scrollBar_2 = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar_2);
		
		boutiqueTree = new JTree();
		boutiqueTree.setFont(new Font("Tahoma", Font.PLAIN, 17));
		boutiqueTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) boutiqueTree.getModel().getRoot();
				  DefaultMutableTreeNode node = (DefaultMutableTreeNode) boutiqueTree.getLastSelectedPathComponent();
				  if (node != null) {
					   String nodeInfo = (String) node.getUserObject();
					   DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
					    if(root.equals(node)) {
					    	boutiqueSelectionnee = false;
							modifProduitPanel.setVisible(false);
					    }
					    else if(parent.equals(root)) {
					    	nomBoutiqueSelectionnee = nodeInfo;
					    	boutiqueSelectionnee = true;
						    initDescription();
					    }
					    else {
					    	nomBoutiqueSelectionnee = (String) parent.getUserObject();
					    	setNomProduitSelectionne(nodeInfo);
				        	boutiqueSelectionnee = false;
				        	initDescription();
					    }
				  }
			}
		});
		scrollPane.setViewportView(boutiqueTree);
		
		JLabel lblNewLabel = new JLabel("Produits Disponibles");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setColumnHeaderView(lblNewLabel);
		produitsPanel.setLayout(gl_produitsPanel);
		GridBagConstraints gbc_produitsPanel = new GridBagConstraints();
		gbc_produitsPanel.fill = GridBagConstraints.BOTH;
		gbc_produitsPanel.gridx = 0;
		gbc_produitsPanel.gridy = 0;
		layeredPane.add(produitsPanel, gbc_produitsPanel);
		layeredPane.add(statisticPanel, gbc_statisticPanel);

		
		starsLabel = new JLabel("NB Stars");
		starsLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		starsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(presentationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
				.addComponent(starsLabel, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(presentationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(24)
					.addComponent(starsLabel, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 451, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	public void setMenuPrincipalUI(MenuPrincipalUI menuPrincipalUI) {
		this.menuPrincipalUI = menuPrincipalUI;
		flashCardUI.setMenuPrincipalUI(menuPrincipalUI);
	}
	
	public static void activerButtonFlashCard() {
		flashCardButton.setVisible(true);
	}
	
	public static void desactiverButtonFlashCard() {
		flashCardButton.setVisible(false);
	}
	

	public static void setNomProduitSelectionne(String nomProduitSelectionne) {
		InfosPersoUI.nomProduitSelectionne = nomProduitSelectionne;
	}

	public void setBoutiqueUI(BoutiqueUI boutiqueUI) {
		this.boutiqueUI = boutiqueUI;
	}
}
