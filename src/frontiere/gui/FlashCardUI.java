package frontiere.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import coeur.DonneesApplication;
import controleur.ControlAfficherInfosPerso;
import controleur.ControlJouerFlashCards;

public class FlashCardUI extends GUIManager{
	
	private ControlJouerFlashCards controlJouerFlashCards;
	private ControlAfficherInfosPerso controlAfficherInfosPerso;
	private MenuPrincipalUI menuPrincipalUI;
	private InfosPersoUI infoPersoUI;

	private JFrame frame;
	private JTextField reponseTextField;
	private JToolBar toolBar;
	private JTextPane textPane;
	private JButton modifierButton;
	private JButton supprimerButton;
	private JLabel titreLabel;
	private JButton retirerCarteButton;
	
	private boolean isOpen = false;
	private Color couleurBase;

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
	
	public void initDynamique() {
		initToolBar();
	}
	
	private void initToolBar() {
		List<String> nomFlashCards = controlAfficherInfosPerso.getFlashCardsPossedes();
		String premiereFlashCard = nomFlashCards.get(0);
		for(String flashCards : nomFlashCards) {
			JButton button = new JButton(flashCards);
			button.setFont(new Font("Tahoma", Font.PLAIN, 15));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					initPageFlashCard(e.getActionCommand());
				}
			});
			toolBar.add(button);
		}
		initPageFlashCard(premiereFlashCard);
	}
	
	private void initPageFlashCard(String nomFlashCard) {
		controlJouerFlashCards.recupererFlashCards(nomFlashCard);
		nouvelleCarte(controlJouerFlashCards.carteSuivante());
		titreLabel.setText(nomFlashCard);
	}
	
	private void nouvelleCarte(String nomCote) {
		if(controlJouerFlashCards.estVide()) {
			modifierButton.setEnabled(false);
			supprimerButton.setEnabled(false);
			retirerCarteButton.setEnabled(false);
		}
		else {
			modifierButton.setEnabled(true);
			supprimerButton.setEnabled(true);
			retirerCarteButton.setEnabled(true);
		}
		nouveauCote(nomCote);
	}
	
	private void nouveauCote(String cote) {
		textPane.setText(cote);
		textPane.setForeground(couleurBase);
	}
	


	/**
	 * Create the application.
	 */
	public FlashCardUI(ControlJouerFlashCards controlJouerFlashCards, 
			ControlAfficherInfosPerso controlAfficherInfosPerso) {
		this.controlJouerFlashCards  = controlJouerFlashCards;
		this.controlAfficherInfosPerso = controlAfficherInfosPerso;
		initialize();
		ajouterFrame(frame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 698, 683);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		titreLabel = new JLabel("New label");
		titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titreLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 30));
		
		JPanel modifierFlashCardPanel = new JPanel();
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				  if (e.getClickCount() == 2) {
					  String autreCote = controlJouerFlashCards.autreCote();
			           nouveauCote(autreCote);
			        }
			}
		});
		
		JPanel modifierFlashCardPanel_1 = new JPanel();
		
		JButton lancerDescButton = new JButton("Mode Description D'abord");
		JButton lancerRepButton = new JButton("Mode Réponse d'Abord");
		JButton lancerAleatButton = new JButton("Mode Aléatoire");

		
		lancerDescButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlJouerFlashCards.activerModeDescription();
				lancerDescButton.setEnabled(false);
				lancerRepButton.setEnabled(true);
				lancerAleatButton.setEnabled(true);
			}
		});
		lancerDescButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifierFlashCardPanel_1.add(lancerDescButton);
		
		reponseTextField = new JTextField();
		reponseTextField.setHorizontalAlignment(SwingConstants.CENTER);
		reponseTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String reponseDonnee = reponseTextField.getText();
				String bonneReponse = controlJouerFlashCards.autreCote();
				if(reponseDonnee.equals(bonneReponse)) {
					nouvelleCarte(bonneReponse);
					textPane.setForeground(Color.GREEN);
				}
				else {
					nouvelleCarte(bonneReponse);
					textPane.setForeground(Color.RED);
				}
			}
		});
		reponseTextField.setColumns(10);
		
		JButton cartePredButton = new JButton("<html>Carte<br>Précédente</html>");
		cartePredButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String carte = controlJouerFlashCards.cartePrecedente();
				nouvelleCarte(carte);
			}
		});
		cartePredButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cartePredButton.setMargin(new Insets(0, 3, 0, 3));
		
		JButton carteSuivButton = new JButton("<html>Carte<br>Suivante</html>");
		carteSuivButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String carte = controlJouerFlashCards.carteSuivante();
				nouvelleCarte(carte);
			}
		});
		carteSuivButton.setMargin(new Insets(0, 3, 0, 3));
		carteSuivButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		supprimerButton = new JButton("supprimer");
		supprimerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlJouerFlashCards.retirerCarte();
				nouvelleCarte(controlJouerFlashCards.carteSuivante());
			}
		});
		
		modifierButton = new JButton("modifier");
		modifierButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] rep = demmanderInfosCarte();
				if(rep != null) {
					controlJouerFlashCards.setCarte(rep);
					controlJouerFlashCards.carteSuivante();
					nouvelleCarte(controlJouerFlashCards.cartePrecedente());
				}
			}
		});
		
		JPanel menuPanel = new JPanel();
		
		toolBar = new JToolBar();
		toolBar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(modifierFlashCardPanel, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(cartePredButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(carteSuivButton, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(58)
					.addComponent(modifierButton)
					.addGap(50)
					.addComponent(reponseTextField, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
					.addGap(36)
					.addComponent(supprimerButton)
					.addGap(73))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(2)
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(titreLabel, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
					.addGap(128))
				.addComponent(modifierFlashCardPanel_1, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
				.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(titreLabel, 0, 0, Short.MAX_VALUE)
						.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(modifierFlashCardPanel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(reponseTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(supprimerButton)
								.addComponent(modifierButton)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(160)
							.addComponent(cartePredButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addGap(197))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(162)
							.addComponent(carteSuivButton, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
							.addGap(193)))
					.addGap(18)
					.addComponent(modifierFlashCardPanel_1, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
					.addGap(134))
		);
		
		menuPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton menuButton = new JButton("Menu");
		menuPanel.add(menuButton);
		menuButton.setMargin(new Insets(0, 8, 0, 8));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				menuPrincipalUI.lancerGUI(DonneesApplication.JOUR_ACTUEL);
			}
		});
		
		JButton retourButton = new JButton("Retour");
		menuPanel.add(retourButton);
		retourButton.setMargin(new Insets(2, 4, 2, 4));
		retourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				infoPersoUI.lancerGUI();
			}
		});
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLayeredPane layeredPane = new JLayeredPane();
		
		layeredPane.setOpaque(true);
		layeredPane.setBackground(new Color(255, 255, 255));
		Border border = BorderFactory.createTitledBorder("Carte");
		layeredPane.setBorder(border);
		panel.add(layeredPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 32));
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		couleurBase = textPane.getForeground();
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGap(48)
					.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
					.addGap(50))
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addGap(102)
					.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addGap(92))
		);
		layeredPane.setLayout(gl_layeredPane);
		
		lancerRepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlJouerFlashCards.activerModeReponse();
				lancerDescButton.setEnabled(true);
				lancerRepButton.setEnabled(false);
				lancerAleatButton.setEnabled(true);
			}
		});
		lancerRepButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifierFlashCardPanel_1.add(lancerRepButton);
		
		
		lancerAleatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlJouerFlashCards.activerModeAleatoire();
				lancerDescButton.setEnabled(true);
				lancerRepButton.setEnabled(true);
				lancerAleatButton.setEnabled(false);
			}
		});
		lancerAleatButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifierFlashCardPanel_1.add(lancerAleatButton);
		
		JButton ajouterCarteButton = new JButton("Ajouter une Carte");
		ajouterCarteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] rep = demmanderInfosCarte();
				if(rep != null) {
					controlJouerFlashCards.ajouterCarte(rep);
					nouvelleCarte(controlJouerFlashCards.cartePrecedente());
				}
			}
		});
		ajouterCarteButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifierFlashCardPanel.add(ajouterCarteButton);
		
		retirerCarteButton = new JButton("Retirer Une Carte");
		retirerCarteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String input = JOptionPane.showInputDialog(frame, "Donnez un côté de la carte", null);
				 if(input != null) {
					 controlJouerFlashCards.retirerCarteInput(input);
					 nouvelleCarte(controlJouerFlashCards.carteSuivante());
				 }
			}
		});
		retirerCarteButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modifierFlashCardPanel.add(retirerCarteButton);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private String[] demmanderInfosCarte(){
		JTextField description = new JTextField();
		JTextField reponse = new JTextField();
		Object[] params = {"Donnez la description :",description,"Donnez la réponse :", reponse};
		int input = JOptionPane.showOptionDialog( null,  params, "Donnez les deux faces de la carte : ", JOptionPane.OK_CANCEL_OPTION,  JOptionPane.PLAIN_MESSAGE,  null, null, null );
		if (input == JOptionPane.YES_OPTION) {
			String[] tab = { description.getText(), reponse.getText()};
			if(tab[0] != null && tab[1] != null) {
				return tab;
			}
		}
		return null;
	}
	
	public void setInfoPersoUI(InfosPersoUI infoPersoUI) {
		this.infoPersoUI = infoPersoUI;
	}

	public void setMenuPrincipalUI(MenuPrincipalUI menuPrincipalUI) {
		this.menuPrincipalUI = menuPrincipalUI;
	}
}
